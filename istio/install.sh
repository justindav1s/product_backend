#!/usr/bin/env bash

APP=basket

. ../app/env.sh

oc login https://${IP}:8443 -u $USER

cd ~/istio/install/ansible/

ansible-playbook main.yml -e '{"istio": {"release_tag_name": "0.6.0", "delete_resources": true, "addon": ["grafana", "prometheus", "jaeger", "servicegraph"]}}'

cd -
