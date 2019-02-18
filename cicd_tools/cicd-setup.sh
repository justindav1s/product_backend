#!/usr/bin/env bash


. ../env.sh

oc login https://${IP}:8443 -u $USER

oc delete project $CICD_PROJECT
oc adm new-project $CICD_PROJECT --node-selector='' 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc adm new-project $CICD_PROJECT --node-selector='' 2> /dev/null
done