package scraping

import org.scalatest.funspec.AnyFunSpec

class ScraperImplSpec extends AnyFunSpec {
  val testScraper = new ScraperImpl()

  describe( "scraper"){
    it ("should scrape"){
      val url = "https://denver.craigslist.org/cto/d/broomfield-1967-kaiser-jeep-m715/7372106350.html"

      val test = testScraper.scrapeListing(url)


      assert(true)
    }
  }

}
