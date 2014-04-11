package src.main.scala.jira.burndown.build


import sbt._
import Keys._

import com.typesafe.sbt.SbtScalariform._
import scalariform.formatter.preferences.PreferencesImporterExporter

object ScalariformSettings {

  val settings =
    defaultScalariformSettings ++ Seq(
      ScalariformKeys.preferences <<= baseDirectory.apply(getScalariformPreferences)
    )

  def getScalariformPreferences(dir: File): scalariform.formatter.preferences.IFormattingPreferences = {
    val inProject = dir / "scalaFormatter.properties"

      if (inProject.exists)
        PreferencesImporterExporter.loadPreferences(inProject.getPath)
      else
        getScalariformPreferences(dir.getParentFile)
  }

}
