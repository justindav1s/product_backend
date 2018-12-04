#!/usr/bin/env bash

mvn clean package
java  -jar target/mongodb-1.0.jar
