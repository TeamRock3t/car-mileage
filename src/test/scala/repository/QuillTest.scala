package repository

import io.getquill.Ord
import org.scalatest.funspec.AnyFunSpec
import repository.QuillContext.ctx
import repository.QuillContext.ctx._
import scraping.Car

class QuillTest extends AnyFunSpec{

//  val quill = QuillContext
//
//  import quill.ctx._

  describe( "scraper"){
    it ("should scrape"){

      val car = Car(
        carId = "1",
        year = 1995,
        make = "ford",
        model = "Ranger",
        odometer = 65000,
        price = 4000,
        cylinders = Option(6),
        drive = Option("4WD"),
        fuel = Option("Gas"),
        transmission = Option("Manual"),
        titleStatus = Option.empty[String]
      )

      val query1 = quote {
        query[Car].insert(lift(car))
      }

      val test  = ctx.run(query1)

      assert(true)
    }
  }

  describe( "price analysis service"){
    it ("should read top 5 records"){

      val q = quote{
        query[Car]
          .filter(c=> c.make == "ford")
          .filter(c=> c.model == "mustang")
          .sortBy(c => c.odometer)(Ord.descNullsLast)
          .take(5)
      }

     val test = ctx.run(q)
      println(test)
      assert(test.length == 5)
    }
  }

//  List(
//    Car(50,2012,ford,mustang,197605,16900.0,None,None,None,None,None),
//    Car(49,2012,ford,mustang,187654,15600.0,None,None,None,None,None),
//    Car(48,2012,ford,mustang,187000,14700.0,None,None,None,None,None),
//    Car(47,2012,ford,mustang,175400,15100.0,None,None,None,None,None),
//    Car(46,2012,ford,mustang,167987,16400.0,None,None,None,None,None))


}
