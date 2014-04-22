package jira.charts

import akka.actor.{ActorContext, Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import jira.charts.service.RouteDispatcherActor
import java.security.cert.X509Certificate
import jira.charts.tracking.TrackingAPI

object Main extends App {

  prepareEmptyCertificateManagement

  implicit val system = ActorSystem()

  val api = new ApiActors(system)

  // create and start our service actor
  val service = system.actorOf(Props(new RouteDispatcherActor{
   val trackingApi = api.trackingApi
  }), "route-service")

  // start a new HTTP server on port 8080 with our service actor as the handler
  IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = 8080)


  //ignore secure stuff for now
  import javax.net.ssl.{TrustManager, HttpsURLConnection, X509TrustManager, SSLContext}

  def prepareEmptyCertificateManagement = {
    val trustManger: Array[TrustManager] = Array(
      new X509TrustManager() {
        override def checkServerTrusted(certs: Array[X509Certificate], authType: String) = ()

        override def checkClientTrusted(certs: Array[X509Certificate], authType: String) = ()

        override def getAcceptedIssuers: Array[X509Certificate] = null
      }
    )

    // Activate the new trust manager
    try {
      val sc = SSLContext.getInstance("SSL")
      sc.init(null, trustManger, new java.security.SecureRandom())
      HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory)
    } catch {
      case ex: Throwable => "boo hoo something went wrong..."
    }
  }
}
class ApiActors(system: ActorSystem){
  val trackingApi = system.actorOf(Props(new TrackingAPI), "TrackingAPI")

}