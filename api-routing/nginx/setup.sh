#!/bin/bash

PROJECT=reverse-proxy

echo Deleting $PROJECT
oc delete project $PROJECT
echo Creating $PROJECT
oc new-project $PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc new-project $PROJECT 2> /dev/null
done

oc create secret docker-registry rh-dockercfg \
  --docker-server=registry.access.redhat.com \
  --docker-username=${RHDN_USERNAME} \
  --docker-password=${RHDN_PASSWORD} \
  --docker-email=${RHDN_USERNAME} \
  -n $PROJECT

oc create secret docker-registry quayio-dockercfg \
  --docker-server=${QUAYIO_REGISTRY} \
  --docker-username=${QUAYIO_USER} \
  --docker-password=${QUAYIO_PASSWORD} \
  --docker-email=${QUAYIO_EMAIL} \
  -n $PROJECT

oc new-build nginx https://github.com/justindav1s/microservices-on-openshift \
    --push-secret="quayio-dockercfg" \
    --source-image="registry.redhat.io/rhel8/nginx-116" \
    --to="quay.io/justindav1s" \
    --context-dir=""
    --strategy="Source"


