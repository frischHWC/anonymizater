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
# Export your host here to launch the program on the platform
# export HOST=

export USER=root
export DIRECTORY_TO_WORK="/root/anonymizater"

sbt clean assembly

echo "Create needed directory on platform and send required files there"

# Create directory folder on cluster
ssh ${USER}@${HOST} "mkdir -p ${DIRECTORY_TO_WORK}/"

# Copy files to cluster
scp src/main/resources/spark-submit.sh ${USER}@${HOST}:${DIRECTORY_TO_WORK}/spark-submit.sh

scp src/main/resources/application.conf ${USER}@${HOST}:${DIRECTORY_TO_WORK}/application.conf
scp src/main/resources/log4j.properties ${USER}@${HOST}:${DIRECTORY_TO_WORK}/log4j.properties
scp target/scala-2.11/anonymizater-0.1-SNAPSHOT-jar-with-dependencies.jar ${USER}@${HOST}:${DIRECTORY_TO_WORK}/anonymizater.jar

# Check everything is in target directory
ssh ${USER}@${HOST} ls -ali ${DIRECTORY_TO_WORK}/

echo "Finished to send required files"

echo "Launch script on platform to launch program properly"
ssh ${USER}@${HOST} 'bash -s' < src/main/resources/launch.sh $@
echo "Program finished"