package jira.burndown.jsonwrappers

import spray.json.DefaultJsonProtocol

case class SprintAverage(startSprint: String, endSprint: String, points: Double, sprintName: String)

object SprintAverageProtocol extends DefaultJsonProtocol {
  implicit val sprintAverageFormat = jsonFormat4(SprintAverage.apply)
}