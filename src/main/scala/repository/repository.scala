package repository

import io.getquill.Ord
import scraping.Car

trait CarRepository {

  def getMaxMileageCars(make: String, model: String): List[Car]
  def getCars(make: String, model: String): List[Car]
}

class CarRepositoryImpl extends CarRepository with CarQueries {
  import repository.QuillContext.ctx
  import ctx._

  override def getMaxMileageCars(make: String, model: String): List[Car] = {
    ctx.run(maxMileageCarsQuery(make, model))
  }

  override def getCars(make: String, model: String): List[Car] = {
    ctx.run(carQuery(make, model))
  }
}

trait CarQueries {
  import repository.QuillContext.ctx._
//  def cars = querySchema[Car]

  def maxMileageCarsQuery(make: String, model: String) = quote {
    query[Car]
      .filter(c => c.make == lift(make))
      .filter(c => c.model == lift(model))
      .sortBy(c => c.odometer)(Ord.descNullsLast)
      .take(5)
  }

  def carQuery(make: String, model: String) = quote {
    query[Car]
      .filter(c => c.make == lift(make))
      .filter(c => c.model == lift(model))
  }

}