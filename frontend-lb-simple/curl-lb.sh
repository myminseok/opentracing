#!/bin/bash


for i in `seq 1 $1`;
do
  curl localhost:8082/ 10000
  echo ""
  sleep 1
done

