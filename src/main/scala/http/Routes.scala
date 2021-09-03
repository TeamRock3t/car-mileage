//package http
//
//import MyTapir._
//import akka.http.scaladsl.server.Route
//import org.json4s._
//import org.json4s.native.Serialization
//import org.json4s.native.Serialization.{read, write}
//import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter
//
////scalacOptions += "-Ypartial-unification"
//
//
//class Routes(priceAnalysisService: PriceAnalysisService) extends CarEndpoints  {
//
//  val priceAnalysisRoute: Route = {
//    AkkaHttpServerInterpreter().toRoute(priceAnalysisEndpoint)(priceAnalysisService.createPriceAnalysis)
//  }
//
//}
//
//trait CarEndpoints {
//  implicit val serialization: Serialization = Serialization
//  implicit val formats: Formats = Serialization.formats(NoTypeHints) + DefaultFormats
//
//  val priceAnalysisEndpoint: Endpoint[PriceAnalysisRequest, Unit, PriceAnalysisResponse, Any] = {
//    endpoint
//      .name("create price analysis")
//      .description("given a price analysis request return price analysis response")
//      .post
//      .in("cars" / "price")
//      .in(jsonBody[PriceAnalysisRequest])
//      .out(jsonBody[PriceAnalysisResponse])
//  }
//}
