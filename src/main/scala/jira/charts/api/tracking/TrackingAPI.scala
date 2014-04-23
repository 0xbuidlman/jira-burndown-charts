package jira.charts.api.tracking

import jira.charts.JiraInfomationExtractor
import jira.charts.jsonwrappers.{TrackingOutputContainer, SprintBurndownSummary, ApiReader}
import jira.charts.config.DefaultConfig
import java.util.Date
import akka.actor.Actor

class TrackingApi extends Actor{


  def getSprintSummaryInformationJson(projectName: String): TrackingOutputContainer = {
    val jiraInfomrationExtractor = new JiraInfomationExtractor with ApiReader with DefaultConfig

    val project = jiraInfomrationExtractor.getProject(projectName)
    val sprints = jiraInfomrationExtractor.getSprints(project.id)

    val items = sprints map {
      sprint =>
        val sprintDetails = jiraInfomrationExtractor.extractSprintDetails(project.id, sprint.id)
        SprintBurndownSummary(sprintDetails.sprint.name, sprintDetails.sprint.completeDate, sprintDetails.contents.completedIssuesEstimateSum.value) // todo
    }

    TrackingOutputContainer(items.toList,
      jiraInfomrationExtractor.movingAverage(items.toList, 4),
      "Completed",
      "Average")
  }

  override def receive = {
    case project: String => sender ! getSprintSummaryInformationJson(project)
    case _ => sender ! "Error. I dont know what to do with that!"
  }
}
