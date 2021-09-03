package http

import sttp.tapir.client.sttp.SttpClientInterpreter
import sttp.tapir.docs.openapi.OpenAPIDocsInterpreter
import sttp.tapir.{Tapir, TapirAliases}
import sttp.tapir.generic.SchemaDerivation
import sttp.tapir.json.json4s.TapirJson4s
import sttp.tapir.server.akkahttp.AkkaHttpServerInterpreter

object MyTapir extends Tapir
  with TapirJson4s
  with AkkaHttpServerInterpreter
  with SttpClientInterpreter
  with OpenAPIDocsInterpreter
  with SchemaDerivation
  with TapirAliases
