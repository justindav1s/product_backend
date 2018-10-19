#!/usr/bin/env bash

set -x
APP=inventory
#S2I_IMAGE=redhat-openjdk18-openshift:1.2
S2I_IMAGE=redhat-openjdk18-openshift:1.4

. ../../../env.sh

oc login https://${IP}:8443 -u $USER

oc project ${DEV_PROJECT}

oc delete all -l app=${APP} -n ${DEV_PROJECT}
oc delete pvc -l app=${APP} -n ${DEV_PROJECT}
oc delete is,bc,dc,svc,route ${APP} -n ${DEV_PROJECT}
oc delete template ${APP}-dev-dc -n ${DEV_PROJECT}
oc delete configmap ${APP}-config -n ${DEV_PROJECT}

echo Setting up ${APP} for ${DEV_PROJECT}
oc new-build --binary=true --labels=app=${APP} --name=${APP} ${S2I_IMAGE} -n ${DEV_PROJECT}
oc new-app -f ../ocp/${APP}-dev-dc.yaml --allow-missing-imagestream-tags=true -n ${DEV_PROJECT}
oc set volume dc/${APP} --add --name=${APP}-config-vol --mount-path=/etc/${APP} --configmap-name=${APP}-config -n ${DEV_PROJECT}
oc set triggers dc/${APP} --from-config
oc expose dc ${APP} --port 8080 -n ${DEV_PROJECT}
oc expose svc ${APP} -l app=${APP} -n ${DEV_PROJECT}

