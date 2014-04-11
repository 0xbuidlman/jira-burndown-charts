package jira.charts.config

import com.typesafe.config.Config

trait HasConfig {
  def conf: Config
}
