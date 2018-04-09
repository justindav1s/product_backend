#!/usr/bin/env bash

istioctl delete routerules inventory-default

istioctl create -f v2-routing-rule.yaml



