#!/usr/bin/env bash


. ../env.sh

PROJECT=openwhisk

oc login https://${IP}:8443 -u $USER

oc delete project $PROJECT
oc adm new-project $PROJECT --node-selector='capability=apps' 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc adm new-project $PROJECT --node-selector='capability=apps' 2> /dev/null
done

oc process -f deploy_311.yaml | oc create -f -