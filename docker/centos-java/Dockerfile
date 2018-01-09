# dockerfile to build image centos with java installed

# start from centos
FROM centos:latest

# file author / maintainer
MAINTAINER "Henrik Tolboel" "henrik.tolboel@gmail.com"

# update OS
RUN yum -y update && \
yum -y install sudo openssh-clients telnet unzip java-1.8.0-openjdk-devel && \
yum clean all

# enabling sudo group
# enabling sudo over ssh
RUN echo '%wheel ALL=(ALL) NOPASSWD: ALL' >> /etc/sudoers && \
sed -i 's/.*requiretty$/Defaults !requiretty/' /etc/sudoers

ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk-1.8.0.121-0.b13.el7_3.x86_64

CMD ["bash"]

