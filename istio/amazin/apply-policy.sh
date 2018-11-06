#!/usr/bin/env bash

oc project amazin-prod

oc delete rbacconfig default
oc delete policy inventory

oc apply -f policy1.yaml

