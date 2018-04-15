#!/usr/bin/env bash

. ../../app/env.sh

oc login https://${IP}:8443 -u $USER

oc project ${PROD_PROJECT}

oc delete ingress -l app=${APP} -n ${PROD_PROJECT}

oc apply -f <(istioctl kube-inject -f app-ingress.yaml)

# Gateway
export GATEWAY_URL=$(oc get po -l istio=ingress -n istio-system -o 'jsonpath={.items[0].status.hostIP}'):$(oc get svc istio-ingress -n istio-system -o 'jsonpath={.spec.ports[0].nodePort}')
echo http://${GATEWAY_URL}/
curl -o /dev/null -s -w "%{http_code}\n" http://${GATEWAY_URL}/products/types