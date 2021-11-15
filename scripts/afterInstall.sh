#!/bin/bash

REPOSITORY=mingukangkor
APP_NAME=brtrip

echo "> Build new app image"
docker pull $REPOSITORY/$APP_NAME

echo "> Deploy new app container"
docker run -it -d -v /home/ubuntu/log:/app/log -p 8080:8080 --name app $REPOSITORY/$APP_NAME /bin/bash