#!/usr/bin/env bash

oc project istio-system

oc delete rbacconfig default

oc apply -f rbac_off.yaml

oc describe rbacconfig -n istio-system

