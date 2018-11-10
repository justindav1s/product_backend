#!/usr/bin/env bash

#export IP=192.168.0.91
#export IP=ocp.192.168.33.11.xip.io
export IP=local.datr.eu
export USER=justin

export ORG=amazin
export DEV_PROJECT=${ORG}-dev
export PROD_PROJECT=${ORG}-prod
export CICD_PROJECT=cicd

export CURL="curl -k -v"
export JENKINS_USER=justin-admin
export JENKINS_TOKEN=a96aec8d7dd2c9fbec501d96db95ca60
#export JENKINS=jenkins-cicd.apps.ocp.datr.eu
export JENKINS=jenkins-cicd.apps.${IP}

