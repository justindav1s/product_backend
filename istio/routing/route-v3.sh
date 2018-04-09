#!/usr/bin/env bash

istioctl delete routerules inventory-default

istioctl create -f v3-routing-rule.yaml



