import AssemblyKeys._

import spray.revolver.RevolverPlugin._

assemblySettings

name := "yuubin"

version := "0.1"

scalaVersion := "2.11.1"

resolvers ++= Seq(
)

libraryDependencies ++= Seq(
  "javax.mail" 		% "mail" 		% "1.4.7",
  "dnsjava"		% "dnsjava"		% "2.1.6",
  "ch.qos.logback"	% "logback-classic"	% "1.0.13",
  "ch.qos.logback"	% "logback-core"	% "1.0.13",
  "org.slf4j"		% "slf4j-api"		% "1.7.5",
  "junit"               % "junit"		% "4.10"	% "test",
  "org.scalatest" 	% "scalatest_2.11"	% "2.2.0"	% "test"
)

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-unchecked",
  "-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-value-discard",
  "-Xfuture" )

