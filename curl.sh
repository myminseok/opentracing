#!/bin/bash

for i in `seq 1 $2`;
do
  curl $1
  echo ""
#  sleep 1
done
