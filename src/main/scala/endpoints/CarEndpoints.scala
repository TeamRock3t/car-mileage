package endpoints

import dbContext.NotFoundException
import json.extractBodyFromJson
import pdi.jwt.JwtClaim
import services.PriceAnalysisRequest
import zhttp.http._
import zio.Has
import zio.console._
import zio.json._
import services.{Car, CarPriceService}

object CarEndpoints {

  val item: JwtClaim => Http[Has[CarPriceService] with Console, HttpError, Request, UResponse] = jwtClaim =>
    Http
      .collectM[Request] {
        case Method.GET -> Root / "cars"        =>
          (for {
            _     <- putStrLn(s"Validated claim: $jwtClaim")
            cars <- CarPriceService.listCars
          } yield Response.jsonString(cars.toJson))

        case Method.GET -> Root / "cars" / id   =>
          for {
            _    <- putStrLn(s"Validated claim: $jwtClaim")
            car <- CarPriceService.getCar(id)
          } yield Response.jsonString(car.toJson)

        case req @ Method.POST -> Root / "cars" =>
          for {
            _       <- putStrLn(s"Validated claim: $jwtClaim")
            request <- extractBodyFromJson[Car](req)
            results <- CarPriceService.createCar(request)
          } yield Response.jsonString(results.toJson)

        case req@Method.PATCH -> Root / "cars" / id =>
          for {
            _ <- putStrLn(s"Validated claim: $jwtClaim")
            request <- extractBodyFromJson[Car](req)
            // TODO check to make this safe           _ <-
            results <- CarPriceService.updateCar(request)
          } yield Response.jsonString(results.toJson)

        case Method.DELETE -> Root / "cars" / id =>
          for {
            _ <- putStrLn(s"Validated claim: $jwtClaim")
            _ <- CarPriceService.deleteCar(id)
          } yield Response.ok
      }
      .catchAll {
        case NotFoundException(msg, id) =>
          Http.fail(HttpError.NotFound(Root / "items" / id.toString))
        case ex: Throwable              =>
          Http.fail(HttpError.InternalServerError(msg = ex.getMessage, cause = Option(ex)))
        case err                        => Http.fail(HttpError.InternalServerError(msg = err.toString))
      }


}
