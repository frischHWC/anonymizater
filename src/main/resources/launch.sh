#!/usr/bin/env bash

export HOME=/root/anonymizater/

cd $HOME

echo "Launching spark-submit"

./spark-submit.sh $@

echo "Finished spark-submit"
