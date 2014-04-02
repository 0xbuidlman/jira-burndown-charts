package jira.burndown.jsonwrappers

import spray.json.DefaultJsonProtocol

case class OutputContainer(sprintBurndownSummaries: List[SprintBurndownSummary], averages: List[SprintAverage], sprintBurndownSummariesLabel: String, averagesLabel: String)

object OutputContainerProtocol extends DefaultJsonProtocol {
  import SprintAverageProtocol._
  import SprintBurndownSummaryProtocol._
  implicit val outputContainerFormat = jsonFormat4(OutputContainer.apply)
}
