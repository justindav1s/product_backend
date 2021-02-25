#!/bin/bash

oc delete validatingwebhookconfiguration/openshift-operators.servicemesh-resources.maistra.io
oc delete mutatingwebhookconfigurations/openshift-operators.servicemesh-resources.maistra.io
oc delete -n openshift-operators daemonset/istio-node
oc delete clusterrole/istio-admin clusterrole/istio-cni clusterrolebinding/istio-cni
oc get crds -o name | grep '.*\.istio\.io' | xargs -n 1 oc delete
oc get crds -o name | grep '.*\.maistra\.io' | xargs -n 1 oc delete
oc get crds -o name | grep '.*\.kiali\.io' | xargs -n 1 oc delete