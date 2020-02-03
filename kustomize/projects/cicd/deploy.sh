#!/usr/bin/env bash

. ../../../env.sh

oc login https://${IP} -u ${USER}

APP=sonarqube
VERSION=7.9

oc project cicd

oc start-build ${APP}-docker-build  --follow

oc import-image ${APP}:${VERSION} \
  --from ${REG_HOST}/repository/docker/${APP}:${VERSION} \
  --insecure=true \
  --confirm



#then download quality profile from market place https://sonarqube-cicd.apps.ocp.datr.eu/admin/marketplace
