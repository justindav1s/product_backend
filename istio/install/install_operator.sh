#!/usr/bin/env bash

OCP=https://ocp.datr.eu:8443
PROJECT=istio-operator
USER=justin

oc login ${OCP} -u $USER

oc delete project $PROJECT
oc new-project $PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
    oc new-project $PROJECT 2> /dev/null
done

oc delete clusterrolebinding default-account-istio-operator-cluster-role-binding
oc delete customresourcedefinition installations.istio.openshift.com

sleep 2

oc new-app -f istio_product_operator_template.yaml \
            --param=OPENSHIFT_ISTIO_MASTER_PUBLIC_URL=$OCP
