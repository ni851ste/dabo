organization := "de.knxamk"

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "org.json" % "json" % "20190722"
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.12.2"
libraryDependencies += "org.javatuples" % "javatuples" % "1.2"
