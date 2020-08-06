package com.cloudera.frisch.anonymizater.encoders

import org.apache.spark.sql.expressions.UserDefinedFunction
import org.apache.spark.sql.functions.udf

object NullEncoder extends Encoder {

  def encodeUdf: UserDefinedFunction = {
    udf { value: String => "null"  }
  }

}
