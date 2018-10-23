#!/usr/bin/env bash

oc project amazin-dev

oc delete virtualservice inventory
oc delete virtualservice user
oc delete virtualservice basket
oc delete gateway amazin-gateway
oc delete gateway amazin-gateway-dev

oc apply -f amazin-istio-dev.yaml



