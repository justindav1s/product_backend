FROM registry.redhat.io/openshift4/ose-jenkins-agent-maven

# Install Maven
USER root

WORKDIR /opt 

RUN echo $PATH && \
    pwd && \
    ls -ltr && \
    wget https://apache.mirrors.nublue.co.uk/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz >/dev/null 2>&1
RUN ls -ltr
RUN tar zxvf apache-maven-3.6.3-bin.tar.gz
RUN ln -s apache-maven-3.6.3 maven
RUN find /opt/maven
RUN /opt/maven/bin/mvn -version

RUN chown -R 1001:0 $HOME && \
    chmod -R g+rw $HOME

USER 1001