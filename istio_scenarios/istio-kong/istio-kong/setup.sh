#!/bin/bash

rm -rf *192.168.28.136*

APP=kong
SLEEP=2

kubectl delete -n istio-system secret kong-tls-credentials
#kubectl delete -f kong-gateway-mtls.yaml
kubectl delete gateway,virtualservice,ingress,peerauthentication,serviceentry,destinationrule,KongIngress,ServiceEntry --all -n shop
kubectl delete gateway,virtualservice,ingress,peerauthentication,serviceentry,destinationrule,KongIngress,ServiceEntry --all -n kong-istio
kubectl delete peerauthentication default -n istio-system
kubectl delete AuthorizationPolicy --all -n default

openssl req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -subj '/O=Kong Inc./CN=192.168.28.136.nip.io' -keyout 192.168.28.136.nip.io.key -out 192.168.28.136.nip.io.crt

openssl req -out ${APP}.192.168.28.136.nip.io.csr -newkey rsa:2048 -nodes -keyout ${APP}.192.168.28.136.nip.io.key -subj "/CN=${APP}.192.168.28.136.nip.io/O=${APP} organization"
openssl x509 -req -days 365 -CA 192.168.28.136.nip.io.crt -CAkey 192.168.28.136.nip.io.key -set_serial 0 -in ${APP}.192.168.28.136.nip.io.csr -out ${APP}.192.168.28.136.nip.io.crt

sleep ${SLEEP}

kubectl create -n istio-system secret tls kong-tls-credentials --key=${APP}.192.168.28.136.nip.io.key --cert=${APP}.192.168.28.136.nip.io.crt

sleep ${SLEEP}

kubectl apply -f kong-gateway-mtls-multi-ns.yaml
#kubectl apply -f kong-gateway-mtls.yaml

sleep ${SLEEP}

echo SHOP
curl -sk --cacert ${APP}.192.168.28.136.nip.io.crt https://${APP}.192.168.28.136.nip.io:31997/amazin/products/all
echo
echo JAVA
curl -sk --cacert ${APP}.192.168.28.136.nip.io.crt https://${APP}.192.168.28.136.nip.io:31997/java/products/all

rm -rf *192.168.28.136*