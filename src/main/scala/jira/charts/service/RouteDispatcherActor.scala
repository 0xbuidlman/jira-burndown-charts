package jira.charts.service

import spray.json.DefaultJsonProtocol
import spray.httpx.SprayJsonSupport
import spray.routing.HttpServiceActor
import jira.charts.tracking.TrackingService
import akka.actor.ActorRef

abstract class RouteDispatcherActor extends HttpServiceActor with TrackingService {
  def trackingApi: ActorRef
  def receive = runRoute(trackingRoute)

}

/** Mixes in things used in all services - marshalling etc. */
trait JsonService
  extends DefaultJsonProtocol
  with SprayJsonSupport
