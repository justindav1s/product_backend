FROM docker-registry.default.svc:5000/openshift/java

MAINTAINER Justin Davis

EXPOSE 8080

ENV TZ Europe/London

COPY target/mongodb-1.0.jar .

ENTRYPOINT [ "sh", "-c", "java -jar  /home/jboss/mongodb-1.0.jar" ]