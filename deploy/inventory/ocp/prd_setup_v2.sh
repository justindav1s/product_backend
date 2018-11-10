#!/usr/bin/env bash

APP=inventory
IMAGE_NAME=${APP}
IMAGE_TAG=0.0.1-SNAPSHOT
SAP=v2
APP_SA=${APP}-sa

. ../../../env.sh

oc login https://${IP}:8443 -u $USER

oc project ${PROD_PROJECT}

oc delete configmap ${APP}-${SAP}-config --ignore-not-found=true -n ${PROD_PROJECT}
oc create configmap ${APP}-${SAP}-config --from-file=config/config.${SAP}.properties -n ${PROD_PROJECT}

oc new-app -f ../../spring-boot-prd-template.yaml \
    -p APPLICATION_NAME=${APP} \
    -p IMAGE_NAME=${IMAGE_NAME} \
    -p IMAGE_TAG=${IMAGE_TAG} \
    -p SPRING_PROFILES_ACTIVE=${SAP} \
    -p VERSION_LABEL=${SAP}

sleep 2

oc policy add-role-to-group system:image-puller system:serviceaccounts:${PROD_PROJECT} -n ${DEV_PROJECT}
oc policy add-role-to-group system:image-puller system:serviceaccounts:${APP_SA} -n ${DEV_PROJECT}
oc adm policy add-scc-to-user privileged -z ${APP_SA}
