
# This project demonstrates use of JBOSS EAP 7

# Get:

Get the jboss-eap-7.1.0.zip file from the web. (google "get jboss-eap-7.1.0.zip" and create an account with red hat)

# Build:
```
docker build --rm -t centos-jboss:latest .
```


# If You dont have it already
```
docker network create my-net
```

# Run:
Run with commandline
```
docker run --network my-net --name my-jboss --publish-all -it centos-jboss /bin/bash
```

Run without commandline

```
docker run --network my-net --name my-jboss --publish-all -d centos-jboss
```

connect to it (bash) if needed:

```
docker exec -it <container id> /bin/bash
```

# Configuration

You can have envrironment variables on the command line by supplying values
on the ```docker run``` commands.

You can apecify e.g. ```-e JBOSS_USER=jbossadmin``` to specify the name of
the admin user (this is the default value).

```-e JBOSS_PASSWORD=jboss@min1``` for the password (default value)
```-e JBOSS_DEBUG_SUSPEND=true``` for enabling debug connection on port
8787

See the docker-entrypoint.sh script for details.

# Inspirred by:
https://github.com/fbascheper/JBoss-EAP-Docker-image



