#!/bin/bash

set -x
URLBASE=http://sso-gatekeeper-api-gateway-amazin-dev.apps.ocp.datr.eu

URL=${URLBASE}/api/products/all

curl -v -X GET -D headers.txt \
    -H 'Accept: application/json' \
    -H 'Content-Type: application/json' \
    $URL

LOC=$(grep Location headers.txt | awk '{print $2}')
rm -rf headers.txt
echo ${URLBASE}$LOC

curl -v "${URLBASE}$LOC"