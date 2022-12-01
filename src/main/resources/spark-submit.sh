#
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#  http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.
#
#!/usr/bin/env bash

spark-submit \
    --class com.cloudera.frisch.anonymizater.App \
    --master yarn \
    --deploy-mode cluster \
    --files log4j.properties \
    --conf "spark.executor.extraJavaOptions=-Dlog4j.configuration=log4j.properties" \
    --conf "spark.driver.extraJavaOptions=-Dlog4j.configuration=log4j.properties" \
    --keytab /home/dev/dev.keytab \
    --principal dev \
    anonymizater.jar -Dconfig.file=application.conf \
        --encoder Null --reader parquet --writer csv --cols name \
        --input hdfs:///tmp/random_datagen_csv/random_data-0000000000.csv --output hdfs:///tmp/random_datagen_anonymized/