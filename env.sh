#!/usr/bin/env bash

#export IP=192.168.0.91
#export IP=ocp.datr.eu
#export IP=master.aws.datr.eu
export IP=api.crc.testing:6443
export USER=justin

export ORG=amazin
export DEV_PROJECT=${ORG}-dev
export PROD_PROJECT=${ORG}-prod
export CICD_PROJECT=cicd

export CURL="curl -k -v"
export JENKINS_USER=justin-admin-edit-view
export JENKINS_TOKEN=1187728df978d0c44510ad7a42aaf25afe
export JENKINS=jenkins-cicd.apps.ocp4.datr.eu
export JENKINS=jenkins-cicd.apps-crc.testing
#export JENKINS=jenkins-cicd.apps.${IP}

