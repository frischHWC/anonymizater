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