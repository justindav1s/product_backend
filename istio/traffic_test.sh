#!/usr/bin/env bash

HOST=https://istio-ingressgateway-istio-system.apps.ocp.datr.eu/api

for i in $(seq 1 1000)
do
    echo Iteration \# ${i}
    echo POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' ${HOST}/login
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' ${HOST}/login
    echo
    echo POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"passw' ${HOST}/login
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"passw' ${HOST}/login
    echo
    echo POST -H "Content-Type: application/json" -d "{\"username\":\"justin${i}\",\"password\":\"password\"}" ${HOST}/login
    BASKET=$(curl -s -X POST -H "Content-Type: application/json" -d "{\"username\":\"justin${i}\",\"password\":\"password\"}" ${HOST}/login | jq .basketId)
    echo
    echo GET ${HOST}/products/all
    curl -s -X GET ${HOST}/products/all
    echo
    echo GET ${HOST}/products/7
    curl -s -X GET ${HOST}/products/7
    echo
    echo  GET ${HOST}/products/19
    curl -s -X GET ${HOST}/products/19
    echo
    echo GET ${HOST}/prod/19
    curl -s -X GET ${HOST}/prod/19
    echo
    echo GET ${HOST}/products/19
    curl -s -X GET ${HOST}/products/19
    echo
    echo POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' ${HOST}/user/login
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' ${HOST}/user/login
    echo
    echo PUT ${HOST}/basket/${BASKET}/add/1
    curl -s -X PUT ${HOST}/basket/${BASKET}/add/1
    echo
    echo PUT ${HOST}/basket/${BASKET}/add/7
    curl -s -X PUT ${HOST}/basket/${BASKET}/add/7
    echo
    echo PUT ${HOST}/basket/${BASKET}/add/1
    curl -s -X PUT ${HOST}/basket/${BASKET}/add/1
    echo
    echo PUT ${HOST}/basket/${BASKET}/add/9
    curl -s -X PUT ${HOST}/basket/${BASKET}/add/9
    echo
    echo PUT ${HOST}/basket/${BASKET}/add/19
    curl -s -X PUT ${HOST}/basket/${BASKET}/add/19
    echo
    echo PUT ${HOST}/basket/${BASKET}/add/anything
    curl -s -X PUT ${HOST}/basket/${BASKET}/add/anything
    echo
    echo DELETE ${HOST}/basket/${BASKET}/remove/1
    curl -s -X DELETE ${HOST}/basket/${BASKET}/remove/1
    echo
    echo DELETE ${HOST}/basket/${BASKET}/remove/1
    curl -s -X DELETE ${HOST}/basket/${BASKET}/remove/1
    echo
    echo DELETE ${HOST}/basket/${BASKET}/remove/1
    curl -s -X DELETE ${HOST}/basket/${BASKET}/remove/1
    echo
    echo GET ${HOST}/basket/${BASKET}/empty
    curl -s -X DELETE ${HOST}/basket/${BASKET}/empty
    echo
    echo PUT ${HOST}/basket/${BASKET}/add/9
    curl -s -X PUT ${HOST}/basket/${BASKET}/add/9
    echo
    echo POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' ${HOST}/login
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' ${HOST}/login
    echo
    echo GET ${HOST}/products/all
    curl -s -X GET ${HOST}/products/all
done







