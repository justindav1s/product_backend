#!/usr/bin/env bash

OCP=https://ocp.datr.eu:8443
PROJECT=istio-operator
USER=justin

oc login ${OCP} -u $USER

oc project $PROJECT

oc create -f istio-installation.yaml
