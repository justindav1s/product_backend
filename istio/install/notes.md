#Istio

##OCP Tech Preview

Docs here :

https://docs.openshift.com/container-platform/3.11/servicemesh-install/servicemesh-install.html

Use scripts in this folder 

##Most up-to-date version

Download from here :

https://github.com/istio/istio/releases

Go here :

istio-1.0.x/install/kubernetes/ansible

ansible-playbook main.yml -e '{"cluster_flavour": "ocp", "istio": {"auth": true, "addon": "kiali", "delete_resources": true} }'


this is interesting

https://istio.io/help/faq/security/