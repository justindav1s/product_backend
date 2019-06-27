#!/usr/bin/env bash

. ./env.sh

oc login https://${IP}:8443 -u $USER

oc delete project $PROJECT
oc new-project $PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc new-project $PROJECT 2> /dev/null
done


oc new-app -f https://raw.githubusercontent.com/3scale/3scale-amp-openshift-templates/2.5-stable/amp/amp.yml \
    -p WILDCARD_DOMAIN=amp.apps.ocp.datr.eu \
    -p WILDCARD_POLICY=Subdomain
