package com.cloudera.frisch.anonymizater

import com.cloudera.frisch.anonymizater.parameters.ArgsParser
import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col


object App {

  @transient lazy val logger = Logger.getLogger(getClass.getName)

  /**
   * Main function that creates a SparkContext and launches treatment
   * @param args
   */
  def main(args : Array[String]) {

    // Get Parameters
    val argumentsParsed = new ArgsParser(args)
    val encoder = argumentsParsed.getEncoder(argumentsParsed.encoder())
    val reader = argumentsParsed.getReaderWriter(argumentsParsed.reader())
    val writer = argumentsParsed.getReaderWriter(argumentsParsed.writer())
    val input = argumentsParsed.input()
    val output = argumentsParsed.output()
    val cols = argumentsParsed.cols()

    // Create Spark SQL Context
    val spark = SparkSession
      .builder()
      .appName(AppConfig.name)
      .getOrCreate()

    // Read as a dataframe
    var df = reader.read(input, spark)

    if(logger.isInfoEnabled) {
      df.printSchema()
      df.show()
    }

    // Encode
    logger.warn("Are there any cols to anonymize ? " + cols.nonEmpty)
    logger.warn("cols are " + cols.toString())
    var column = ""
    for (column <- cols) {
      logger.warn("Anonymizing column: " + column)
      df = df.withColumn(column, encoder.encodeUdf(col(column)))
    }

    if(logger.isDebugEnabled) {
      df.printSchema()
      df.show()
    }

    // Write as a dataframe
    writer.write(output, df)

    spark.stop()

  }



}