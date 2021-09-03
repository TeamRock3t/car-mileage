package scraping

case class Car(
                     carId: String,
                     year: Int,
                     make: String,
                     model: String,
                     odometer: Int,
                     price: Float,
                     cylinders: Option[Int],
                     drive: Option[String],
                     fuel: Option[String],
                     transmission: Option[String],
                     titleStatus: Option[String] = Option("unknown")
                     )

//CREATE TABLE cars (
//  car_id TEXT PRIMARY KEY,
//  year  INTEGER NOT NULL,
//  make TEXT NOT NULL,
//  model TEXT NOT NULL,
//  odometer INTEGER NOT NULL,
//  price FLOAT NOT NULL,
//  cylinders INTEGER,
//  drive TEXT,
//  fuel TEXT,
//  transmission TEXT,
//  title_status TEXT
//)