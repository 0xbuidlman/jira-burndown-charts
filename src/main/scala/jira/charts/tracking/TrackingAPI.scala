package jira.charts.tracking

import jira.charts.JiraInfomationExtractor
import jira.charts.jsonwrappers.{OutputContainer, SprintBurndownSummary, ApiReader}
import jira.charts.config.DefaultConfig
import java.util.Date

class TrackingAPI {

  def getSprintSummaryInformationJson(projectName: String): OutputContainer = {
    val jiraInfomrationExtractor = new JiraInfomationExtractor with ApiReader with DefaultConfig

    println(s"""trying $projectName""")
    println(new Date(System.currentTimeMillis()))
    val project = jiraInfomrationExtractor.getProject(projectName)
    val sprints = jiraInfomrationExtractor.getSprints(project.id)

    val items = sprints map {
      sprint =>
        val sprintDetails = jiraInfomrationExtractor.extractSprintDetails(project.id, sprint.id)
        SprintBurndownSummary(sprintDetails.sprint.name, sprintDetails.sprint.completeDate, sprintDetails.contents.completedIssuesEstimateSum.value) // todo
    }

    println(project)
    println(projectName)
    println(sprints)
    println(new Date(System.currentTimeMillis()))

    OutputContainer(items.toList, jiraInfomrationExtractor.movingAverage(items.toList, 4), "Completed", "Average")
  }
}
