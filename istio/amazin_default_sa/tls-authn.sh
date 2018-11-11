#!/usr/bin/env bash

oc project istio-system

oc delete policy default

oc apply -f tls_authn.yaml

