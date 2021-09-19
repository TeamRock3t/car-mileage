package repository

package object car {

  case class CarRecord(
                        carId: String,
                        year: Int,
                        make: String,
                        model: String,
                        mileage: Int,
                        price: Float,
                        cylinders: Option[Int],
                        drive: Option[String],
                        fuel: Option[String],
                        transmission: Option[String]
                      )

}
