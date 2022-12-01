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
package com.cloudera.frisch.anonymizater.parameters

import com.cloudera.frisch.anonymizater.encoders._
import com.cloudera.frisch.anonymizater.readerwriters._
import org.apache.log4j.Logger
import org.rogach.scallop.ScallopConf


class ArgsParser(arguments: Array[String]) extends ScallopConf(arguments) {
  val encoder = opt[String](descr = "Encoder used to anonymize data, possibilities: null, random, md5, sha256", required = true)
  val reader = opt[String](descr = "Reader to read data to anonymize, possibilities: CSV, AVRO, PARQUET, ORC", required = true)
  val writer = opt[String](descr = "Writer to write data anonymized, possibilities: CSV, AVRO, PARQUET, ORC", required = true)
  val input = opt[String](descr = "Input path or database.Table name depending on input type", required = true)
  val output = opt[String](descr = "Output path or database.Table name depending on output type", required = true)
  val cols = opt[List[String]](descr = "List of columns separated by a space to anonymize", required = true)
  val other = props[String]('D')
  verify()

  @transient lazy val logger = Logger.getLogger(getClass.getName)

  def getEncoder(encoder: String): Encoder = {
    logger.warn("Encoder is : " + encoder)
    encoder.toLowerCase() match {
      case "null" => NullEncoder
      case "md5" => MD5Encoder
      case "sha256" => SHA256Encoder
      case "random" => RandomEncoder
    }
  }

  def getReaderWriter(readerwriter: String): Readerwriter = {
    logger.warn("Reader or Writer is : "  + readerwriter)
    readerwriter.toLowerCase() match {
      case "parquet" => ParquetReaderWriter
      case "csv" => CSVReaderWriter
      case "json" => JsonReaderWriter
      case "orc" => OrcReaderWriter
    }
  }

}