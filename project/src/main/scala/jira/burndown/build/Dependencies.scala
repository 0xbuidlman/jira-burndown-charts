package src.main.scala.jira.burndown.build

import sbt._
import Keys._


object Versions {

  val Akka = "2.3.0"
  val Spray = "1.3.0"

}


object Dependencies {

  // helper Seq's which define sope for you
  /** Seq of "test" scoped dependencies */
  def TestSeq(deps: ModuleID*) = deps.map(_ % "test")

  /** Seq of "it,test" (it = integration tests) scoped dependencies */
  def TestItSeq(deps: ModuleID*) = deps.map(_ % "test,it")

  // dependencies -------------------------------------------------------------

  // akka
  val akkaActor   = "com.typesafe.akka" %% "akka-actor"   % Versions.Akka
  val akkaCamel   = "com.typesafe.akka" %% "akka-camel"   % Versions.Akka
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % Versions.Akka
  val akkaSlf4j   = "com.typesafe.akka" %% "akka-slf4j"   % Versions.Akka
  val akkaAll     = Seq(akkaActor, akkaTestkit, akkaCamel, akkaSlf4j)

  // spray

  val sprayCan     = "io.spray"            %   "spray-can"     % Versions.Spray
  val sprayJson    = "io.spray"           %%   "spray-json"    % "1.2.5"
  val sprayRouting = "io.spray"            %   "spray-routing" % Versions.Spray
  val sprayTestkit = "io.spray"            %   "spray-testkit" % Versions.Spray
  val sprayAll = Seq(sprayCan, sprayRouting, sprayJson, sprayTestkit)

  // camel
  val camelFtp = "org.apache.camel" % "camel-ftp" % "2.12.2"
  val camelAll = Seq(camelFtp)


  val jsoup     = "org.jsoup"          % "jsoup"       % "1.7.2"

  val apacheIo  = "org.apache.commons" % "commons-io"  % "1.3.2"
  val guava     = "com.google.guava"   % "guava"       % "14.0.1"

  val scalaz    = "org.scalaz"        %% "scalaz-core" % "7.0.5"

  val typesafeLogging = "com.typesafe"   %% "scalalogging-slf4j"  % "1.0.1"
  val typesafeConfig  = "com.typesafe"    % "config"              % "1.0.2"
  val log4jOverSlf4j  = "org.slf4j"       % "log4j-over-slf4j"    % "1.7.2"
  val log4jSimple     = "org.slf4j"       % "slf4j-simple"        % "1.7.2"
  val log4j           = "log4j"           % "log4j"               % "1.2.17"


  val scalateVersion  = "1.6.1"

  val macWireMacros  = "com.softwaremill.macwire" %% "macros" % "0.5"
  val macWireRuntime = "com.softwaremill.macwire" %% "runtime" % "0.5"

  val asyncHttpClient = "com.ning" % "async-http-client" % "1.7.22"

  // joda time
  val jodaTime        = "joda-time" % "joda-time"    % "2.1"
  val jodaTimeConvert = "org.joda"  % "joda-convert" % "1.2"

  val jodaTimeAll = Seq(jodaTime, jodaTimeConvert)

  val jsr305    = "com.google.code.findbugs" % "jsr305" % "1.3.9"

  // testing
  val scalaTest = "org.scalatest" %% "scalatest"      % "2.0"
  val mockito   = "org.mockito"    % "mockito-all"    % "1.9.5"

  // internal stuffs


  // groups of dependencies ---------------------------------------------------

  val logging = Seq(typesafeLogging, log4jSimple, log4jOverSlf4j)
  val testingAll = TestSeq(scalaTest, mockito)
  val testingIt = TestItSeq(scalaTest, mockito)
}
