package com.cloudera.frisch.anonymizater.readerwriters

import org.apache.spark.sql.{DataFrame, SaveMode, SparkSession}

object CSVReaderWriter extends Readerwriter {

  def write(path: String, df: DataFrame) {
    df
      .write
      .mode(SaveMode.Append)
      .csv(path)
  }

  def read(path: String, spark: SparkSession) : DataFrame = {
    spark
      .read
      .option("sep", ",")
      .option("inferSchema", "true")
      .option("header", "true")
      .csv(path)

  }

}
