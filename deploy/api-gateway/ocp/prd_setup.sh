#!/usr/bin/env bash

APP=api-gateway
ENV=prd
IMAGE_NAME=${APP}
IMAGE_TAG=0.0.1-SNAPSHOT
SAP=prd
SERVICEACCOUNT_NAME=${APP}-${ENV}-sa
SERVICE_NAME=${APP}-${ENV}

. ../../../env.sh

oc login https://${IP}:8443 -u $USER

oc project ${PROD_PROJECT}

oc delete deployments ${APP}-${SAP} -n ${PROD_PROJECT}
oc delete svc ${SERVICE_NAME} -n ${PROD_PROJECT}
oc delete sa ${SERVICEACCOUNT_NAME} -n ${PROD_PROJECT}

oc delete configmap ${APP}-${SAP}-config --ignore-not-found=true -n ${PROD_PROJECT}
oc create configmap ${APP}-${SAP}-config --from-file=config/config.${SAP}.properties -n ${PROD_PROJECT}

oc new-app -f ../../spring-boot-prd-deploy-template.yaml \
    -p APPLICATION_NAME=${APP} \
    -p IMAGE_NAME=${IMAGE_NAME} \
    -p IMAGE_TAG=${IMAGE_TAG} \
    -p SPRING_PROFILES_ACTIVE=${SAP} \
    -p VERSION_LABEL=${SAP} \
    -p SERVICEACCOUNT_NAME=${SERVICEACCOUNT_NAME}

oc new-app -f ../../service-template.yaml \
    -p APPLICATION_NAME=${APP} \
    -p SERVICEACCOUNT_NAME=${SERVICEACCOUNT_NAME} \
    -p SERVICE_NAME=${SERVICE_NAME}

oc set triggers deployment/${APP}-${SAP} --from-config

sleep 2

oc policy add-role-to-group system:image-puller system:serviceaccounts:${PROD_PROJECT} -n ${DEV_PROJECT}
oc policy add-role-to-group system:image-puller system:serviceaccounts:${SERVICEACCOUNT_NAME} -n ${DEV_PROJECT}
oc adm policy add-scc-to-user privileged -z ${SERVICEACCOUNT_NAME}
