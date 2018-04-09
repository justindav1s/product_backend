#!/usr/bin/env bash

APP=frontend
S2I_IMAGE=nginx:latest

. ../../env.sh

oc login https://${IP}:8443 -u $USER

oc project ${PROD_PROJECT}

oc delete all -l app=${APP} -n ${PROD_PROJECT}
oc delete pvc -l app=${APP} -n ${PROD_PROJECT}
oc delete is,bc,dc,svc,route ${APP} -n ${PROD_PROJECT}
oc delete template ${APP}-dev-dc -n ${PROD_PROJECT}
oc delete configmap ${APP}-config -n ${PROD_PROJECT}

echo Setting up ${APP} for ${PROD_PROJECT}
oc new-build --binary=true --labels=app=${APP} --name=${APP} ${S2I_IMAGE} -n ${PROD_PROJECT}
oc new-app -f ./${APP}-prod-dc.yaml --allow-missing-imagestream-tags=true -n ${PROD_PROJECT}
oc expose dc ${APP} --port 8080 -n ${PROD_PROJECT}

