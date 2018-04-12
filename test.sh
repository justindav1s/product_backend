#!/usr/bin/env bash

HOST=192.168.0.131:32619

for i in $(seq 1 1000)
do
    echo Iteration \# ${i}
    echo POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' http://${HOST}/user/login
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' http://${HOST}/user/login
    echo
    echo POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"passw' http://${HOST}/user/login
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"passw' http://${HOST}/user/login
    echo
    echo POST -H "Content-Type: application/json" -d "{\"username\":\"justin${i}\",\"password\":\"password\"}" http://${HOST}/user/login
    BASKET=$(curl -s -X POST -H "Content-Type: application/json" -d "{\"username\":\"justin${i}\",\"password\":\"password\"}" http://${HOST}/user/login | jq .basketId)
    echo
    echo GET http://${HOST}/products/all
    curl -s -X GET http://${HOST}/products/all
    echo
    echo GET http://${HOST}/products/7
    curl -s -X GET http://${HOST}/products/7
    echo
    echo  GET http://${HOST}/products/19
    curl -s -X GET http://${HOST}/products/19
    echo
    echo GET http://${HOST}/prod/19
    curl -s -X GET http://${HOST}/prod/19
    echo
    echo GET http://${HOST}/products/19
    curl -s -X GET http://${HOST}/products/19
    echo
    echo POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' http://${HOST}/user/login
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' http://${HOST}/user/login
    echo
    echo PUT http://${HOST}/basket/${BASKET}/add/1
    curl -s -X PUT http://${HOST}/basket/${BASKET}/add/1
    echo
    echo PUT http://${HOST}/basket/${BASKET}/add/7
    curl -s -X PUT http://${HOST}/basket/${BASKET}/add/7
    echo
    echo PUT http://${HOST}/basket/${BASKET}/add/1
    curl -s -X PUT http://${HOST}/basket/${BASKET}/add/1
    echo
    echo PUT http://${HOST}/basket/${BASKET}/add/9
    curl -s -X PUT http://${HOST}/basket/${BASKET}/add/9
    echo
    echo PUT http://${HOST}/basket/${BASKET}/add/19
    curl -s -X PUT http://${HOST}/basket/${BASKET}/add/19
    echo
    echo PUT http://${HOST}/basket/${BASKET}/add/anything
    curl -s -X PUT http://${HOST}/basket/${BASKET}/add/anything
    echo
    echo DELETE http://${HOST}/basket/${BASKET}/remove/1
    curl -s -X DELETE http://${HOST}/basket/${BASKET}/remove/1
    echo
    echo DELETE http://${HOST}/basket/${BASKET}/remove/1
    curl -s -X DELETE http://${HOST}/basket/${BASKET}/remove/1
    echo
    echo DELETE http://${HOST}/basket/${BASKET}/remove/1
    curl -s -X DELETE http://${HOST}/basket/${BASKET}/remove/1
    echo
    echo GET http://${HOST}/basket/${BASKET}/empty
    curl -s -X DELETE http://${HOST}/basket/${BASKET}/empty
    echo
    echo PUT http://${HOST}/basket/${BASKET}/add/9
    curl -s -X PUT http://${HOST}/basket/${BASKET}/add/9
    echo
    echo POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' http://${HOST}/user/login
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' http://${HOST}/user/login
    echo
    echo GET http://${HOST}/products/all
    curl -s -X GET http://${HOST}/products/all
done







