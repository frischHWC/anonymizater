package com.cloudera.frisch.anonymizater.readerwriters

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object JsonReaderWriter extends Readerwriter {

  def write(path: String, df: DataFrame) {
    df
      .write
      .mode(SaveMode.Append)
      .json(path)
  }

  def read(path: String, spark: SparkSession) : DataFrame = {
    spark
      .read
      .json(path)

  }

}
