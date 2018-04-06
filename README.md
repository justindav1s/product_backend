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

## Istio
