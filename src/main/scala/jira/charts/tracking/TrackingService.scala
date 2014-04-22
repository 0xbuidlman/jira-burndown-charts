package jira.charts.tracking

import spray.routing.HttpService
import jira.charts.service.JsonService
import jira.charts.jsonwrappers.OutputContainer
import spray.http.MediaTypes._
import scala.io.Source
import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import java.util.concurrent.TimeUnit
import scala.concurrent.ExecutionContext
import reflect.ClassTag

trait TrackingService extends HttpService with JsonService {
  def trackingApi: ActorRef

  implicit val timeout = Timeout(50000, TimeUnit.SECONDS)


  //  val api = new TrackingAPI
  val trackingRoute =
    path("track" / "api" / Segment) {
      project: String => {
        get {
          complete {
            import ExecutionContext.Implicits.global
            import jira.charts.jsonwrappers.OutputContainerProtocol._
            val future = (trackingApi ? project).mapTo[OutputContainer]
            future map {
              response: OutputContainer => response
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
