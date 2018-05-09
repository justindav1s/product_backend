#!/usr/bin/env bash

istioctl delete routerules basket-lb inventory-lb user-lb gateway-lb

istioctl create -f all-routing-rule.yaml



