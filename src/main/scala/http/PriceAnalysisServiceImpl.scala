package http

import repository.CarRepositoryImpl
import scraping.Car

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{ExecutionContext, Future}

class PriceAnalysisServiceImpl(carRepo: CarRepositoryImpl) extends PriceAnalysisService {


  override def createPriceAnalysis(request: PriceAnalysisRequest): PriceAnalysisResponse = {//Future[Either[Nothing, PriceAnalysisResponse]] = {
    val cars = carRepo.getCars(request.make, request.model)

//    val numberOfCars = cars.length
//    val totalMileage = cars.map(car => car.odometer).sum

    val orderedCars = cars.sortBy(car => car.odometer)
    val maxMileageCars = orderedCars.reverse.take(5)
    val remainingCars = orderedCars.take(cars.length-5)

    val expectedMileage = maxMileageCars
      .map(maxMileageCars => maxMileageCars.odometer)
      .sum/maxMileageCars.length



    val mileageRemaining = expectedMileage-request.mileage
    val pricePerMileRemaining = mileageRemaining/request.price
    val fairPrice = calculateFairPrice(remainingCars,expectedMileage,request.price)




    val option: Option[String] = Option("cory")
    val option1: Option[String] = None

    option.map(string => string.toUpperCase)


    val future1: Future[String] = Future("Jesse")

    val future2 = future1.map(string => string.toUpperCase())

    println(s"expected mileage $expectedMileage")
    println(s"mileage remaining $mileageRemaining")
    println(s"fair price $fairPrice")
    /*
    1. Search Database for Same Make and Model vehicle
    2. Identify 5 highest mileage vehicles
    3. average mileage
    4. subtract input mileage from average mileage to get mileage remaining
    5. divide mileage remaining by input price
     */

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



  def calculateFairPrice(cars: List[Car], expectedMileage: Int, inputMileage: Float): Float = {

    val totalMileage = cars.map(c => c.odometer).sum
    val totalPrice = cars.map(c => c.price).sum

    val totalExpectedMileage = expectedMileage*cars.length

    val totalMileageRemaining = totalExpectedMileage-totalMileage
    val fairCostPerMile = totalMileageRemaining/totalPrice

    val inputMileageRemaining = expectedMileage-inputMileage

      println(s"fair Cost per mile $fairCostPerMile")
      println(s"total price $totalPrice")
      println(s"total mileage remaining $totalMileageRemaining")

    fairCostPerMile*inputMileageRemaining

    /*
    I want this to use the input from make and model to establish a price per miles remaingin for all entries in
    the table except the entries that are used to determine the maximum expected mileage. then i want to average
    the price per mile remaining to get an average price that will be used to calculate the fair value for the
    car that the user inputs.
    1. Search Database for Same Make and Model vehicle
    2. Identify 5 highest mileage vehicles
    3. average mileage of 5 highest mileage vehicles to get expected mileage
    go to 4. or 9. for alternative
    4. get mileage remaining by subtracting mileage for each car in the table (except 5 used to get expected mileage) from
    expected mileage.
    5. divide mileage remaining by price for each car (except last 5) to get price per mile for each car
    6. average all price per mile values to get average price per mile
    7. for input car calculate mileage remaining: expected mileage-input mileage
    8. mileage remaining for user car * average price per mile = fair value

    9. sum mileage for all cars in table(except 5 highest) = total mileages
    10. sum Price for all cars in table(except 5 highest) = total price
    11. expected mileage * number of cars in table(except 5 highest) = total expected Mileage(TEM)
    12. total price/(TEM-total mileage) = Average price per mile
     */
  }
}
