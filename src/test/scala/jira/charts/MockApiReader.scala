package jira.charts

import jira.charts.jsonwrappers.ApiReader
import scala.io.Source

trait MockApiReader extends ApiReader {

  override def getRapidboardJson(projectName: String): String = {
    """
      |{
      |
      |    "views": [
      |        {
      |            "id": 2676,
      |            "name": "Large Merchant Integration",
      |            "canEdit": true,
      |            "sprintSupportEnabled": true
      |        }
      |    ]
      |}
    """.stripMargin
  }

  override def getSprintListJson(projectId: Int): String = {
    """
      |{
      |
      |    "sprints": [
      |        {
      |            "id": 2557,
      |            "name": "Sprint 0",
      |            "closed": true
      |        },
      |        {
      |            "id": 2601,
      |            "name": "Goal 1 - Sprint 1",
      |            "closed": true
      |        },
      |        {
      |            "id": 2653,
      |            "name": "Sprint 2",
      |            "closed": true
      |        },
      |        {
      |            "id": 2759,
      |            "name": "Sprint 3",
      |            "closed": true
      |        },
      |        {
      |            "id": 2789,
      |            "name": "Sprint 4",
      |            "closed": true
      |        },
      |        {
      |            "id": 2837,
      |            "name": "Sprint 5",
      |            "closed": true
      |        },
      |        {
      |            "id": 2884,
      |            "name": "Sprint 6",
      |            "closed": false
      |        }
      |    ],
      |    "rapidViewId": 2676
      |
      |}
    """.stripMargin
  }

  override def getSprintDetailsJson(projectId: Int, sprintId: Int): String = {
    """
      |{
      |
      |    "contents": {
      |        "completedIssues": [
      |
      |        ],
      |        "incompletedIssues": [
      |
      |        ],
      |        "puntedIssues": [ ],
      |        "completedIssuesEstimateSum": {
      |            "value": 18,
      |            "text": "18.0"
      |        },
      |        "incompletedIssuesEstimateSum": {
      |            "value": 10,
      |            "text": "10.0"
      |        },
      |        "allIssuesEstimateSum": {
      |            "value": 28,
      |            "text": "28.0"
      |        },
      |        "puntedIssuesEstimateSum": {
      |            "value": 0,
      |            "text": "0.0"
      |        },
      |        "issueKeysAddedDuringSprint": {
      |            "EPDLMI-63": true,
      |            "EPDLMI-75": true
      |        }
      |    },
      |    "sprint": {
      |        "id": 2837,
      |        "name": "Sprint 5",
      |        "closed": true,
      |        "startDate": "21/Mar/14 11:20 AM",
      |        "endDate": "03/Apr/14 12:20 PM",
      |        "completeDate": "27/Mar/14 11:05 AM"
      |    }
      |
      |}
    """.stripMargin
  }
}
