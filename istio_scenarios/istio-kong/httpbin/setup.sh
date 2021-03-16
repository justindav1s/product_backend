#!/bin/bash

rm -rf *192.168.28.136*

APP=httpbin

kubectl delete -n istio-system secret httpbin-credential
kubectl delete -f istio.yaml

openssl req -x509 -sha256 -nodes -days 365 -newkey rsa:2048 -subj '/O=HTTPBIN Inc./CN=192.168.28.136.nip.io' -keyout 192.168.28.136.nip.io.key -out 192.168.28.136.nip.io.crt

openssl req -out ${APP}.192.168.28.136.nip.io.csr -newkey rsa:2048 -nodes -keyout ${APP}.192.168.28.136.nip.io.key -subj "/CN=${APP}.192.168.28.136.nip.io/O=httpbin organization"
openssl x509 -req -days 365 -CA 192.168.28.136.nip.io.crt -CAkey 192.168.28.136.nip.io.key -set_serial 0 -in ${APP}.192.168.28.136.nip.io.csr -out ${APP}.192.168.28.136.nip.io.crt

sleep 5

kubectl create -n istio-system secret tls httpbin-credential --key=${APP}.192.168.28.136.nip.io.key --cert=${APP}.192.168.28.136.nip.io.crt

sleep 5

kubectl apply -f istio.yaml

sleep 5

curl -vk --cacert ${APP}.192.168.28.136.nip.io.crt https://${APP}.192.168.28.136.nip.io:31997/status/418

rm -rf *192.168.28.136*