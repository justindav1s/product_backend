#!/usr/bin/env bash
# https://github.com/m88i/nexus-operator/blob/main/README.md

. ../../env.sh

oc login https://${IP} -u ${USER}

oc project $CICD_PROJECT

oc delete nexus nexus

sleep 5

oc apply -f allow-nexus-userid-200.yaml

oc adm policy add-scc-to-user allow-nexus-userid-200 -z nexus3

oc apply -f  nexus_rd.yaml


