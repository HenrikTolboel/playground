#!/bin/bash

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

if [[ $# -eq 2 ]]; then
    Name=$1
    Port=$2
else
    echo "Usage: ${BASH_SOURCE[0]} <DB instance name> <port no for phpMyAdmin>"
    echo "Exiting..."
    exit 1
fi

echo "Name=$Name"

echo "Port=$Port"

docker run --rm --name admin-$Name -d --link $Name -p $Port:80 -e PMA_HOST=$Name phpmyadmin/phpmyadmin

echo "phpMyAdmin for $Name should now be at http://localhost:$Port"

