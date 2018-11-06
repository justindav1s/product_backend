#!/usr/bin/env bash

APP=basket

. ../../../env.sh

oc login https://${IP}:8443 -u $USER

oc project ${PROD_PROJECT}

APP_SA=basket-sa

oc delete ${APP_SA}
oc delete deployments -l app=${APP} -n ${PROD_PROJECT}
oc delete deploymentconfigs -l app=${APP} -n ${PROD_PROJECT}
oc delete po -l app=${APP} -n ${PROD_PROJECT}
oc delete builds -l app=${APP} -n ${PROD_PROJECT}
oc delete svc -l app=${APP} -n ${PROD_PROJECT}
oc delete bc -l app=${APP} -n ${PROD_PROJECT}
oc delete routes -l app=${APP} -n ${PROD_PROJECT}

oc apply -f ${APP}-sa-prod.yaml -n ${PROD_PROJECT}

oc adm policy add-scc-to-user privileged -z ${APP_SA}

oc apply -f ${APP}-istio-prod.yaml -n ${PROD_PROJECT}