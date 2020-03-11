#!/usr/bin/env bash

. ../../env.sh

set -x

oc login https://${IP}:8443 -u $USER

IMAGE=jenkins-slave-quarkus:latest
REGISTRY_HOST=nexus3-docker-cicd.apps.ocp4.datr.eu:443

oc project ${CICD_PROJECT}

docker build -t $IMAGE .
docker tag $IMAGE $REGISTRY_HOST/$IMAGE

docker login -u admin $REGISTRY_HOST

docker push $REGISTRY_HOST/$IMAGE
