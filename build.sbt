name := """Gitterific"""
organization := "Concordia"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.6"
libraryDependencies += "org.mockito" % "mockito-core" % "2.10.0" % "test"

libraryDependencies += "com.typesafe.akka" %% "akka-testkit" % "2.6.14" % Test
libraryDependencies += "com.typesafe.akka" %% "akka-stream-testkit" % "2.6.14" % Test

libraryDependencies += guice
libraryDependencies ++= Seq(
  ws
)