FROM nexus-mo-base-devops.nonprod.theosmo.com:443/mo-base/mo-openjdk18-openshift:1.5-14.1539812388
#FROM docker-registry.default.svc:5000/devops/mo-openjdk18-openshift:1.5-14.1539812388

MAINTAINER Justin Davis

EXPOSE 8080

ENV TZ Europe/London
ENV SERVICE_ARTIFACT_ID=user
ENV SERVICE_VERSION=0.0.1-SNAPSHOT
ENV SERVICE_JAR_FILE=$SERVICE_ARTIFACT_ID-$SERVICE_VERSION.jar

COPY target/$SERVICE_JAR_FILE .

ENV JAVA_OPTS="-XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -XX:MaxRAMFraction=1"

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom $JAVA_OPTS -jar $SERVICE_JAR_FILE" ]