#!/usr/bin/env bash

APP=data-model
S2I_IMAGE=redhat-openjdk18-openshift:1.2

. ../../env.sh

#setup Jenkins Jobs
JENKINS_USER=justin-admin
JENKINS_TOKEN=a269a10c43b6cfa1e68c115ee6350345
JENKINS=jenkins-cicd.apps.ocp.datr.eu

#turn on "Prevent Cross-site scripting"
CRUMB_JSON=$(curl -s "https://${JENKINS_USER}:${JENKINS_TOKEN}@${JENKINS}/crumbIssuer/api/json")

echo CRUMB_JSON=$CRUMB_JSON
CRUMB=$(echo $CRUMB_JSON | jq -r .crumb)
echo CRUMB=$CRUMB

curl -v -H "Content-Type: text/xml" \
  --user ${JENKINS_USER}:${JENKINS_TOKEN} \
  -H Jenkins-Crumb:${CRUMB} \
  -X POST https://${JENKINS}/job/amazin-${APP}/doDelete

sleep 5

curl -v -H "Content-Type: text/xml" \
  --user ${JENKINS_USER}:${JENKINS_TOKEN} \
  -H Jenkins-Crumb:${CRUMB} \
  --data-binary @config.xml \
  -X POST https://${JENKINS}/createItem?name=amazin-${APP}

