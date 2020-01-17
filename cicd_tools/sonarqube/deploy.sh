#!/usr/bin/env bash

. ../../env.sh

oc login https://${IP} -u justin

APP=sonarqube

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
  --docker-server=nexus3-docker.cicd.svc:5443 \
  --docker-username=${NEXUS_USER} \
  --docker-password=${NEXUS_PASSWORD} \
  --docker-email=docker@gmail.com \
  -n cicd

oc create sa ${APP}
oc secrets link ${APP} nexus-dockercfg --for=pull -n cicd
oc secrets link builder nexus-dockercfg -n cicd

oc new-app -f sonarqube-persistent-template.yml \
    -p APPLICATION_NAME=${APP} \
    -p SOURCE_REPOSITORY_URL=https://github.com/justindav1s/microservices-on-openshift.git \
    -p SOURCE_REPOSITORY_URL=master \
    -p DOCKERFILE_PATH="cicd_tools/sonarqube" \
    -p SONARQUBE_JDBC_USERNAME=${DATABASE_USER} \
    -p SONARQUBE_JDBC_PASSWORD=${DATABASE_PASSWORD} \
    -p SONARQUBE_JDBC_URL=${DATABASE_URL}

#then download quality profile from market place https://sonarqube-cicd.apps.ocp.datr.eu/admin/marketplace
