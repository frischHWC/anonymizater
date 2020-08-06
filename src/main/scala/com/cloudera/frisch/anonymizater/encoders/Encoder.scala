package com.cloudera.frisch.anonymizater.encoders

import org.apache.log4j.Logger
import org.apache.spark.sql.expressions.UserDefinedFunction

trait Encoder {

  @transient lazy val logger = Logger.getLogger(getClass.getName)

  def encodeUdf: UserDefinedFunction

}
