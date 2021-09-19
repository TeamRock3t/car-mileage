package services

import zio.console.Console
import zio.{Has, RLayer, Task, ZIO}
import scala.language.implicitConversions
import repository.car.{CarRecord, CarRepository}

import java.util.UUID.randomUUID

trait CarPriceService {

  def createCar(request: Car): Task[Car]
  def getCar(id: String): Task[Car]
  def listCars: Task[Seq [Car]]
  def updateCar(request: Car): Task[Car]
  def deleteCar(id: String): Task[Unit]

}

object CarPriceService{
  def createCar(request: Car): ZIO[Has[CarPriceService],Throwable, Car]  = ZIO.serviceWith[CarPriceService](_.createCar(request))
  def getCar(id: String): ZIO[Has[CarPriceService],Throwable, Car]                    = ZIO.serviceWith[CarPriceService](_.getCar(id))
  def listCars: ZIO[Has[CarPriceService], Throwable, Seq[Car]]                        = ZIO.serviceWith[CarPriceService](_.listCars)
  def updateCar(request: Car): ZIO[Has[CarPriceService], Throwable, Car] = ZIO.serviceWith[CarPriceService](_.updateCar(request))
  def deleteCar(id: String): ZIO[Has[CarPriceService], Throwable, Unit]               = ZIO.serviceWith[CarPriceService](_.deleteCar(id))


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
//  def conductPriceAnalysis() = {
//    ???
//  }
}



