#!/bin/bash

REPOSITORY=mingukangkor
APP_NAME=brtrip

echo "> Build new app image"
docker pull $REPOSITORY/$APP_NAME

echo "> Deploy new app container"
docker run -it -d --name app $REPOSITORY/$APP_NAME -p 8080:8080 /bin/bash