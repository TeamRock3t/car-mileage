package http

import org.scalatest.funspec.AnyFunSpec
import repository.CarRepositoryImpl

class PriceAnalysisServiceImplSpec extends AnyFunSpec{


  val testCarRepo = new CarRepositoryImpl()
  val testPAS = new PriceAnalysisServiceImpl(testCarRepo)


  describe( "price analysis"){
    it ("Analyze price"){
      //inputs
     val testRequest = PriceAnalysisRequest(
       make = "ford", model = "mustang", mileage = 101000, price = 8999
     )
      //code we run
      val test = testPAS.createPriceAnalysis(testRequest)


      //assertions
   println(test)
      assert(test.mileageRemaining == 82129)
    }
  }
}
