import sbt._
import Keys._

import src.main.scala.jira.burndown.build._
import src.main.scala.jira.burndown.build.Dependencies._
import src.main.scala.jira.burndown.build.ScalariformSettings._
import com.typesafe.sbt.SbtScalariform._
import sbt.Keys._
import scala.Some
import scalariform.formatter.preferences

import sbtscalaxb.Plugin._
import ScalaxbKeys._

import sbtassembly.Plugin._
import sbtassembly.AssemblyUtils._
import AssemblyKeys._

import ScctPlugin._
import spray.revolver.RevolverPlugin._

import net.virtualvoid.sbt._

object ApplicationBuild extends Build {

  val appName         = """jira-charts"""
  val appVersion      = "0.0.1-SNAPSHOT"
  val appScalaVersion = "2.10.3"

  val generalDependencies = Seq(
    scalaz,
    guava, jsr305,
    typesafeConfig,
    macWireMacros,
    macWireRuntime
  ) ++ logging ++ jodaTimeAll ++ akkaAll ++ testingAll

  val projectResolvers = Seq(
    "spray repo" at "http://repo.spray.io/",
    "scct" at "http://mtkopone.github.io/scct/maven-repo",
    "Typesafe repo" at "http://repo.typesafe.com/typesafe/repo/"

  )

  val defaultSettings = Seq(
    scalaVersion := appScalaVersion,
    resolvers ++= projectResolvers
  ) ++ ScalariformSettings.settings



  val common = Project("common", file("common"))
   .configs(IntegrationTest)
   .settings(defaultSettings: _*)
   .settings(ScctPlugin.instrumentSettings: _*)
   .settings(graph.Plugin.graphSettings: _*)
   .settings(Revolver.settings: _*)
   .settings(
     libraryDependencies ++= generalDependencies ++
       camelAll ++
       Seq(typesafeConfig) ++ Seq()
   )

  val main = Project(appName, file("."))
    .configs(IntegrationTest)
    .settings(defaultSettings: _*)
    .settings(Defaults.itSettings: _*)
    .settings(ScctPlugin.instrumentSettings: _*)
    .settings(Revolver.settings: _*)
    .settings(resourceDirectory in ScctPlugin.Scct <<= resourceDirectory in Test) // fix scct not copying resources by default
    .settings(graph.Plugin.graphSettings: _*)
    .settings(
      name := appName,
      mainClass := Some("jira.charts.Runner"),
      mainClass in assembly := Some("jira.charts.runner"),
        libraryDependencies ++= generalDependencies ++
        testingIt ++
        sprayAll ++
        Seq()
  ) dependsOn (
    common
  ) aggregate(common)

}
