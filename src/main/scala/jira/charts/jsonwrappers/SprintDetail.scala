package jira.charts.jsonwrappers

import spray.json.DefaultJsonProtocol

case class SprintDetail(id: Int, name: String, closed: Boolean, startDate: String, endDate: String, completeDate: String)

object SprintDetailProtocol extends DefaultJsonProtocol {
  implicit val sprintDetailProtocol = jsonFormat6(SprintDetail.apply)
}