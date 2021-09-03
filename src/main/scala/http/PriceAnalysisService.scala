package http

import scala.concurrent.Future

trait PriceAnalysisService {

//  def createPriceAnalysis(request: PriceAnalysisRequest): Future[Either[Unit, PriceAnalysisResponse]]


  def createPriceAnalysis(request: PriceAnalysisRequest): PriceAnalysisResponse
  var xxx = ""
  val one = 1
  def stringeroo(incoing: Int): String = {
    incoing.toString
  }


  def doStuff(): String = {
    stringeroo(one)
  }

}
