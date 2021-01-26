#!/bin/bash

for i in `seq 1 10000`;
do
  echo ""
  curl localhost:8082/
  sleep 1
done

