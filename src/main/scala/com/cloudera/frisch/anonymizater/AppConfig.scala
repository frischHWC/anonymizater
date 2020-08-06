package com.cloudera.frisch.anonymizater

import com.typesafe.config.{Config, ConfigFactory}


object AppConfig {

  val conf: Config = ConfigFactory.load()

  val name = conf.getString("appName")
  val master = conf.getString("master")
  
}