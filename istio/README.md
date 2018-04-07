# Istio

https://istio.io/

## Install

Do the Ansible install :
- https://istio.io/docs/setup/kubernetes/ansible-install.html

Playbooks are  here : 
- https://github.com/istio/istio

In the ```install/ansible``` folder, then run :

```
playbook main.yml -e '{"istio": {"release_tag_name": "0.7.1", "delete_resources": true, "addon": ["grafana", "prometheus", "jaeger"]}}'
```

## Jaeger - Open Tracing

https://medium.com/jaegertracing/using-opentracing-with-istio-envoy-d8a4246bdc15
https://github.com/pavolloffay/opentracing-spring-boot-istio
https://github.com/jaegertracing/jaeger-client-java
http://www.hawkular.org/blog/2017/06/9/opentracing-spring-boot.html
https://github.com/opentracing/opentracing-javascript