package jira.burndown

import org.scalatest.{FlatSpec, FlatSpecLike, ShouldMatchers, FunSuite}

class JiraInfomationExtractorTest extends FlatSpec
  with FlatSpecLike
  with ShouldMatchers {

  val information = new JiraInfomationExtractor with MockApiReader
  val sprintFiveId: Int = 2837
  val projectId = 2676
  val projectName = "Large Merchant Integration"

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
}
