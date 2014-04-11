package jira.charts.service

import spray.json.DefaultJsonProtocol
import spray.httpx.SprayJsonSupport
import spray.routing.HttpServiceActor
import jira.charts.JiraInfomationExtractor
import jira.charts.jsonwrappers.SprintBurndownSummary
import jira.charts.tracking.TrackingService

class RouteDispatcherActor extends HttpServiceActor with TrackingService {

  def receive = runRoute(trackingRoute)

}

/** Mixes in things used in all services - marshalling etc. */
trait JsonService
  extends DefaultJsonProtocol
  with SprayJsonSupport
