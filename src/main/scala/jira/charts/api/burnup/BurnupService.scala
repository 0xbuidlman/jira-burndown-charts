package jira.charts.api.tracking

import spray.routing.HttpService
import jira.charts.service.{DefaultTimeout, JsonService}
import jira.charts.jsonwrappers.{MinMaxAvgOutputContainer, TrackingOutputContainer}
import spray.http.MediaTypes._
import scala.io.Source
import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext
import reflect.ClassTag

trait BurnupService extends HttpService with JsonService with DefaultTimeout{
  def burnupApi: ActorRef



  //  val api = new TrackingAPI
  val burnupRoute =
    path("burnup" / "api" / Segment) {
      project: String => {
        get {
          complete {
            import ExecutionContext.Implicits.global
            import jira.charts.jsonwrappers.MinMaxAvgOutputContainerProtocol._
            val future = (burnupApi ? (project, 200)).mapTo[MinMaxAvgOutputContainer]
            future map {
              response: MinMaxAvgOutputContainer => response
            }
          }
        }
      }
    } ~
      path("burnup" / Segment) {
        product: String => {
          respondWithMediaType(`text/html`) {
            complete {
              val stream = getClass.getResourceAsStream("/burnup.html")
              val result = Source.fromInputStream(stream).getLines().mkString
              stream.close()
              result
            }
          }
        }
      }
}
