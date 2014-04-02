package jira.burndown

import java.security.cert.X509Certificate
import spray.json._
import com.google.common.io.Files
import com.google.common.base.Charsets
import java.io.File
import jira.burndown.jsonwrappers._
import jira.burndown.jsonwrappers.SprintBurndownSummary
import jira.burndown.jsonwrappers.OutputContainer
import jira.burndown.config.DefaultConfig


object Runner extends App with DefaultConfig{

  prepareEmptyCertificateManagement


  val jiraInfomrationExtractor = new JiraInfomationExtractor with ApiReader with DefaultConfig
  
  println("Looking for " + config.projectName)
  val project = jiraInfomrationExtractor.getProject(config.projectName)
  println("Collecting sprints...")
  val sprints = jiraInfomrationExtractor.getSprints(project.id)

  val sb = new StringBuilder()
  println("Processing...")
  import OutputContainerProtocol._
  val items = sprints map { sprint =>
    val sprintDetails = jiraInfomrationExtractor.extractSprintDetails(project.id, sprint.id)
    SprintBurndownSummary(sprintDetails.sprint.name, sprintDetails.sprint.completeDate, sprintDetails.contents.completedIssuesEstimateSum.value) // todo
  }

  val output =  OutputContainer(items.toList, jiraInfomrationExtractor.movingAverage(items.toList, 4), "Completed", "Average")

  sb.append(output.toJson)
  println("done")
  Files.write(sb.toString(), new File("burndown.json"), Charsets.UTF_8)


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

