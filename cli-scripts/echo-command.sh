#!/usr/bin/env bash
# https://issues.jboss.org/browse/EAP7-525 - Requesting CLI Equivalent of Remote Echo / set -x in non-interactive mode (from within scripts)
# https://issues.jboss.org/browse/WFCORE-1614 - Requesting CLI Equivalent of Remote Echo / set -x in non-interactive mode (from within scripts)

export EAP_HOME=~/workspace/wildfly-core-3.0.0.Alpha3-SNAPSHOT/bin
source ./cli-all.sh

function prepare_cli {
    # prepare cli script
    cat > $CLI_SCRIPT << 'EOF'
embed-server

try
    :read-attribute(name=foo)
catch
    /system-property=catch:add(value=bar)
finally
    /system-property=finally:add(value=bar)
end-try

/system-property=*:read-resource

if (outcome == success) of /system-property=catch:read-attribute(name=value)
    set prop=Catch\ block\ was\ executed
    /system-property=finally:write-attribute(name=value, value=if)
else
    set prop=Catch\ block\ wasn\'t\ executed
    reload
    stop-embedded-server
    asdqwe-error
    /system-property=finally:write-attribute(name=value, value=else)
end-if
reload
stop-embedded-server
EOF

    echo "#####     $CLI_SCRIPT     #####"
    cat $CLI_SCRIPT
    echo ''
}

function run_cli {
    echo "#####     RUN IT     #####"
    COMMAND="./jboss-cli.sh --file=${CLI_SCRIPT}"
    echo $COMMAND
    eval $COMMAND
}


clean_env
enable_echo_command
prepare_cli
run_cli
