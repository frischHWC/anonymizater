package com.cloudera.frisch.anonymizater.encoders

import java.security.MessageDigest

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object MD5Encoder extends Encoder {

  def encodeUdf: UserDefinedFunction = {
    udf { value: String => MessageDigest.getInstance("MD5").digest(value.getBytes)  }
  }

}
