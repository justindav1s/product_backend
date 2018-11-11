#!/usr/bin/env bash

. ../../env.sh

OCP=https://${IP}:8443
PROJECT=istio-operator
USER=justin

oc login ${OCP} -u $USER

oc delete project $PROJECT
oc adm new-project $PROJECT --node-selector='capability=infra' 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc adm new-project $PROJECT --node-selector='capability=infra' 2> /dev/null
done

oc delete validatingwebhookconfigurations istio-galley
oc delete mutatingwebhookconfigurations istio-sidecar-injector
oc delete roles "istio-operator"
oc delete rolebinding "default-account-istio-operator"
oc delete clusterrolebinding "default-account-istio-operator-cluster-role-binding"
oc delete deployment.extensions "istio-operator"
oc delete customresourcedefinition "installations.istio.openshift.com"

sleep 2

oc new-app -f istio_product_operator_template-3.11.yaml \
            --param=OPENSHIFT_ISTIO_MASTER_PUBLIC_URL=$OCP
