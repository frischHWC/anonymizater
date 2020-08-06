package com.cloudera.frisch.anonymizater.encoders

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object RandomEncoder extends Encoder {

  def encodeUdf: UserDefinedFunction = {
    udf { value: String => {
      new scala.util.Random().alphanumeric(10)
      }
    }
  }

}
