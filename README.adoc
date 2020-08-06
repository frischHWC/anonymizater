# Anonymizater

Anonymizater takes some structured data and make them anonymized 
by replacing values of one or more columns values.

Data could be read and written in CSV, ORC, Parquet, JSON, (and later in Hive, HBase, Ozone).

Anonymization process could simply be a replace by null values, a hash in MD5 of the value or a random
string.


It takes as input: 
- Location of data to anonymize
- Location where to write data anonymized
- Input data type 
- Output data type
- Encoding to apply to anonymize the data

An example on how to run it here: link:src/main/resources/spark-submit.sh[spark_submit.sh].

Or directly changing link:src/main/resources/launchToPlatform.sh[launch to platform script] to setup a cluster name allows to run it and deploy automatically
this program and launch it.





