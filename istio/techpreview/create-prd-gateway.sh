#!/usr/bin/env bash

oc project amazin-prod

oc delete virtualservice inventory
oc delete virtualservice user
oc delete virtualservice basket
oc delete virtualservice apigateway
oc delete gateway amazin-gateway-prod

oc apply -f amazin.yaml



