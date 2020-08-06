package com.cloudera.frisch.anonymizater.readerwriters

import org.apache.log4j.Logger
import org.apache.spark.sql.{DataFrame, SparkSession}

trait Readerwriter {

  @transient lazy val logger = Logger.getLogger(getClass.getName)

  def write(path: String, df: DataFrame)

  def read(path: String, spark: SparkSession) : DataFrame

}
