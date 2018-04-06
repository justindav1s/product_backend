# Istio

https://istio.io/

## Install

Do the Ansible Install :
- https://istio.io/docs/setup/kubernetes/ansible-install.html

Playbooks are  here : 
- https://github.com/istio/istio

In the install/ansible folder, then run :

```
playbook main.yml -e '{"istio": {"delete_resources": true, "addon": ["grafana", "prometheus", "jaeger"]}}'
```