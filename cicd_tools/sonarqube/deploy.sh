#!/usr/bin/env bash

#Database Service Name
#postgresql-sonar
#The name of the OpenShift Service exposed for the database.
#
#PostgreSQL Connection Username
#sonar
#Username for PostgreSQL user that will be used for accessing the database.
#
#PostgreSQL Connection Password
#sonar
#Password for the PostgreSQL connection user.
#
#PostgreSQL Database Name
#sonar




. ../env.sh

APP=sonarqube

oc project $CICD_PROJECT

oc delete imagestream ${APP}
oc delete buildconfig ${APP}-docker-build
oc delete deploymentconfig ${APP}
oc delete persistentvolumeclaim ${APP}-data
oc delete persistentvolumeclaim ${APP}-extensions
oc delete serviceaccounts ${APP}
oc delete service ${APP}
oc delete route ${APP}

DATABASE_USER="sonar"
DATABASE_PASSWORD="sonar"
DATABASE_URL="jdbc:postgresql://postgresql-sonar/sonar"

oc new-app -f sonarqube-persistent-template.yml \
    -p DOMAIN=${DOMAIN} \
    -p APPLICATION_NAME=${APP} \
    -p SOURCE_REPOSITORY_URL=https://github.com/justindav1s/ocp-appdev.git \
    -p SOURCE_REPOSITORY_URL=master \
    -p DOCKERFILE_PATH="sonarqube" \
    -p SONARQUBE_JDBC_USERNAME=${DATABASE_USER} \
    -p SONARQUBE_JDBC_PASSWORD=${DATABASE_PASSWORD} \
    -p SONARQUBE_JDBC_URL=${DATABASE_URL}
