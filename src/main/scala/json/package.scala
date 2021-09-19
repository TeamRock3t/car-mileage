import zhttp.http.Request
import zio.{IO, ZIO}
import zio.json.{DecoderOps, JsonCodec}



package object json {
  def extractBodyFromJson[A](request: Request)(implicit codec: JsonCodec[A]): IO[Serializable, A] =
    for {
      requestOrError <- ZIO.fromOption(request.getBodyAsString.map(_.fromJson[A]))
      body           <- ZIO.fromEither(requestOrError)
    } yield body

}
