#!/usr/bin/env bash

istioctl delete routerules inventory-default

istioctl create -f v1-routing-rule.yaml



