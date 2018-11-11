#!/usr/bin/env bash

oc project amazin-prod

oc adm policy add-scc-to-user anyuid -z default
oc adm policy add-scc-to-user privileged -z default


