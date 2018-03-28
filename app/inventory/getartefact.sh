#!/usr/bin/env bash

NEXUS_SERVICE=http://nexus-cicd2.apps.172.16.173.128.nip.io
NEXUS_PORT=8081
GRP=org.jnd
ARTEFACT=product.backend
VERSION=1.0.0-SNAPSHOT
PACKAGING=war

mvn org.apache.maven.plugins:maven-dependency-plugin:2.1:get \
    -DrepoUrl=$NEXUS_SERVICE:$NEXUS_PORT/repository/maven-public/ \
    -Dartifact=$GRP:$ARTEFACT:$VERSION \
    -Dpackaging=$PACKAGING

GRPDIR=$(echo $GRP | sed 's/\./\//')

cp ~/.m2/repository/$GRPDIR/$ARTEFACT/$VERSION/product.backend-$VERSION.war .
