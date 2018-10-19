#!/usr/bin/env bash

oc project amazin-dev

oc delete virtualservice inventory
oc delete gateway amazin-gateway

oc apply -f amazin.yaml



