#!/usr/bin/env bash

istioctl delete routerules inventory-default inventory-lb

istioctl create -f all-routing-rule.yaml



