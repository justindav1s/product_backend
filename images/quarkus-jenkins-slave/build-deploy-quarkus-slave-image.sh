#!/usr/bin/env bash

. ../../env.sh

IMAGE=jenkins-slave-quarkus:latest
# REGISTRY_HOST=nexus3-docker-cicd.apps.ocp4.datr.eu:443
# USER-admin
REGISTRY_HOST=quay.io/justindav1s
USER=justindav1s

docker build -t $IMAGE .
docker tag $IMAGE $REGISTRY_HOST/$IMAGE

docker login -u $USER $REGISTRY_HOST

docker push $REGISTRY_HOST/$IMAGE
