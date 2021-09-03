package scraping
import org.jsoup.Jsoup

import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class ScraperImpl extends Scraper {

  override def scrapeListing(url: String): Future[Car] = {
    Jsoup.connect(url).validateTLSCertificates(false).get()


    Future(
      Car(
        carId = ???,
        year = ???,
        make = ???,
        model = ???,
        odometer = ???,
        price = ???,
        cylinders = ???,
        drive = ???,
        fuel = ???,
        transmission = ???,
        titleStatus = ???
      )
    )
  }

}
