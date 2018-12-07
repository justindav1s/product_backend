#!/usr/bin/env bash

. ../env.sh

oc login https://${IP}:8443 -u justin

APP=grafeas

oc project $CICD_PROJECT

oc delete imagestream ${APP}
oc delete buildconfig ${APP}-docker-build
oc delete deploymentconfig ${APP}
oc delete serviceaccounts ${APP}
oc delete service ${APP}
oc delete route ${APP}

oc create sa ${APP} -n ${CICD_PROJECT}
oc adm policy add-scc-to-user privileged -z builder -n ${CICD_PROJECT}
oc adm policy add-scc-to-user anyuid -z builder -n ${CICD_PROJECT}

oc new-app -f grafeas-template.yml \
    -p APPLICATION_NAME=${APP} \
    -p SOURCE_REPOSITORY_URL=https://github.com/justindav1s/microservices-on-openshift.git \
    -p SOURCE_REPOSITORY_REF=master \
    -p DOCKERFILE_PATH="cicd_tools/grafeas-server" \

