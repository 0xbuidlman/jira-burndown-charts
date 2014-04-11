package jira.charts.tracking

import spray.routing.HttpService
import jira.charts.service.JsonService
import jira.charts.jsonwrappers.SprintBurndownSummary
import jira.charts.jsonwrappers.SprintBurndownSummaryProtocol._
import jira.charts.jsonwrappers.OutputContainerProtocol._
import spray.http.MediaTypes._
import scala.io.Source

trait TrackingService extends HttpService with JsonService {
  val api = new TrackingAPI
  val trackingRoute =
    path("track" / Segment) {
      pproduct: String => {
              respondWithMediaType(`text/html`){
                complete{
                  val stream = getClass.getResourceAsStream("/track.html")
                  val result = Source.fromInputStream(stream).getLines().mkString
                  stream.close()
                  result
                }
              }
            }
    } ~
    path("track" / "api" / Segment) {
      projectName: String =>
        complete {
//          List(SprintBurndownSummary("Test", "test", 2))
          api.getSprintSummaryInformationJson(projectName)
        }
    }

}
