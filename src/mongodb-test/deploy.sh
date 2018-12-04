#!/usr/bin/env bash

export APP=mongo-app

oc login https://ocp.datr.eu:8443 -u justin

oc project $PROJECT

oc delete all -l app=${APP}
oc delete is,bc ${APP}

mvn clean package -DskipTests

oc new-app -f mongo-app-template.yaml \
    -p RESOURCE_NAME=${APP} \
    -p APP_LABEL=${PROJECT}-${APP} \
    -p DOCKER_REGISTRY=docker-registry.default.svc:5000 \
    -p IMAGE_VERSION=v1 \
    -p NAMESPACE=$PROJECT \
    -p DOCKER_SECRET=openshift-ns-secret

oc start-build ${APP}  --from-dir=. --follow
