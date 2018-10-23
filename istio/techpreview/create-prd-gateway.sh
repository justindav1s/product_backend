#!/usr/bin/env bash

oc project amazin-prod

oc delete virtualservice inventory
oc delete virtualservice user
oc delete virtualservice basket
oc delete virtualservice api-gateway
oc delete gateway amazin-gateway-prod
oc delete destinationrule inventory

oc apply -f amazin-istio-prod.yaml



