#!/usr/bin/env bash

oc project istio-system

oc delete rbacconfig default

oc apply -f rbac_on.yaml

