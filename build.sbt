name := "yuubin"

version := "0.1"

scalaVersion := "2.11.8"

resolvers ++= Seq()

libraryDependencies ++= Seq(
  "javax.mail"              % "mail"            % "1.4.7",
  "dnsjava"                 % "dnsjava"         % "2.1.7",
  "ch.qos.logback"          % "logback-classic" % "1.1.7",
  "ch.qos.logback"          % "logback-core"    % "1.1.7",
  "org.slf4j"               % "slf4j-api"       % "1.7.21",
  "org.jvnet.mock-javamail" % "mock-javamail"   % "1.9"       % "test",
  "org.scalatest"           % "scalatest_2.11"	% "2.2.6"	    % "test"
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

lazy val `it-config-sbt-project` = project.in(file(".")).configs(IntegrationTest).settings( Defaults.itSettings : _*).
  settings( libraryDependencies ++= itLibraryDependencies )

lazy val itLibraryDependencies = Seq(
  "javax.mail"              % "mail"            % "1.4.7",
  "dnsjava"                 % "dnsjava"         % "2.1.7",
  "ch.qos.logback"          % "logback-classic" % "1.1.7",
  "ch.qos.logback"          % "logback-core"    % "1.1.7",
  "org.slf4j"               % "slf4j-api"       % "1.7.21",
  "org.jvnet.mock-javamail" % "mock-javamail"   % "1.9"       % "test",
  "org.scalatest"           % "scalatest_2.11"  % "2.2.6"     % "test"
)
