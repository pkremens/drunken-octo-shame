#!/usr/bin/env bash
# https://issues.jboss.org/browse/JBEAP-5018 - Add more details about using JBoss CLI in non-interactive mode (CLI guide)

source ./cli-all.sh

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
echo ${host}:${server}

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
    COMMAND="./jboss-cli.sh --file=${CLI_SCRIPT} --properties=${PROPERTIES} -Dhost=master"
    echo $COMMAND
    eval $COMMAND
}

clean_env
prepare_props
prepare_cli
enable_resolve_parameter_values
run_cli
