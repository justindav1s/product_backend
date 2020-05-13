FROM centos

RUN yum -y update && yum -y install curl

USER 1001 

ENTRYPOINT ["tail", "-f", "/dev/null"]
CMD ["bash"]