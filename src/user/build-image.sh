#!/usr/bin/env bash

. ./env.sh

IMAGE=ocp-awscli
VERSION=1
REGISTRY_HOST=nexus-dev-devops.nonprod.theosmo.com:5001

docker build -t $IMAGE .

TAG=$REGISTRY_HOST/$PROJECT/$IMAGE:$VERSION

docker tag $IMAGE $TAG

TOKEN=`oc whoami -t`

docker login -p $TOKEN -u justin $REGISTRY_HOST

sleep 5

docker push $TAG

