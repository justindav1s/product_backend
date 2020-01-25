#!/usr/bin/env bash


. ../env.sh

oc login https://${IP} -u $USER


#Create Projects

echo Deleting $DEV_PROJECT
oc delete project $DEV_PROJECT
echo Creating $DEV_PROJECT
oc new-project $DEV_PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
oc new-project $DEV_PROJECT 2> /dev/null
done

echo Deleting $PROD_PROJECT
oc delete project $PROD_PROJECT
echo Creating $PROD_PROJECT
oc new-project $PROD_PROJECT 2> /dev/null
while [ $? \> 0 ]; do
    sleep 1
    printf "."
    oc new-project $PROD_PROJECT 2> /dev/null
done

# Setup Dev Project
oc policy add-role-to-user edit system:serviceaccount:${CICD_PROJECT}:jenkins -n ${DEV_PROJECT}
oc policy add-role-to-user edit system:serviceaccount:${CICD_PROJECT}:default -n ${DEV_PROJECT}
#oc policy add-role-to-user view --serviceaccount=default -n ${DEV_PROJECT}

oc create secret docker-registry nexus-dockercfg \
  --docker-server=nexus3-docker-cicd.apps.ocp4.datr.eu \
  --docker-username=${NEXUS_USER} \
  --docker-password=${NEXUS_PASSWORD} \
  --docker-email=docker@gmail.com \
  -n ${DEV_PROJECT}

oc secrets link deployer nexus-dockercfg --for=pull -n ${DEV_PROJECT}

# Setup Prod Project
oc policy add-role-to-user edit system:serviceaccount:${CICD_PROJECT}:jenkins -n ${PROD_PROJECT}
oc policy add-role-to-user edit system:serviceaccount:${CICD_PROJECT}:default -n ${PROD_PROJECT}
#oc policy add-role-to-user view --serviceaccount=default -n ${PROD_PROJECT}

oc create secret docker-registry nexus-dockercfg \
  --docker-server=nexus3-docker-cicd.apps.ocp4.datr.eu \
  --docker-username=${NEXUS_USER} \
  --docker-password=${NEXUS_PASSWORD} \
  --docker-email=docker@email.com \
  -n ${PROD_PROJECT}

oc secrets link deployer nexus-dockercfg --for=pull -n ${PROD_PROJECT}

#Deploy microservice OCP components
cd user && ./dev_setup.sh && ./prd_setup.sh && cd -
cd basket && ./dev_setup.sh && ./prd_setup.sh && cd -
cd api-gateway && ./dev_setup.sh && ./prd_setup.sh && cd -
cd inventory && ./dev_setup.sh && ./prd_setup_v1.sh && ./prd_setup_v2.sh && ./prd_setup_v3.sh  && cd -
cd web && ./dev_setup.sh  && ./prd_setup.sh && cd -

