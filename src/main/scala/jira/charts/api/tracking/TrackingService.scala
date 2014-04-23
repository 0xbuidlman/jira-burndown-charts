package jira.charts.api.tracking

import spray.routing.HttpService
import jira.charts.service.{DefaultTimeout, JsonService}
import jira.charts.jsonwrappers.TrackingOutputContainer
import spray.http.MediaTypes._
import scala.io.Source
import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext
import reflect.ClassTag

trait TrackingService extends HttpService with JsonService with DefaultTimeout{
  def trackingApi: ActorRef



  //  val api = new TrackingAPI
  val trackingRoute =
    path("track" / "api" / Segment) {
      project: String => {
        get {
          complete {
            import ExecutionContext.Implicits.global
            import jira.charts.jsonwrappers.TrackingOutputContainerProtocol._
            val future = (trackingApi ? project).mapTo[TrackingOutputContainer]
            future map {
              response: TrackingOutputContainer => response
            }
          }
        }
      }
    } ~
      path("track" / Segment) {
        product: String => {
          respondWithMediaType(`text/html`) {
            complete {
              val stream = getClass.getResourceAsStream("/track.html")
              val result = Source.fromInputStream(stream).getLines().mkString
              stream.close()
              result
            }
          }
        }
      }
}
