package jira.charts

import org.scalatest.{FlatSpec, FlatSpecLike, ShouldMatchers, FunSuite}
import jira.charts.jsonwrappers._

class JiraInfomationExtractorTest extends FlatSpec
  with FlatSpecLike
  with ShouldMatchers {

  val information = new JiraInfomationExtractor with MockApiReader
  val sprintFiveId: Int = 2837
  val projectId = 2676
  val projectName = "Large Merchant Integration"

  val sprint1 = SprintBurndownSummary("Sprint 1", "", 2)
  val sprint2 = SprintBurndownSummary("Sprint 2", "", 4)
  val sprint3 = SprintBurndownSummary("Sprint 3", "", 6)
  val sprint4 = SprintBurndownSummary("Sprint 4", "", 8)

  it should "give a project from the json" in {
    
    //when
    val project = information.getProject(projectName)

    //then
    project.name should equal (projectName)
    project.id should equal (projectId)
  }

  it should "find the sprint list for a given project id" in {

    //when
    val sprints = information.getSprints(projectId)

    //then
    sprints.length should equal(6)
    sprints(5).name should equal("Sprint 5")
    sprints(5).id should equal(sprintFiveId)
  }
  
  it should "give the number of points completed in sprint 0" in {

    //given
    //when
    val sprintDetails = information.extractSprintDetails(projectId, sprintFiveId)
    
    //then
    sprintDetails.sprint.id should equal(sprintFiveId)
    sprintDetails.contents.completedIssuesEstimateSum.value should equal(18)
  }
  it should "calculate the average of 4 sprints with period 4" in {

    //when
    val averages = information.movingAverage(List(sprint1, sprint2, sprint3, sprint4), 4)

    //then
    val points = averages map { a => a.points }
    points(0) should equal(2)
    points(1) should equal(2.5)
    points(2) should equal(3.5)
    points(3) should equal(5)
  }
  it should "calculage averages of 4 sprints with a period of 2" in {

    //when
    val averages = information.movingAverage(List(sprint1, sprint2, sprint3, sprint4), 2)

    //then
    val points = averages map { a => a.points }
    points(0) should equal(2)
    points(1) should equal(3)
    points(2) should equal(5)
    points(3) should equal(7)
  }
}
