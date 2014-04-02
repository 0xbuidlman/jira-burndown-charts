import AssemblyKeys._

seq(Revolver.settings: _*)


lazy val buildSettings = Seq(
  version := "0.1-SNAPSHOT",
  organization := "jira.burndown",
  scalaVersion := "2.10.1"
)

val app = (project in file("project")).
  settings(buildSettings: _*)
  .settings(assemblySettings: _*)
  .settings(
    // your settings here
  )
