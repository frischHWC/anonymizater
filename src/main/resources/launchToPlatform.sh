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