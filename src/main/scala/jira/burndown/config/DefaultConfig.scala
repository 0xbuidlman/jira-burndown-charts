package jira.burndown.config

import com.typesafe.config.ConfigFactory

trait DefaultConfig {
  val config = new JiraChartingConfig(ConfigFactory.load)

}
