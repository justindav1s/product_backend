#!/usr/bin/env bash

OCP=https://ocp.datr.eu:8443
PROJECT=istio-system
OP_PROJECT=istio-operator
USER=justin

oc login ${OCP} -u $USER

oc delete project $PROJECT
oc adm new-project $PROJECT --node-selector='capability=infra' 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc adm new-project $PROJECT --node-selector='capability=infra' 2> /dev/null
done

oc project $OP_PROJECT

oc create -f istio-installation-3.11.yaml
