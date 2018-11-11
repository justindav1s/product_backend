#!/usr/bin/env bash

oc project amazin-prod

oc adm policy add-scc-to-user anyuid -z default
oc adm policy add-scc-to-user privileged -z default

oc adm policy add-scc-to-user anyuid -z inventory-prd-sa
oc adm policy add-scc-to-user privileged -z inventory-prd-sa

oc adm policy add-scc-to-user anyuid -z basket-prd-sa
oc adm policy add-scc-to-user privileged -z basket-prd-sa

oc adm policy add-scc-to-user anyuid -z user-prd-sa
oc adm policy add-scc-to-user privileged -z user-prd-sa

oc adm policy add-scc-to-user anyuid -z api-gateway-prd-sa
oc adm policy add-scc-to-user privileged -z api-gateway-prd-sa

oc project istio-system
oc adm policy add-scc-to-user anyuid -z istio-ingress-service-account -n istio-system
oc adm policy add-scc-to-user anyuid -z istio-grafana-service-account -n istio-system
oc adm policy add-scc-to-user anyuid -z istio-prometheus-service-account -n istio-system

