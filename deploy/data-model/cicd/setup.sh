#!/usr/bin/env bash

APP=data-model
S2I_IMAGE=redhat-openjdk18-openshift:1.2

. ../../../env.sh

#setup Jenkins Jobs
JENKINS_USER=justin-admin
JENKINS_TOKEN=a96aec8d7dd2c9fbec501d96db95ca60
JENKINS=jenkins-cicd.apps.ocp.192.168.33.11.xip.io

#turn on "Prevent Cross-site scripting"
CRUMB_JSON=$(curl -k -s "https://${JENKINS_USER}:${JENKINS_TOKEN}@${JENKINS}/crumbIssuer/api/json")

echo CRUMB_JSON=$CRUMB_JSON
CRUMB=$(echo $CRUMB_JSON | jq -r .crumb)
echo CRUMB=$CRUMB

curl -k -v -H "Content-Type: text/xml" \
  --user ${JENKINS_USER}:${JENKINS_TOKEN} \
  -H Jenkins-Crumb:${CRUMB} \
  -X POST https://${JENKINS}/job/amazin-${APP}/doDelete

sleep 5

curl -k -v -H "Content-Type: text/xml" \
  --user ${JENKINS_USER}:${JENKINS_TOKEN} \
  -H Jenkins-Crumb:${CRUMB} \
  --data-binary @config.xml \
  -X POST https://${JENKINS}/createItem?name=amazin-${APP}

