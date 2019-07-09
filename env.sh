#!/usr/bin/env bash

#export IP=192.168.0.91
#export IP=ocp.datr.eu
#export IP=master.aws.datr.eu
export IP=192.168.33.10.xip.io
export USER=justin

export ORG=amazin
export DEV_PROJECT=${ORG}-dev
export PROD_PROJECT=${ORG}-prod
export CICD_PROJECT=cicd

export CURL="curl -k -v"
export JENKINS_USER=justin-admin-edit-view
export JENKINS_TOKEN=110247d18019506aa026b2cd4ee5ecf647
export JENKINS=jenkins-cicd.apps.192.168.33.10.xip.io
#export JENKINS=jenkins-cicd.apps.${IP}

