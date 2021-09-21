package services

import zio.console.Console
import zio.{Has, RLayer, Task, ZIO}

import scala.language.implicitConversions
import repository.car.{CarRecord, CarRepository, CarRepositoryLive}

import java.util.UUID.randomUUID

trait CarPriceService {

  def createCar(request: Car): Task[Car]
  def getCar(id: String): Task[Car]
  def listCars: Task[Seq [Car]]
  def updateCar(request: Car): Task[Car]
  def deleteCar(id: String): Task[Unit]
  def conductPriceAnalysis(priceAnalysisRequest: PriceAnalysisRequest): Task[PriceAnalysisResponse]
}

object CarPriceService{
  def createCar(request: Car): ZIO[Has[CarPriceService],Throwable, Car]  = ZIO.serviceWith[CarPriceService](_.createCar(request))
  def getCar(id: String): ZIO[Has[CarPriceService],Throwable, Car]                    = ZIO.serviceWith[CarPriceService](_.getCar(id))
  def listCars: ZIO[Has[CarPriceService], Throwable, Seq[Car]]                        = ZIO.serviceWith[CarPriceService](_.listCars)
  def updateCar(request: Car): ZIO[Has[CarPriceService], Throwable, Car] = ZIO.serviceWith[CarPriceService](_.updateCar(request))
  def deleteCar(id: String): ZIO[Has[CarPriceService], Throwable, Unit]               = ZIO.serviceWith[CarPriceService](_.deleteCar(id))
  def conductPriceAnalysis(priceAnalysisRequest: PriceAnalysisRequest): ZIO[Has[CarPriceService], Throwable, PriceAnalysisResponse] = ZIO.serviceWith[CarPriceService](_.conductPriceAnalysis(priceAnalysisRequest))

  val layer: RLayer[Has[CarRepository] with Console, Has[CarPriceService]] = (CarPriceServiceLive(_, _)).toLayer
}

//TODO need to get this to work

case class CarPriceServiceLive(repository: CarRepository, console: Console.Service) extends CarPriceService {
  override def createCar(request: Car): Task[Car] = {
    repository.create(
      CarRecord(
        carId = randomUUID.toString,
        year = request.year,
        make = request.make,
        model = request.model,
        mileage = request.mileage,
        price = request.price,
        cylinders = request.cylinders,
        drive = request.drive,
        fuel = request.fuel,
        transmission = request.transmission
      )
    ).map(Car.fromCarRecord)
  }

  override def getCar(id: String): Task[Car] = repository.findById(id).map(Car.fromCarRecord)

  override def listCars: Task[Seq[Car]] = repository.listAll.map(Car.fromSeqCarRecord)

//TODO add more to the updated later
  override def updateCar(request: Car): Task[Car] = repository.update(
    CarRecord(
      carId = randomUUID.toString,
      year = request.year,
      make = request.make,
      model = request.model,
      mileage = request.mileage,
      price = request.price,
      cylinders = request.cylinders,
      drive = request.drive,
      fuel = request.fuel,
      transmission = request.transmission
    )
  ).map(Car.fromCarRecord)

  override def deleteCar(id: String): Task[Unit] = repository.delete(id)

  //TODO make car price analysis here
  override def conductPriceAnalysis(request: PriceAnalysisRequest): Task[PriceAnalysisResponse] = {
    for {
      expectedMileage <- getExpectedMileage(request.make, request.model)
      mileageRemaining <- getMileageRemaining(request.make, request.model, request.mileage)
      pricePerMileRemaining <- getPricePerMile(request.make, request.model, request.mileage, request.price)
      fairPrice <- getFairPrice(request.make, request.model, request.mileage)
    } yield {
      PriceAnalysisResponse(
        make = request.make,
        model = request.model,
        mileage = request.mileage,
        price = request.price,
        expectedMileage = expectedMileage,
        mileageRemaining = mileageRemaining,
        pricePerMileRemaining = pricePerMileRemaining,
        fairPrice = fairPrice
      )
    }

  }

  //This sorts the cars to get the remaining cars not used to calculate the max mileage
  def getRemainingCars(make: String, model: String): Task[Seq[CarRecord]] = {

    for {
      cars <- repository.findByMakeAndModel(make, model)
      orderedCars = cars.sortBy(car => car.mileage)
      remainingCars = orderedCars.take(cars.length - 5)
    } yield remainingCars

  }

  //This sorts the cars to get the max mileage cars
  def getMaxMileageCars(make: String, model: String): Task[Seq[CarRecord]] = {
    for {
      cars <- repository.findByMakeAndModel(make, model)
      orderedCars = cars.sortBy(c => c.mileage)
      maxMileageCars = orderedCars.reverse.take(5)
    } yield maxMileageCars

  }

  // calculates expected mileage of a specific make and model of car by taking the average of the 5 highest mileage cars
  // of the input make and model.  this takes a string for make and model, and returns the expected mileage as an Int.
  def getExpectedMileage(make: String, model: String): Task[Int] = {
    for {
      maxMileageCars <- getMaxMileageCars(make, model)
      expectedMileage = maxMileageCars
        .map(maxMileageCars => maxMileageCars.mileage)
        .sum / maxMileageCars.length
    } yield expectedMileage
  }

  //this calculates the mileage remaining, by taking the expected mileage and subtracting the input mileage.
  //this takes make, model, and mileage, and returns the mileage remaining.
  def getMileageRemaining(make: String, model: String, mileage: Int): Task[Int] = {
    for {
      expectedMileage <- getExpectedMileage(make, model)
      mileageRemaining = expectedMileage - mileage
    } yield mileageRemaining
  }


  def getPricePerMile(make: String, model: String, mileage: Int, price: Float): Task[Float] = {
    for {
      mileageRemaining <- getMileageRemaining(make, model, mileage)
      pricePerMile = price / mileageRemaining
    } yield pricePerMile
  }

  //This calculates the fair price of the car based on mileage and price. it will take the average price per mile remaining
  // from the cars not used to calculate the max mileage.  it then multiplies this average price per mile by the miles
  //remaining on the input car to calculate the fair price
  //
  def getFairPrice(make: String, model: String, mileage: Int): Task[Float] = {
    for {
      remainingCars <- getRemainingCars(make, model)
      mileageRemaining <- getMileageRemaining(make, model, mileage)
      maxMileageCars <- getMaxMileageCars(make, model)

      totalMileage = remainingCars.map(c => c.mileage).sum
      totalPrice = remainingCars.map(c => c.price).sum

      totalExpectedMileage = mileageRemaining * maxMileageCars.length

      totalMileageRemaining = totalExpectedMileage - totalMileage
      fairCostPerMile = totalMileageRemaining / totalPrice

      inputMileageRemaining = mileageRemaining - mileage

      fairPrice = fairCostPerMile * inputMileageRemaining

    } yield fairPrice
  }

}

