name := """noname"""
organization := "com.noname"

version := "1.0-SNAPSHOT"

lazy val macros = project.in(file("macros"))
lazy val root = (project in file(".")).dependsOn(macros).enablePlugins(PlayScala)

// add compiler
resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

val json4sVersion = "3.5.3"
val jacksonVersion = "2.9.2"
val slickVersion = "3.0.1"
val scalaVersionNum = "2.12.4"

scalaVersion := scalaVersionNum
scalacOptions += "-Ymacro-debug-lite"
libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

libraryDependencies ++= Seq(

  "org.scala-lang" % "scala-reflect" % scalaVersionNum,

  "mysql" % "mysql-connector-java" % "6.0.6",

  "com.github.t3hnar" %% "scala-bcrypt" % "3.0",

  "com.typesafe.play" %% "play-slick" % slickVersion,
  "com.typesafe.play" %% "play-slick-evolutions" % slickVersion,

  "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion,
  "org.json4s" %% "json4s-jackson" % json4sVersion
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.noname.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.noname.binders._"
