#!/usr/bin/env bash

#remember to setup routes on ingres gateway service that mapp to those in the hosts fields of the config in this folder

#oc project amazin-dev
#
#oc adm policy add-scc-to-user anyuid -z default
#oc adm policy add-scc-to-user privileged -z default


oc project amazin-prod

oc adm policy add-scc-to-user anyuid -z default
oc adm policy add-scc-to-user privileged -z default

