# dockerfile to build image for JBoss EAP 7.0

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

# add a user for the application, with sudo permissions
RUN useradd -m jboss ; echo jboss: | chpasswd ; usermod -a -G wheel jboss

# create workdir
RUN mkdir -p /opt/rh

WORKDIR /opt/rh

# install JBoss EAP 7.0.0
ADD jboss-eap-7.0.0.zip /tmp/jboss-eap-7.0.0.zip
RUN unzip /tmp/jboss-eap-7.0.0.zip
RUN rm /tmp/jboss-eap-7.0.0.zip

# set environment
ENV JBOSS_HOME /opt/rh/jboss-eap-7.0

WORKDIR /home/jboss

############################################
# Add customization sub-directories (for entrypoint)
############################################
ADD docker-entrypoint-initdb.d  docker-entrypoint-initdb.d
RUN chown -R jboss:jboss        docker-entrypoint-initdb.d
RUN find docker-entrypoint-initdb.d -type d -execdir chmod 770 {} \;
RUN find docker-entrypoint-initdb.d -type f -execdir chmod 660 {} \;

ADD modules  modules
RUN chown -R jboss:jboss modules
RUN find modules -type d -execdir chmod 770 {} \;
RUN find modules -type f -execdir chmod 660 {} \;


COPY docker-entrypoint.sh docker-entrypoint.sh

# set permission folder
RUN chown -R jboss:jboss /opt/rh
RUN chmod 700 docker-entrypoint.sh
RUN chown -R jboss:jboss docker-entrypoint.sh

RUN mkdir /var/log/jboss/
RUN chown jboss:jboss /var/log/jboss/

# JBoss ports
EXPOSE 8080 9990 9999 8787


USER jboss

############################################
# Start JBoss in stand-alone mode
############################################

ENTRYPOINT ["./docker-entrypoint.sh"]

CMD ["start-jboss"]
