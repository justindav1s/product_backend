#!/usr/bin/env bash

export PROJECT=hygieia
export APP=mongodb

oc project $PROJECT

oc delete all -l app=${APP}
oc delete configmap ${APP}
oc delete is ${APP}
oc delete bc ${APP}
oc delete dc ${APP}
oc delete sa ${APP}
oc delete secret ${APP}

oc project ${PROJECT}

#oc create sa ${APP}
#oc adm policy add-scc-to-user anyuid system:serviceaccount:$PROJECT:${APP}

oc new-app -f mongo-statefulset-template.yml \
    -p REPLICAS=3


