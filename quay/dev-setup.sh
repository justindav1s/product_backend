#!/usr/bin/env bash


. ../env.sh

DEV_PROJECT=quay-dev

oc login https://${IP}:8443 -u $USER

oc delete project $DEV_PROJECT
oc adm new-project $DEV_PROJECT --node-selector='' 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc adm new-project $DEV_PROJECT --node-selector='' 2> /dev/null
done

oc project  $DEV_PROJECT

oc policy add-role-to-user edit system:serviceaccount:${CICD_PROJECT}:jenkins -n ${DEV_PROJECT}
oc policy add-role-to-user edit system:serviceaccount:${CICD_PROJECT}:default -n ${DEV_PROJECT}
oc policy add-role-to-user view --serviceaccount=default -n ${DEV_PROJECT}


./inventory_setup.sh