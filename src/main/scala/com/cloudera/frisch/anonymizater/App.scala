/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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