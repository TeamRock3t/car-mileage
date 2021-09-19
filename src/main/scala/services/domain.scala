package services

import io.scalaland.chimney.dsl.TransformerOps
import repository.car.CarRecord
import zio.json.{DeriveJsonCodec, DeriveJsonDecoder, DeriveJsonEncoder, JsonCodec, JsonDecoder, JsonEncoder}


case class PriceAnalysisRequest(
                                 make: String,
                                 model: String,
                                 mileage: Int,
                                 price: Float
                               )

object PriceAnalysisRequest {
  implicit val codec: JsonCodec[PriceAnalysisRequest] = DeriveJsonCodec.gen[PriceAnalysisRequest]
}

case class PriceAnalysisResponse(
                                  make: String,
                                  model: String,
                                  mileage: Int,
                                  price: Float,
                                  expectedMileage: Int,
                                  mileageRemaining: Int,
                                  pricePerMileRemaining: Float,
                                  fairPrice: Float = 0
                                )

object PriceAnalysisResponse{
  implicit val codec: JsonCodec[PriceAnalysisResponse] = DeriveJsonCodec.gen[PriceAnalysisResponse]
}

case class Car(
                carId: String = "Needs Id",
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
object Car{

    implicit def fromCarRecord(record: CarRecord): Car                 = record.into[Car].transform
    implicit def fromSeqCarRecord(records: Seq[CarRecord]): Seq[Car]   = records.map(fromCarRecord)
    implicit val jsonEncoder: JsonEncoder[Car]                         = DeriveJsonEncoder.gen[Car]
    implicit val jsonDecoder: JsonDecoder[Car]                         = DeriveJsonDecoder.gen[Car]
    implicit val codec: JsonCodec[Car]                                 = DeriveJsonCodec.gen[Car]

}


