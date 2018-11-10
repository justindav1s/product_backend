#!/usr/bin/env bash

APP=api-gateway

. ../../../env.sh

oc login https://${IP}:8443 -u $USER

oc project ${PROD_PROJECT}

APP_SA=${APP}-sa

oc delete sa ${APP_SA}
oc delete template spring-boot-prd-template
oc delete deployments -l app=${APP} -n ${PROD_PROJECT}
oc delete deploymentconfigs -l app=${APP} -n ${PROD_PROJECT}
oc delete po -l app=${APP} -n ${PROD_PROJECT}
oc delete builds -l app=${APP} -n ${PROD_PROJECT}
oc delete svc -l app=${APP} -n ${PROD_PROJECT}
oc delete bc -l app=${APP} -n ${PROD_PROJECT}
oc delete routes -l app=${APP} -n ${PROD_PROJECT}

oc new-app -f ../../spring-boot-prd-template.yaml \
    -p APPLICATION_NAME=${APP} \
    -p APPLICATION_VERSION=0.0.1-SNAPSHOT \
    -p VERSION_LABEL=v1

sleep 2

oc policy add-role-to-group system:image-puller system:serviceaccounts:${APP_SA} -n ${DEV_PROJECT}
oc adm policy add-scc-to-user privileged -z ${APP_SA}
