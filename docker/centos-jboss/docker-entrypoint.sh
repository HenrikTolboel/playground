#!/bin/bash

#
# Default usage: docker-entrypoint.sh start-jboss
#
# Default value of environment variables:
#     JBOSS_USER=jbossadmin
#     JBOSS_PASSWORD=jboss@min1
#
#     JBOSS_MODE=standalone
#     JBOSS_CONFIG=standalone.xml
#

# set -e

export JBOSS_HOME=/opt/rh/jboss-eap-7.1

export JBOSS_CLI=$JBOSS_HOME/bin/jboss-cli.sh
export PATH=$JBOSS_HOME/bin:$PATH


#
# Determine JBoss configuration (parse environment variables)
#
if [ -z "$JBOSS_USER" ]; then
    JBOSS_USER=jbossadmin
fi
if [ -z "$JBOSS_PASSWORD" ]; then
    JBOSS_PASSWORD=jboss@min1
fi
if [ -z "$JBOSS_MODE" ]; then
    JBOSS_MODE=standalone
fi
if [ -z "$JBOSS_CONFIG" ]; then
    JBOSS_CONFIG=$JBOSS_MODE.xml
fi
echo "Using JBOSS_MODE=$JBOSS_MODE and JBOSS_CONFIG=$JBOSS_CONFIG"


if [ $JBOSS_MODE != "domain" ] && [ $JBOSS_MODE != "standalone" ]; then
    echo "JBOSS_MODE should be domain or standalone"
    exit 1
fi


function wait_for_server() {
    STARTUP_WAIT=30
    count=0

    until `$JBOSS_CLI -c "ls /deployment" &> /dev/null`; do
        sleep 1
        let count=$count+1;

        if [ $count -gt $STARTUP_WAIT ] ; then
            break
        fi
    done

    if [ $count -gt $STARTUP_WAIT ] ; then
        echo "JBoss startup timed out"
        cat /var/log/jboss/console.log
        exit 1
    fi
}


#
# Set JBoss admin user / password
#
$JBOSS_HOME/bin/add-user.sh -s -u $JBOSS_USER -p $JBOSS_PASSWORD


#
# Copy any modules to EAP module dir
#
if [ -d $HOME/modules ]; then
    echo "=> Copying customization modules to EAP module dir"
    for f in $HOME/modules/*; do
	cp -R  $f $JBOSS_HOME/modules
    done


fi

#
# Start JBoss EAP server
#
echo "=> Starting JBoss EAP server"
exec nohup $JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -c $JBOSS_CONFIG > /var/log/jboss/console.log 2>&1 &

echo "=> Waiting for the server to boot"
wait_for_server

#
# Run entrypoint scripts of dependent docker containers
#
if [ -d $HOME/docker-entrypoint-initdb.d ]; then
    for f in $HOME/docker-entrypoint-initdb.d/*.sh; do
        [ -f "$f" ] && . "$f"
    done
fi

#
# Restart JBoss EAP server
#
echo "=> Shutting down JBoss EAP server"
if [ "$JBOSS_MODE" = "standalone" ]; then
  $JBOSS_CLI -c ":shutdown"
else
  $JBOSS_CLI -c "/host=master:shutdown"
fi

echo "=> Restarting JBoss EAP server"

if [ "$JBOSS_DEBUG_SUSPEND" = "TRUE" ] || [ "$JBOSS_DEBUG_SUSPEND" = "true" ]; then
   JBOSS_DEBUG_CONFIG="--debug 8787"
   echo "Using debug configuration $JBOSS_DEBUG_CONFIG"
else
   JBOSS_DEBUG_CONFIG=""
   echo "Default mode (no suspend / debug)"
fi


if [ "$1" = 'start-jboss' ]; then
    exec $JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -c $JBOSS_CONFIG $JBOSS_DEBUG_CONFIG 2>&1 | tee /var/log/jboss/console.log


else
    exec nohup $JBOSS_HOME/bin/$JBOSS_MODE.sh -b 0.0.0.0 -bmanagement 0.0.0.0 -c $JBOSS_CONFIG $JBOSS_DEBUG_CONFIG > /var/log/jboss/console.log 2>&1 &
    wait_for_server

    echo "=> JBoss EAP server startup complete"

    exec "$@"
fi
