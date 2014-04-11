package jira.charts.jsonwrappers

import spray.json.DefaultJsonProtocol

case class Sprint(contents: SprintContents, sprint: SprintDetail)

object SprintProtocol extends DefaultJsonProtocol {
  import SprintContentsProtocol._
  import SprintDetailProtocol._
  implicit val sprintFormat = jsonFormat2(Sprint.apply)
}
