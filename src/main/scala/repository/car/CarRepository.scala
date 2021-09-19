package repository.car

import zio.{Task, _}
import java.io.Closeable
import javax.sql.DataSource

trait CarRepository {

  def create(car: CarRecord): Task[CarRecord]
  def findById(id: String): Task[CarRecord]
  def listAll: Task[Seq [CarRecord]]
  def update(car: CarRecord): Task[CarRecord]
  def delete(id: String): Task[Unit]
  def findByMakeAndModel(make: String, model: String): Task[Seq[CarRecord]]

}

//companion object for CarRepository Trait
object CarRepository{

  def create(car:CarRecord): RIO[Has[CarRepository], CarRecord]   = ZIO.serviceWith[CarRepository](_.create(car))
  def findById(id: String): RIO[Has[CarRepository], CarRecord]    = ZIO.serviceWith[CarRepository](_.findById(id))
  def listAll: RIO[Has[CarRepository], Seq[CarRecord]]            = ZIO.serviceWith[CarRepository](_.listAll)
  def update(car: CarRecord): RIO[Has[CarRepository], CarRecord]  = ZIO.serviceWith[CarRepository](_.update(car))
  def delete(id: String): RIO[Has[CarRepository], Unit]           = ZIO.serviceWith[CarRepository](_.delete(id))
  def findByMakeAndModel(make: String, model: String): RIO[Has[CarRepository], Seq[CarRecord]] = ZIO.serviceWith[CarRepository](_.findByMakeAndModel(make, model))


  val layer: URLayer[Has[DataSource with Closeable], Has[CarRepository]] = (CarRepositoryLive(_)).toLayer

}

