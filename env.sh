#!/usr/bin/env bash

#export IP=192.168.0.91
#export IP=ocp.datr.eu
#export IP=master.aws.datr.eu
export IP=api.ocp4.datr.eu:6443
export USER=justin

export ORG=amazin
export DEV_PROJECT=${ORG}-dev
export PROD_PROJECT=${ORG}-prod
export CICD_PROJECT=cicd

export CURL="curl -k -v"
export JENKINS_USER=justin-admin-edit-view
export JENKINS_TOKEN=119918a638cbc2e2f720a1c9072568e07d
export JENKINS=jenkins-cicd.apps.ocp4.datr.eu
#export JENKINS=jenkins-cicd.apps.${IP}

