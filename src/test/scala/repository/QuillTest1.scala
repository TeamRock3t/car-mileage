//package repository
//
//import io.getquill.Ord
//import org.scalatest.funspec.AnyFunSpec
//import repository.QuillContext.ctx
//import repository.QuillContext.ctx._
//import scraping.Car
//
//
//class QuillTest1 extends AnyFunSpec{
//
////  val quill = QuillContext
////
////  import quill.ctx._
//
//  describe( "scraper"){
//    it ("should scrape"){
//
//      val car = Car(
//        carId = "1",
//        year = 1995,
//        make = "ford",
//        model = "Ranger",
//        odometer = 65000,
//        price = 4000,
//        cylinders = Option(6),
//        drive = Option("4WD"),
//        fuel = Option("Gas"),
//        transmission = Option("Manual"),
//        titleStatus = Option.empty[String]
//      )
//
//      val query1 = quote {
//        query[Car].insert(lift(car))
//      }
//
//      val test  = ctx.run(query1)
//
//      assert(true)
//    }
//  }
//
//  describe( "price analysis service"){
//    it ("should read top 5 records"){
//
//
//      val q1 = quote {
//        query[Car].filter(c=>c.make =="ford")
//      }
//      ctx.run(q1)
//
//      val q2 = quote{
//        query[Car].filter(c=>c.model == "mustang")
//      }
//      ctx.run(q2)
//
//      val q3 = quote{
//        query[Car].sortBy(c => c.odometer)(Ord.descNullsLast)
//      }
//      ctx.run(q3)
//
//      val q4 = quote{
//        query[Car].take(5)
//      }
//      ctx.run(q4.avg)
//
//      val mq = quote{
//        query[Car]
//          .filter(c=> c.make == "ford")
//          .filter(c=> c.model == "mustang")
//          .sortBy(c => c.odometer)(Ord.descNullsLast)
//          .take(5)
//      }
//      ctx.run(mq.avg)
//
//
//
//      val test  = ctx.run(query1)
//
//      assert(test.length == 5)
//    }
//  }
//
//
//}
