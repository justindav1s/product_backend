#!/usr/bin/env bash

APP=basket

. ../../app/env.sh

#oc login https://${IP}:8443 -u $USER

oc project istio-system
oc adm policy add-scc-to-user privileged -z default -n ${PROD_PROJECT}

oc project ${PROD_PROJECT}
oc adm policy add-scc-to-user privileged -z default -n ${PROD_PROJECT}
oc label namespace ${PROD_PROJECT} istio-injection=enabled

sleep 5

oc delete deploy -l app=${APP} -n ${PROD_PROJECT}
oc delete deploymentconfigs -l app=${APP} -n ${PROD_PROJECT}
oc delete po -l app=${APP} -n ${PROD_PROJECT}
oc delete builds -l app=${APP} -n ${PROD_PROJECT}
oc delete svc -l app=${APP} -n ${PROD_PROJECT}
oc delete bc -l app=${APP} -n ${PROD_PROJECT}
oc delete routes -l app=${APP} -n ${PROD_PROJECT}
oc delete ingress -l app=${APP} -n ${PROD_PROJECT}

oc apply -f <(istioctl kube-inject -f ${APP}-istio-prod.yaml)