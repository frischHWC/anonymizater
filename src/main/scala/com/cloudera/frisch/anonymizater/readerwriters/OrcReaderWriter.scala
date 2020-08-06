package com.cloudera.frisch.anonymizater.readerwriters

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object OrcReaderWriter extends Readerwriter {

  def write(path: String, df: DataFrame) {
    df
      .write
      .mode(SaveMode.Append)
      .orc(path)
  }

  def read(path: String, spark: SparkSession) : DataFrame = {
    spark
      .read
      .orc(path)

  }

}
