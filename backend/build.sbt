name := """Play_Server"""
organization := "de.knxamk"

version := "0.0.1-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.8"

libraryDependencies += guice

// https://mvnrepository.com/artifact/org.json/json
libraryDependencies += "org.json" % "json" % "20190722"

