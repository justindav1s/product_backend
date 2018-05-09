#!/usr/bin/env bash

. ../../env.sh

oc login https://${IP}:8443 -u $USER

oc project ${PROD_PROJECT}

oc delete ingress gateway -n ${PROD_PROJECT}
oc delete ingress amazin-gateway -n ${PROD_PROJECT}