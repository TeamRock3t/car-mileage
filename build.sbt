name := "car-mileage"

version := "0.1"

scalaVersion := "2.13.6"

//scalacOptions += "-Ypartial-unification"

val funSpecVersion = "3.2.9"
val jsoupVersion = "1.11.3"
val postgresVersion = "42.2.8"
val quillVersion = "3.9.0"
val tapirVersion = "0.19.0-M7"
val json4sVersion = "4.0.3"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-funspec" % funSpecVersion % "test",
  "org.jsoup" % "jsoup" % jsoupVersion,
  "org.postgresql" % "postgresql" % postgresVersion,
  "io.getquill" %% "quill-jdbc" % quillVersion,
  //tapir
  "com.softwaremill.sttp.tapir" %% "tapir-core" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-json-json4s" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-akka-http-server" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-sttp-client" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-docs" % tapirVersion,
  "com.softwaremill.sttp.tapir" %% "tapir-openapi-circe-yaml" % tapirVersion,
  "org.json4s" %% "json4s-native" % json4sVersion
)


