#!/usr/bin/env bash


. ../env.sh

oc login https://${IP} -u $USER

oc create secret docker-registry nexus-dockercfg \
  --docker-server=nexus3-docker-cicd.apps.ocp4.datr.eu \
  --docker-username=${NEXUS_USER} \
  --docker-password=${NEXUS_PASSWORD} \
  --docker-email=docker@email.com \
  -n $DEV_PROJECT

oc secrets link deployer nexus-dockercfg -n $DEV_PROJECT

