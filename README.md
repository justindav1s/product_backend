# Microservices On Openshift


## The Application 

A basic shopping application for a store called "Amazin".

Composed of three microservices, written in Java, using Spring-Boot, deployed as executable jar files, and exposing RESTful endpoints. The 3 services are :
1. User : manages user logon
2. Inventory : provides data to user about what available to buy
3. Basket : provides basket fuctionality 
 
A frontend web application written in Angular5

This should all be built and deployed using Jenkins pipelines and Openshift, scripts to do that are included here.

Information about setting up CICD tools can be found here : https://github.com/justindav1s/openshift-app-development

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

- https://medium.com/jaegertracing/using-opentracing-with-istio-envoy-d8a4246bdc15
- https://github.com/pavolloffay/opentracing-spring-boot-istio
- https://github.com/jaegertracing/jaeger-client-java
- http://www.hawkular.org/blog/2017/06/9/opentracing-spring-boot.html
- https://github.com/opentracing/opentracing-javascript
