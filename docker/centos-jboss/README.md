
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
````
docker run --network my-net --name jboss-myapp --publish-all -it centos-jboss /bin/bash
```

Run without commandline

```
docker run --network my-net --name jboss-myapp --publish-all -it centos-jboss
```

No commandline.

# Inspirred by:
https://github.com/fbascheper/JBoss-EAP-Docker-image



