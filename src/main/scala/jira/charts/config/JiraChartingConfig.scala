package jira.charts.config

import com.typesafe.config.Config

class JiraChartingConfig(val conf: Config) extends JiraChartingConfigProperties

trait JiraChartingConfigProperties extends HasConfig {
  val jiraChartingConfig = conf.getConfig("jiraCharting")

  val apiEndpoints = jiraChartingConfig.getConfig("apiEndpoints")
  val chartLabels = jiraChartingConfig.getConfig("chartLabels")

  lazy val projectName = jiraChartingConfig.getString("projectName")

  lazy val rapidBoardJsonUrl = apiEndpoints.getString("rapidBoardJsonUrl")
  lazy val sprintJsonUrl = apiEndpoints.getString("sprintJsonUrl")
  lazy val sprintDetailJsonUrl = apiEndpoints.getString("sprintDetailJsonUrl")

  lazy val sprintBurndownSummariesLabel = chartLabels.getString("sprintBurndownSummaries")
  lazy val averagesLabel = chartLabels.getString("averages")
}
