#!/usr/bin/env bash

oc project amazin-prod

oc delete virtualservice --all -n amazin-prod
oc delete destinationrule --all -n amazin-prod
oc delete gateway --all -n amazin-prod

oc apply -f amazin-dev-api-gateway-ingress.yaml -n amazin-prod
oc apply -f amazin-prd-destrules.yaml -n amazin-prod

#oc apply -f route.yaml



