#!/usr/bin/env bash

curl -v -X POST https://grafeas-cicd.apps.ocp.datr.eu/v1beta1/projects \
    -H "Content-Type: application/json" \
    --data '{"name":"projects/provider_example"}'




