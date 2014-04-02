package jira.burndown.jsonwrappers

import spray.json.DefaultJsonProtocol

case class SprintBurndownSummary(sprintName: String, date: String, points: Int)

object SprintBurndownSummaryProtocol extends DefaultJsonProtocol {
  implicit val sprintFormat = jsonFormat3(SprintBurndownSummary.apply)
}