#!/usr/bin/env bash

APP=inventory

. ../../../env.sh

oc login https://${IP}:8443 -u $USER

oc project ${PROD_PROJECT}



oc delete deploy -l app=${APP} --ignore-not-found=true -n ${PROD_PROJECT}
oc delete deploymentconfigs -l app=${APP} --ignore-not-found=true -n ${PROD_PROJECT}
oc delete po -l app=${APP} --ignore-not-found=true -n ${PROD_PROJECT}
oc delete builds -l app=${APP} --ignore-not-found=true -n ${PROD_PROJECT}
oc delete svc -l app=${APP} --ignore-not-found=true -n ${PROD_PROJECT}
oc delete bc -l app=${APP} --ignore-not-found=true -n ${PROD_PROJECT}
oc delete routes -l app=${APP} --ignore-not-found=true -n ${PROD_PROJECT}

VERSION=v1
oc delete configmap ${APP}-${VERSION}-config --ignore-not-found=true -n ${PROD_PROJECT}
oc create configmap ${APP}-${VERSION}-config --from-file=config/config.${VERSION}.properties -n ${PROD_PROJECT}

VERSION=v2
oc delete configmap ${APP}-${VERSION}-config --ignore-not-found=true -n ${PROD_PROJECT}
oc create configmap ${APP}-${VERSION}-config --from-file=config/config.${VERSION}.properties -n ${PROD_PROJECT}

VERSION=v3
oc delete configmap ${APP}-${VERSION}-config --ignore-not-found=true -n ${PROD_PROJECT}
oc create configmap ${APP}-${VERSION}-config --from-file=config/config.${VERSION}.properties -n ${PROD_PROJECT}


oc apply -f ${APP}-istio-prod.yaml

VERSION=v1
oc set triggers deployment/${APP}-${VERSION} --from-config
VERSION=v2
oc set triggers deployment/${APP}-${VERSION} --from-config
VERSION=v3
oc set triggers deployment/${APP}-${VERSION} --from-config
