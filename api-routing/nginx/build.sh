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
  --docker-server=registry.redhat.io \
  --docker-username=${RHDN_USERNAME} \
  --docker-password=${RHDN_PASSWORD} \
  --docker-email=${RHDN_USERNAME} \
  -n $PROJECT

oc create secret docker-registry quayio-dockercfg \
  --docker-server=quay.io \
  --docker-username=${QUAYIO_USER} \
  --docker-password=${QUAYIO_PASSWORD} \
  --docker-email=${QUAYIO_EMAIL} \
  -n $PROJECT

oc secrets link builder rh-dockercfg
oc secrets link builder quayio-dockercfg

oc new-build registry.redhat.io/rhel8/nginx-116~https://github.com/justindav1s/microservices-on-openshift \
    --push-secret="quayio-dockercfg" \
    --to="quay.io/justindav1s/nginx" \
    --context-dir="api-routing/nginx/src" \
    --strategy="Source" \
    --to-docker=true \
    --allow-missing-images


