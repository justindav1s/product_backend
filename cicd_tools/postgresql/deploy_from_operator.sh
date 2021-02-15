#!/usr/bin/env bash

APP_NAME=postgresql
DATABASE_USER=sonar
DATABASE_PASSWORD=sonar
DATABASE_NAME=sonar

oc project cicd

oc delete Database postgresql

oc apply -f postgres_rd.yaml


