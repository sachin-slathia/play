name := """Assignment"""
organization := "knoldus"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.11"

javaOptions in Test += "-Dconfig.file=conf/test.conf"

libraryDependencies ++= Seq (
  guice,
  ehcache
)

libraryDependencies +="com.typesafe.play" % "play-slick_2.11" % "3.0.0"
libraryDependencies +="com.typesafe.play" % "play-slick-evolutions_2.11" % "3.0.0"
libraryDependencies +="com.h2database" % "h2" % "1.4.196"
libraryDependencies +="mysql" % "mysql-connector-java" % "5.1.35"




// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.example.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.example.binders._"
