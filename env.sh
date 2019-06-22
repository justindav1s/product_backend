#!/usr/bin/env bash

#export IP=192.168.0.91
export IP=ocp.datr.eu
export IP=master.aws.datr.eu
#export IP=local.datr.eu
export USER=justin

export ORG=amazin
export DEV_PROJECT=${ORG}-dev
export PROD_PROJECT=${ORG}-prod
export CICD_PROJECT=cicd

export CURL="curl -k -v"
export JENKINS_USER=justin-admin
export JENKINS_TOKEN=689cf35e08184521639067df1b432098
export JENKINS=jenkins-cicd.apps.ocp.datr.eu
#export JENKINS=jenkins-cicd.apps.${IP}

