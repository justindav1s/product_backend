#!/bin/bash

oc apply -f $1-release.yaml

sleep 2

oc rollout status deployment/shop-dev-basket-v1 -n shop-dev