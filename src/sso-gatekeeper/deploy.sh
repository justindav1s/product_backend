#!/usr/bin/env bash

oc login https://ocp.datr.eu:8443 -u justin

APP=sso-gatekeeper
PROJECT=amazin-dev

oc project $PROJECT

oc delete deploymentconfig ${APP}
oc delete serviceaccounts ${APP}
oc delete service ${APP}
oc delete route ${APP}

oc new-app -f gatekeeper-template.yml \
    -p APPLICATION_NAME=${APP} \
    -p LISTEN_PORT="3000"

