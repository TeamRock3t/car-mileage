package endpoints

import http.auth.AuthenticationApp
import io.getquill.context.ZioJdbc.DataSourceLayer
import repository.car.CarRepository
import services.CarPriceService
import zhttp.http._
import zhttp.service.Server
import zio.console.Console
import zio.zmx.prometheus.PrometheusClient
import zio.{App, ExitCode, Has, URIO, ZIO}
import zio.magic._

object CarServer extends App {

  val endpoints: Http[Has[PrometheusClient] with Has[CarPriceService] with Console, HttpError, Request, Response[Has[CarPriceService] with Console, HttpError]] =
    AuthenticationApp.login +++ CORS(
      AuthenticationApp.authenticate(HttpApp.forbidden("None shall pass."), CarEndpoints.item),
      config = CORSConfig(anyOrigin = true)
    )

  val program: ZIO[Any, Throwable, Nothing] = Server
    .start(8080, endpoints)
    .inject(Console.live, DataSourceLayer.fromPrefix("carDb"), CarPriceService.layer, CarRepository.layer, PrometheusClient.live)

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] = program.exitCode
}
