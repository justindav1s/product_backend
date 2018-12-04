FROM registry.access.redhat.com/fuse7/fuse-java-openshift:1.1-4

MAINTAINER Justin Davis

EXPOSE 8080

ENV TZ Europe/London

RUN mkdir -p /deployments/keys

COPY target/mongodb-1.0.jar /deployments

ENTRYPOINT [ "sh", "-c", "java -cp /deployments/keys/* -jar /deployments/mongodb-1.0.jar" ]