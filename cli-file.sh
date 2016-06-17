#!/usr/bin/env bash

cd ~/workspace/jboss-eap-7.0/bin
PROPERTIES=`pwd`/cli.properties
CLI_SCRIPT=`pwd`/test.cli

function clean_env {
    git wipeout
}

# prepare properties file
function prepare_props {
    cat > $PROPERTIES << 'EOF'
server=server-four
EOF

echo "#####     $PROPERTIES     #####"
cat $PROPERTIES
echo ''
}

function prepare_cli {
    # prepare cli script
    cat > $CLI_SCRIPT << 'EOF'
set server=/host=${host}/server-config=${server}

# Set resolve-parameter-values=true to enable property resolution on client side
# echo ${host}:${server}

embed-host-controller

try
    $server:read-resource
catch
    $server:add(group=main-server-group, socket-binding-port-offset=350)
finally
    $server:write-attribute(name=auto-start, value=true)
end-try

/host=${host}:read-attribute(name=running-mode)

if (result != ADMIN_ONLY) of /host=${host}:read-attribute(name=running-mode)
    $server:start
else
    echo Cannot start $server when the Host Controller running mode is ADMIN_ONLY
end-if
EOF

    echo "#####     $CLI_SCRIPT     #####"
    cat $CLI_SCRIPT
    echo ''
}


function run_cli {
    echo "#####     RUN IT     #####"
    sed -i 's/values>false/values>true/' jboss-cli.xml
    echo "./jboss-cli.sh --file=test.cli --properties=cli.properties -Dhost=master"
    ./jboss-cli.sh --file=test.cli --properties=cli.properties -Dhost=master
    # bash jboss-cli.sh --file=$CLI_SCRIPT --properties=$PROPERTIES -Dhost=master
}

clean_env
prepare_props
prepare_cli
run_cli
