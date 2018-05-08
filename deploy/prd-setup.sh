#!/usr/bin/env bash


. ./env.sh

oc login https://${IP}:8443 -u $USER

oc delete project $PROD_PROJECT
oc new-project $PROD_PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
    oc new-project $PROD_PROJECT 2> /dev/null
done

oc adm policy add-scc-to-user privileged -z default -n ${PROD_PROJECT}
oc policy add-role-to-user edit system:serviceaccount:${CICD_PROJECT}:jenkins -n ${PROD_PROJECT}
oc policy add-role-to-user edit system:serviceaccount:${CICD_PROJECT}:default -n ${PROD_PROJECT}
oc policy add-role-to-user view --serviceaccount=default -n ${PROD_PROJECT}

#Allow all the downstream projects to pull the dev image
oc policy add-role-to-group system:image-puller system:serviceaccounts:${PROD_PROJECT} -n ${DEV_PROJECT}

oc project istio-system
oc adm policy add-scc-to-user privileged -z default -n ${PROD_PROJECT}
oc adm policy add-scc-to-user anyuid -z istio-ingress-service-account -n istio-system
oc adm policy add-scc-to-user anyuid -z istio-grafana-service-account -n istio-system
oc adm policy add-scc-to-user anyuid -z istio-prometheus-service-account -n istio-system


oc project ${PROD_PROJECT}
oc adm policy add-scc-to-user anyuid -z default -n ${PROD_PROJECT}
oc label namespace ${PROD_PROJECT} istio-injection=enabled
oc label namespace default istio-injection=enabled