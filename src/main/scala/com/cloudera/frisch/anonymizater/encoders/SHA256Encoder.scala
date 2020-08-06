package com.cloudera.frisch.anonymizater.encoders

import java.security.MessageDigest

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object SHA256Encoder extends Encoder {

  def encodeUdf: UserDefinedFunction = {
    udf { value: String => MessageDigest.getInstance("SHA-256").digest(value.getBytes)  }
  }

}
