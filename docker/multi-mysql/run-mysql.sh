#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [[ $# -eq 1 ]]; then
    Name=$1
else
    echo "Parameter 1 names the database instance - missing - exiting"
    exit 1
fi

echo "Name=$Name"

echo "stopping $Name container"
docker stop $Name 2> /dev/null

echo "removing $Name container"
docker rm $Name 2> /dev/null

echo "re-starting $Name container"

mkdir -p $DIR/conf.d $DIR/initdb.d
mkdir -p $DIR/data/$Name

docker network create my-net

docker run \
	--rm \
	--name $Name \
	--network my-net \
	-v $DIR/conf.d:/etc/mysql/conf.d \
	-v $DIR/initdb.d:/docker-entrypoint-initdb.d \
	-v $DIR/data/$Name:/var/lib/mysql \
	-e MYSQL_ROOT_PASSWORD=root1 \
	-d \
	-P \
	mysql

echo "mysql $Name container should be running now. Port 3306 should be exposed on container"

