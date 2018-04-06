FROM openshift/jenkins-slave-base-centos7

ENV NODEJS_VERSION=6.0 \
    NPM_CONFIG_PREFIX=$HOME/.npm-global \
    PATH=$HOME/node_modules/.bin/:$HOME/.npm-global/bin/:$PATH


RUN yum install -y epel-release && yum install -y --setopt=tsflags=nodocs nodejs npm && \
    rpm -V nodejs npm && \
    yum clean all -y

RUN chown -R 1001:0 $HOME && \
    chmod -R g+rw $HOME

USER 1001
