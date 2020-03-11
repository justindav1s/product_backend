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
ENV M2_HOME=/opt/maven

RUN wget https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-20.0.0/graalvm-ce-java11-linux-amd64-20.0.0.tar.gz >/dev/null 2>&1
RUN ls -ltr
RUN tar zxvf graalvm-ce-java11-linux-amd64-20.0.0.tar.gz
RUN ls -ltr
ENV GRAALVM_HOME=/opt/graalvm-ce-java11-20.0.0

RUN export PATH=$GRAALVM_HOME/bin:$M2_HOME/bin:$PATH

RUN chown -R 1001:0 $HOME && \
    chmod -R g+rw $HOME

USER 1001