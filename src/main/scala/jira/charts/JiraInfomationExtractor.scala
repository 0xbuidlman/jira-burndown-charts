package jira.charts

import jira.charts.jsonwrappers._
import scala.io.Source
import spray.json.{JsObject, JsonParser, JsValue}
import jira.charts.jsonwrappers.ProjectHeader
import jira.charts.jsonwrappers.SprintHeader
import com.typesafe.config.Config
import jira.charts.config.JiraChartingConfig

class JiraInfomationExtractor {
  this: ApiReader =>

  def getProject(name: String): ProjectHeader = {
    val projectSource = getRapidboardJson(name)
    val projectsJson: JsValue = JsonParser(projectSource)

    import ProjectHeaderProtocol._

    val projectList = projectsJson.asJsObject.getFields("views")
    val headers = projectList flatMap {
      jsValue: JsValue => jsValue.convertTo[List[JsObject]]
    } map {
      jsObject: JsObject => jsObject.convertTo[ProjectHeader]
    }

    headers find {
      header: ProjectHeader => header.name == name
    } getOrElse NoProjectHeader.NO_PROJECT_HEADER

  }

  def getSprints(projectId: Int): Seq[SprintHeader] = {
    val sprintSource = getSprintListJson(projectId)
    val sprintJson: JsValue = JsonParser(sprintSource)

    import SprintHeaderProtocol._

    val sprintList = sprintJson.asJsObject.getFields("sprints")
    val listOfSprints = sprintList flatMap {
      jsValue: JsValue => jsValue.convertTo[List[JsObject]]
    } map {
      jsObject: JsObject => jsObject.convertTo[SprintHeader]
    }
    listOfSprints filter {
      sprint => sprint.closed
    }
  }

  def extractSprintDetails(projectId: Int, sprintId: Int): Sprint = {
    import SprintProtocol._
    val detailSource = getSprintDetailsJson(projectId, sprintId)
    JsonParser(detailSource).convertTo[Sprint]
  }

  def movingAverage(items: List[SprintBurndownSummary], period: Int): List[SprintAverage] = {

    def addEmptyItems(items: List[SprintBurndownSummary], period: Int): List[SprintBurndownSummary] = period match {
      case 1 => items
      case n => SprintBurndownSummary(items.head.sprintName, items.head.date, items.head.points) :: addEmptyItems(items, period - 1)
    }

    def calculateAverage(items: List[SprintBurndownSummary], period: Int): List[SprintAverage] = items match {
      case head::tail if items.length >= period => {
        val itemsInWindow = items take period
        SprintAverage(itemsInWindow.head.sprintName, itemsInWindow.last.sprintName, average(itemsInWindow), itemsInWindow.last.sprintName) :: calculateAverage(tail, period)
      }
      case _ => Nil
    }


    val withEmptyItems = addEmptyItems(items, period)

    calculateAverage(withEmptyItems, period)


  }
  def average(items: List[SprintBurndownSummary]): Double = {
    val points = items map {item: SprintBurndownSummary => item.points }
    points.sum.toDouble / items.length
  }
}
