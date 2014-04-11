package jira.charts.jsonwrappers

import scala.io.Source
import jira.charts.config.DefaultConfig

trait ApiReader extends DefaultConfig  {

  def getRapidboardJson(projectName: String): String = {
    Source.fromURL(config.rapidBoardJsonUrl).getLines().mkString("")
  }

  def getSprintListJson(projectId: Int): String = {
    Source.fromURL(s"${config.sprintJsonUrl}/${projectId}").getLines().mkString("")
  }

  def getSprintDetailsJson(projectId: Int, sprintId: Int): String = {
    Source.fromURL(s"${config.sprintDetailJsonUrl}?rapidViewId=${projectId}&sprintId=${sprintId}").getLines().mkString("")
  }
}
