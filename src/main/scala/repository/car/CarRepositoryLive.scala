package repository.car

import dbContext.NotFoundException
import io.getquill.context.ZioJdbc.QuillZioExt
import io.getquill.context.qzio.ImplicitSyntax._
import zio._

import java.io.Closeable
import javax.sql.DataSource

case class CarRepositoryLive(dataSource: DataSource with Closeable) extends CarRepository {
  import dbContext.ZQuillContext._
  implicit val env = Implicit(Has(dataSource))

  override def create(car: CarRecord): Task[CarRecord] = transaction {
    for {
      _ <- run(CarQueries.create(car))
      car1 <- run(CarQueries.findById(car.carId))
      created <- ZIO.fromOption(car1.headOption).orElseFail(NotFoundException(s"Could not find car with id ${car.carId}", car.carId))
    } yield created
  }.implicitDS

  override def findById(carId: String): Task[CarRecord] = {
    for {
      results <- run(CarQueries.findById(carId)).implicitDS
      car <- ZIO.fromOption(results.headOption).orElseFail(NotFoundException(s"Could not find car with id $carId", carId))
    } yield car
  }

  override def listAll: Task[Seq[CarRecord]] = run(CarQueries.carsQuery).implicitDS


  override def update(car: CarRecord): Task[CarRecord] = transaction{
    for {
      _ <- run(CarQueries.update(car))
      cars <- run(CarQueries.findById(car.carId))
      updated <- ZIO.fromOption(cars.headOption).orElseFail(new Exception("Cannot find after update"))
    }yield updated
  } .implicitDS

  override def delete(id: String): Task[Unit] = run(CarQueries.delete(id)).implicitDS.map(_ => ())

  override def findByMakeAndModel(make: String, model: String): Task[Seq[CarRecord]] = run(CarQueries.findByMakeAndModel(make, model)).implicitDS

}


