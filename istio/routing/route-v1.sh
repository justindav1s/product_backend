#!/usr/bin/env bash

istioctl delete routerules inventory-default inventory-v1 inventory-v2 inventory-v3

istioctl create -f v1-routing-rule.yaml



