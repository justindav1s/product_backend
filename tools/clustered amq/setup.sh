#!/usr/bin/env bash

. ./env.sh

export APP=amq
export PROJECT=amq-demo

oc project $PROJECT

oc replace --force  -f \
https://raw.githubusercontent.com/jboss-container-images/jboss-amq-7-broker-openshift-image/72-1.1.GA/amq-broker-7-image-streams.yaml

oc replace --force -f \
https://raw.githubusercontent.com/jboss-container-images/jboss-amq-7-broker-openshift-image/72-1.1.GA/amq-broker-7-scaledown-controller-image-streams.yaml

oc import-image amq-broker-72-openshift:1.1

oc import-image amq-broker-72-scaledown-controller-openshift:1.0

for template in amq-broker-72-basic.yaml \
amq-broker-72-ssl.yaml \
amq-broker-72-custom.yaml \
amq-broker-72-persistence.yaml \
amq-broker-72-persistence-ssl.yaml \
amq-broker-72-persistence-clustered.yaml \
amq-broker-72-persistence-clustered-ssl.yaml;
 do
 oc replace --force -f \
https://raw.githubusercontent.com/jboss-container-images/jboss-amq-7-broker-openshift-image/72-1.1.GA/templates/${template}
 done

oc policy add-role-to-user view -z default
