#!/bin/bash

PROJECT=argocd

oc delete -f cluster-admins-group.yaml
oc delete -f cluster-admins-rolebinding.yaml
oc delete project $PROJECT
oc new-project $PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc new-project $PROJECT 2> /dev/null
done

oc create secret generic github-secret --from-file=ssh-privatekey=${HOME}/.ssh/id_rsa

oc apply -f operatorgroup.yaml
oc apply -f subscription.yaml
oc apply -f argocd_instance.yaml
oc apply -f cluster-admins-group.yaml
oc apply -f cluster-admins-rolebinding.yaml
oc apply -f product-plain-yaml-app.yaml