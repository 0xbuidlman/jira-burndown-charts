package jira.charts.jsonwrappers

import spray.json.DefaultJsonProtocol

case class TrackingOutputContainer(sprintBurndownSummaries: List[SprintBurndownSummary], averages: List[SprintAverage], sprintBurndownSummariesLabel: String, averagesLabel: String)

object TrackingOutputContainerProtocol extends DefaultJsonProtocol {
  import SprintAverageProtocol._
  import SprintBurndownSummaryProtocol._
  implicit val outputContainerFormat = jsonFormat4(TrackingOutputContainer.apply)
}
