package http

case class PriceAnalysisRequest(
                                 make: String,
                                 model: String,
                                 mileage: Int,
                                 price: Float
                               )

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
