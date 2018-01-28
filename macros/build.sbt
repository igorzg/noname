scalaVersion := "2.12.4"

name := "macros"
organization := "com.noname"

version := "1.0"

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.bintrayIvyRepo("scalameta", "maven")

libraryDependencies ++= Seq(
  "org.scala-lang" % "scala-reflect" % "2.12.4",
  "org.scalameta" %% "scalameta" % "2.1.2"
)

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full)