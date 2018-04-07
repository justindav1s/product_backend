#!/usr/bin/env bash



for i in $(seq 1 1000)
do
    echo Iteration \# ${i}
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' http://user.apps.ocp.datr.eu/user/login  > 2&>1
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"passw' http://user.apps.ocp.datr.eu/user/login  > 2&>1
    BASKET=$(curl -s -X POST -H "Content-Type: application/json" -d "{\"username\":\"justin${i}\",\"password\":\"password\"}" http://user.apps.ocp.datr.eu/user/login | jq .basketId)
    curl -s -X GET http://inventory.apps.ocp.datr.eu/products/all > 2&>1
    curl -s -X GET http://inventory.apps.ocp.datr.eu/products/7  > 2&>1
    curl -s -X GET http://inventory.apps.ocp.datr.eu/products/19  > 2&>1
    curl -s -X GET http://inventory.apps.ocp.datr.eu/prod/19  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/products/19  > 2&>1
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' http://user.apps.ocp.datr.eu/user/login  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/add/1  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/add/7  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/add/1  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/add/9  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/add/19  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/add/anything > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/remove/1  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/remove/3  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/empty  > 2&>1
    curl -s -X GET http://basket.apps.ocp.datr.eu/basket/${BASKET}/add/9  > 2&>1
    curl -s -X POST -H "Content-Type: application/json" -d '{"username":"justin1","password":"password"}' http://user.apps.ocp.datr.eu/user/login  > 2&>1
    curl -s -X GET http://inventory.apps.ocp.datr.eu/products/all > 2&>1
done







