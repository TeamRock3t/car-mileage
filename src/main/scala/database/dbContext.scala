

import io.getquill.context.jdbc.JdbcRunContext
import io.getquill.{PostgresZioJdbcContext, SnakeCase}

import java.sql.{Timestamp, Types}
import java.time.Instant

package object dbContext {

  object ZQuillContext extends PostgresZioJdbcContext(SnakeCase) with InstantEncoding


  trait InstantEncoding {
    this: JdbcRunContext[_, _] =>

    implicit val instantDecoder: Decoder[Instant] = decoder((index, row, _) => {
      row.getTimestamp(index).toInstant
    })
    implicit val instantEncoder: Encoder[Instant] = encoder(Types.TIMESTAMP, (idx, value, row) => row.setTimestamp(idx, Timestamp.from(value)))
  }

  case class NotFoundException(message: String, id: String) extends Throwable
}
