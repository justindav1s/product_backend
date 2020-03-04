#!/usr/bin/env bash

. ../../env.sh

oc login https://${IP} -u ${USER}

APP=sonarqube
VERSION=7.9
REG_HOST=nexus3-docker-cicd.apps.ocp4.datr.eu
#REG_HOST=nexus3-docker-cicd.apps-crc.testing

oc project cicd

oc delete imagestream ${APP}
oc delete buildconfig ${APP}-docker-build
oc delete deploymentconfig ${APP}
oc delete persistentvolumeclaim ${APP}-data
oc delete persistentvolumeclaim ${APP}-extensions
oc delete serviceaccounts ${APP}
oc delete service ${APP}
oc delete route ${APP}
oc delete secret nexus-dockercfg

oc create secret docker-registry nexus-dockercfg \
  --docker-server=${REG_HOST} \
  --docker-username=${NEXUS_USER} \
  --docker-password=${NEXUS_PASSWORD} \
  --docker-email=docker@gmail.com \
  -n cicd

oc create sa ${APP}
oc secrets link ${APP} nexus-dockercfg --for=pull -n cicd
oc secrets link default nexus-dockercfg --for=pull -n cicd
oc secrets link builder nexus-dockercfg --for=pull -n cicd
oc secrets link deployer nexus-dockercfg --for=pull -n cicd
oc secrets link builder nexus-dockercfg -n cicd

oc new-app -f sonarqube-persistent-template.yml \
    -p APPLICATION_NAME=${APP} \
    -p SONARQUBE_VERSION=${VERSION} \
    -p SOURCE_REPOSITORY_URL=https://github.com/justindav1s/microservices-on-openshift.git \
    -p SOURCE_REPOSITORY_URL=master \
    -p DOCKERFILE_PATH="cicd_tools/sonarqube" \
    -p SONARQUBE_JDBC_USERNAME=${DATABASE_USER} \
    -p SONARQUBE_JDBC_PASSWORD=${DATABASE_PASSWORD} \
    -p SONARQUBE_JDBC_URL=${DATABASE_URL} \
    -p REG_HOST=${REG_HOST}

oc start-build ${APP}-docker-build  --follow

oc import-image ${APP}:${VERSION} \
  --from ${REG_HOST}/repository/docker/${APP}:${VERSION} \
  --insecure=true \
  --confirm



#then download quality profile from market place https://sonarqube-cicd.apps.ocp.datr.eu/admin/marketplace
