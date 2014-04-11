package jira.charts.jsonwrappers

import spray.json.DefaultJsonProtocol

case class SprintHeader(id: Int, name: String, closed: Boolean)

object SprintHeaderProtocol extends DefaultJsonProtocol {
  implicit val sprintListFormat = jsonFormat3(SprintHeader.apply)
}