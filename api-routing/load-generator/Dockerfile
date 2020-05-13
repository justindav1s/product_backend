FROM centos

RUN yum -y update && yum -y install curl nginx wget git

RUN sed -i.bak 's/80/8080/g' /etc/nginx/nginx.conf
RUN sed -i.bak 's/443/8433/g' /etc/nginx/nginx.conf
RUN wget https://storage.googleapis.com/hey-release/hey_linux_amd64 && \
    mv hey_linux_amd64 /usr/local/bin/hey && \
    chmod 755 /usr/local/bin/hey

EXPOSE 8080/tcp
EXPOSE 8443/tcp

USER 1001 

CMD ["nginx", "-g", "daemon off"]