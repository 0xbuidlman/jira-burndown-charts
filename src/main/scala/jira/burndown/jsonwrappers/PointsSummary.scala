package jira.burndown.jsonwrappers

import spray.json.DefaultJsonProtocol

case class PointsSummary(value: Int, text: String)

object PointsSummaryProtocol extends DefaultJsonProtocol {
  implicit val pointsSummaryProtocol = jsonFormat2(PointsSummary.apply)
}