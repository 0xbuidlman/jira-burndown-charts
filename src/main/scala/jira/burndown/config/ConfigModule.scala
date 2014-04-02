package jira.burndown.config

import com.typesafe.config.Config

trait ConfigModule {

  def conf: Config

  lazy val config = new JiraChartingConfig(conf)
}

