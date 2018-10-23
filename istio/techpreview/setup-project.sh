#!/usr/bin/env bash

oc project amazin-dev

oc adm policy add-scc-to-user anyuid -z default
oc adm policy add-scc-to-user privileged -z default


oc project amazin-prd

oc adm policy add-scc-to-user anyuid -z default
oc adm policy add-scc-to-user privileged -z default

