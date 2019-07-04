FROM centos

RUN yum update -y
RUN yum install -y wget
RUN mkdir -p /app/etc
WORKDIR /app
RUN wget https://downloads.jboss.org/keycloak/4.8.3.Final/gatekeeper/keycloak-gatekeeper-linux-amd64.tar.gz
RUN tar zxvf keycloak-gatekeeper-linux-amd64.tar.gz && ls -ltr

COPY config/gatekeeper.yaml /app/etc/gatekeeper.yaml

EXPOSE 3000

CMD ["/app/keycloak-gatekeeper --config /app/etc/gatekeeper.yaml"]