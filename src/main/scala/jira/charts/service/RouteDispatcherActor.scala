package jira.charts.service

import spray.json.DefaultJsonProtocol
import spray.httpx.SprayJsonSupport
import spray.routing.HttpServiceActor
import jira.charts.api.tracking.{BurnupService, TrackingService}
import akka.actor.ActorRef
import akka.util.Timeout
import java.util.concurrent.TimeUnit

abstract class RouteDispatcherActor extends HttpServiceActor
  with TrackingService
  with BurnupService {


  def trackingApi: ActorRef
  def burnupApi: ActorRef
  def receive = runRoute(trackingRoute ~ burnupRoute)

}

/** Mixes in things used in all services - marshalling etc. */
trait JsonService
  extends DefaultJsonProtocol
  with SprayJsonSupport

trait DefaultTimeout {
  implicit val timeout = Timeout(50000, TimeUnit.SECONDS)
}
