name := "slf4j-logman"

version := "1.0.0"

organization := "com.codingrobot"

description := "Run-time log level management. Can be run within a HTTP service to allow chaning log levels over REST"

homepage := Some(url("https://github.com/thecodingrobot/slf4j-logman"))

licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

scmInfo := Some(ScmInfo(url("https://github.com/thecodingrobot/slf4j-logman"), "scm:git:git@github.com:thecodingrobot/slf4j-logman.git"))

scalaVersion := "2.11.8"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8", "-feature",
  "-Xfatal-warnings", "-Xlint:private-shadow", "-unchecked",
  "-language:reflectiveCalls", "-language:postfixOps", "-language:implicitConversions",
  "-Ywarn-unused-import")

publishTo := {
  val nexus = "http://nexus.ovotech.org.uk:8081/nexus/content/repositories/"
  if (isSnapshot.value)
    Some("Ovo snapshots" at nexus + "snapshots")
  else
    Some("Ovo releases" at nexus + "releases")
}

val Versions = new {
  val akka = "2.4.9"
  val slf4j = "1.7.21"
  val logback = "1.1.7"
}

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core" % Versions.akka % "optional",
  "com.typesafe.akka" %% "akka-http-experimental" % Versions.akka % "optional",

  "org.slf4j" % "slf4j-api" % Versions.slf4j,
  "ch.qos.logback" % "logback-classic" % Versions.logback
)
