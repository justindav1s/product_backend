#!/usr/bin/env bash

set -x

./prd-project-setup.sh

oc label namespace amazin-prod istio-injection=enabled --overwrite=true


cd user && ./prd_setup.sh && cd -
cd basket && ./prd_setup.sh && cd -
cd api-gateway && ./prd_setup.sh && cd -

cd inventory && ./prd_setup_v1.sh &&  ./prd_setup_v2.sh && ./prd_setup_v3.sh && cd -

cd istio && ./istio-setup.sh && cd -