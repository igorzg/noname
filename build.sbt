name := """noname"""
organization := "com.noname"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.3"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "6.0.6",
  "com.typesafe.play" %% "play-slick" % "3.0.1",
  "com.typesafe.play" %% "play-slick-evolutions" % "3.0.1",
  "com.github.t3hnar" %% "scala-bcrypt" % "3.0"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.noname.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.noname.binders._"
