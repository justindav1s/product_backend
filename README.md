# Microservices On Openshift


## The Application 

A basic shopping application for a store called "Amazin".

Composed of three microservices, written in Java, using Spring-Boot (https://projects.spring.io/spring-boot/) , deployed as executable jar files, and exposing RESTful endpoints. The 3 services are :
1. User : manages user logon
   * this services can be found in ```app/user```
2. Inventory : provides data to users about what's available to buy, this comes in three versions (controlled by spring.active.profiles)
   * this services can be found in ```app/inventory```, this service in three versions (controlled by spring.active.profiles)
        1. Food only (v1)
        2. Food and clothes (v2)
        3. Food clothes and gadgets (v3)
    
3. Basket : provides basket fuctionality 
   * this services can be found in ```app/basket``` 
    
And a frontend web application written in :
   * Angular 5 : https://angular.io/
   * Bootstrap 3 : https://getbootstrap.com/docs/3.3/
   * this app can be found in ```app/frontend```

All this can be built and deployed CICD using :
   * Openshift : https://www.openshift.com/
   * Jenkins : https://jenkins.io/
   * Pipelines scripts to do that are included here : ```app/..../cicd``` 

Further information about setting up CICD tools can be found here : 
   * https://github.com/justindav1s/openshift-app-development
 
The microservives have been configured to support Open-tracing
   * http://opentracing.io/
    
And in particular integrate with an open-tracing dashboard application called Jaeger
   * http://www.jaegertracing.io/  
    
Istio, see below, provides the base infrastructure to make tracing possible, but small changes are required to the configuration of the micro services to make them traceable.

Using maven, there are additional dependencies in the pom : 

```
		<dependency>
			<groupId>io.opentracing.contrib</groupId>
			<artifactId>opentracing-spring-cloud-starter</artifactId>
			<version>0.1.8</version>
		</dependency>

		<dependency>
			<groupId>com.uber.jaeger</groupId>
			<artifactId>jaeger-core</artifactId>
			<version>0.26.0</version>
		</dependency>

		<dependency>
			<groupId>com.uber.jaeger</groupId>
			<artifactId>jaeger-b3</artifactId>
			<version>0.26.0</version>
		</dependency>

		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpclient</artifactId>
			<version>4.5.5</version>
		</dependency>
```      

and the implementation of a Jaeger client bean within the application code :

```
    @Bean
    public io.opentracing.Tracer jaegerTracer() {
        Builder builder = new Builder("spring-boot",
                new RemoteReporter(new HttpSender("http://jaeger-collector.istio-system:14268/api/traces"), 10,
                        65000, new Metrics(new StatsFactoryImpl(new NullStatsReporter()))),
                new ConstSampler(true))
                .registerInjector(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec())
                .registerExtractor(Format.Builtin.HTTP_HEADERS, new B3TextMapCodec());
        return builder.build();
    }
    
```

Note, no chaanges are required to the code implementing business logic.


# Istio

https://istio.io/

## Install

Do the Ansible install :
- https://istio.io/docs/setup/kubernetes/ansible-install.html

Playbooks are  here : 
- https://github.com/istio/istio

In the ```install/ansible``` folder, then run :

```
ansible-playbook main.yml -e '{"istio": {"release_tag_name": "0.7.1", "delete_resources": true, "addon": ["grafana", "prometheus", "jaeger"]}}'

ansible-playbook -i /root/bin/inventory/hosts main.yml -e '{"istio": {"release_tag_name": "0.7.1", "delete_resources": true, "addon": ["grafana", "prometheus", "jaeger", "servicegraph"]}}'
```

## Jaeger - Open Tracing

- https://medium.com/jaegertracing/using-opentracing-with-istio-envoy-d8a4246bdc15
- https://github.com/pavolloffay/opentracing-spring-boot-istio
- https://github.com/jaegertracing/jaeger-client-java
- http://www.hawkular.org/blog/2017/06/9/opentracing-spring-boot.html
- https://github.com/opentracing/opentracing-javascript

## Servicegraph

http://<HOST>/force/forcegraph.html
