package jira.burndown.jsonwrappers

import spray.json.DefaultJsonProtocol

case class ProjectHeader(id: Int, name: String, canEdit: Boolean, sprintSupportEnabled: Boolean)

object NoProjectHeader {
  lazy val NO_PROJECT_HEADER = ProjectHeader(-1, "NOT FOUND", false, false)
}
object ProjectHeaderProtocol extends DefaultJsonProtocol {
  implicit val projectListFormat = jsonFormat4(ProjectHeader.apply)
}

