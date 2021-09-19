package repository.car

object CarQueries {

  import dbContext.ZQuillContext._

  implicit val carSchemaMeta = schemaMeta[CarRecord]("car")

  val carsQuery                            = quote(query[CarRecord])

  def create(car: CarRecord)                = quote(carsQuery.insert(lift(car)))
  def findById(carId: String)               = quote(carsQuery.filter(_.carId == lift(carId)))
  def listAll                               = quote(carsQuery)
  //TODO learn proper way to update in quill
  def update(car: CarRecord) = quote(carsQuery.filter(c => c.carId == lift(car.carId)).update(lift(car)))
  def delete(carId: String)                 = quote(carsQuery.filter(c => c.carId == lift(carId)).delete)
  def findByMakeAndModel(make: String, model: String) = quote(carsQuery.filter(c => c.make == lift(make)).filter(c => c.model == lift(model)))

}