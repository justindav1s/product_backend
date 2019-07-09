FROM centos

WORKDIR /
RUN wget https://downloads.jboss.org/keycloak/4.8.3.Final/gatekeeper/keycloak-gatekeeper-linux-amd64.tar.gz
RUN tar zxvf keycloak-gatekeeper-linux-amd64.tar.gz && ls -ltr && rm -rf keycloak-gatekeeper-linux-amd64.tar.gz


FROM scratch
WORKDIR /
COPY --from=0 /keycloak-gatekeeper /keycloak-gatekeeper
COPY /config/gatekeeper.yaml /gatekeeper.yaml

CMD ["/keycloak-gatekeeper", "-config", "/gatekeeper.yaml"]