package jira.charts.api.tracking

import jira.charts.JiraInfomationExtractor
import jira.charts.jsonwrappers.{MinMaxAvgOutputContainer, SprintBurndownSummary, ApiReader}
import jira.charts.config.DefaultConfig
import akka.actor.Actor

class BurnupApi extends Actor{


  def getBurnupByHardNumberJson(projectName: String, total: Int): MinMaxAvgOutputContainer = {
    val jiraInfomrationExtractor = new JiraInfomationExtractor with ApiReader with DefaultConfig

    println("Burnup")
    val project = jiraInfomrationExtractor.getProject(projectName)
    val sprints = jiraInfomrationExtractor.getSprints(project.id)

    val items = sprints map {
      sprint =>
        val sprintDetails = jiraInfomrationExtractor.extractSprintDetails(project.id, sprint.id)
        SprintBurndownSummary(sprintDetails.sprint.name, sprintDetails.sprint.completeDate, sprintDetails.contents.completedIssuesEstimateSum.value) // todo
    }
    val pointsToConsider = items takeRight(4) map {item: SprintBurndownSummary => item.points } sorted
    val average = jiraInfomrationExtractor.average(items.takeRight(4).toList)
    val max = pointsToConsider.last
    val min = pointsToConsider.head

    def makeProjections(points: Int, total: Int, sprintName: String, pointsCount: Int, sprintNum: Int = 1): List[SprintBurndownSummary] = {
      points match {
        case _ if points >= total => {
          SprintBurndownSummary(s"""$sprintName $sprintNum ($total)""", "", pointsCount) :: Nil
        }
        case _ => {
          SprintBurndownSummary(s"""$sprintName $sprintNum ($total)""", "", pointsCount) :: makeProjections(points, total-points, sprintName, pointsCount + points, sprintNum + 1)
        }

      }
    }


    val averageSprintsRemaining = makeProjections(average.floor.toInt, total, "Avg", 23)
    val minSprintsRemaining =  makeProjections(min.toInt, total, "Min", 23)
    val maxSprintsRemaining =  makeProjections(max.toInt, total, "Max", 23)

    val averageSprints = items.toList ::: averageSprintsRemaining
    val minSprints = items.toList ::: minSprintsRemaining
    val maxSprints = items.toList ::: maxSprintsRemaining

    MinMaxAvgOutputContainer(averageSprints, minSprints, maxSprints)
  }

  override def receive = {
    case  (project: String, total: Int) => sender ! getBurnupByHardNumberJson(project, total)
    case _ => sender ! "Error. I dont know what to do with that!"
  }
}
