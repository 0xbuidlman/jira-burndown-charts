package jira.charts.jsonwrappers

import spray.json.DefaultJsonProtocol

case class SprintContents(completedIssuesEstimateSum: PointsSummary, allIssuesEstimateSum: PointsSummary)

object SprintContentsProtocol extends DefaultJsonProtocol {
  import PointsSummaryProtocol._
  implicit val sprintContentProtocol = jsonFormat2(SprintContents.apply)
}

