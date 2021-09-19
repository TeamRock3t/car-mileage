package car.startup

import endpoints.CarServer
import zio.console.putStrLn
import zio.{App, ExitCode, URIO, ZIO}

object Main extends App {

  val program: ZIO[Any, Throwable, Nothing] = (CarServer.program)

  override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    (putStrLn("Starting up Price Request Service...") *> program).exitCode

}

