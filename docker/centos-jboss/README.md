
# This project demonstrates use of JBOSS EAP 7

# Get:

Get the jboss-eap-7.0.0.zip file from the web.

# Build:
docker build --rm -t jboss-myapp:latest .


# If You dont have it already
docker network create my-net

# Run:
docker run --network my-net --name jboss-myapp --publish-all -it jboss-myapp /bin/bash

You get a command line also

docker run --network my-net --name jboss-myapp --publish-all -it jboss-myapp

No commandline.

# Inspirred by:
https://github.com/fbascheper/JBoss-EAP-Docker-image



