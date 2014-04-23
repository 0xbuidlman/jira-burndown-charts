package jira.charts.jsonwrappers

import spray.json.DefaultJsonProtocol

case class MinMaxAvgOutputContainer(average: List[SprintBurndownSummary], min: List[SprintBurndownSummary], max: List[SprintBurndownSummary])

object MinMaxAvgOutputContainerProtocol extends DefaultJsonProtocol {
  import SprintAverageProtocol._
  import SprintBurndownSummaryProtocol._
  implicit val outputContainerFormat = jsonFormat3(MinMaxAvgOutputContainer.apply)
}
