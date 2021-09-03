package scraping

import scala.concurrent.Future

trait Scraper {

  def scrapeListing(url: String): Future[Car]

}
