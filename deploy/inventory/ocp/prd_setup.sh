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
oc apply -f <(istioctl kube-inject -f ${APP}-istio-prod-${VERSION}.yaml)