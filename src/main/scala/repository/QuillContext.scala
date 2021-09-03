package repository

import io.getquill._
import com.zaxxer.hikari.{HikariConfig, HikariDataSource}

object QuillContext {

  val pgDataSource = new org.postgresql.ds.PGSimpleDataSource()
  pgDataSource.setUser("postgres")
  val config = new HikariConfig()
  config.setDataSource(pgDataSource)
  val ctx = new PostgresJdbcContext(SnakeCase, new HikariDataSource(config))

  import ctx._

}
