#!/usr/bin/env bash

# set EAP_HOME only if empty
test -z EAP_HOME && export EAP_HOME=~/workspace/jboss-eap-7.0/bin
export PROPERTIES=${EAP_HOME}/cli.properties
export CLI_SCRIPT=${EAP_HOME}/test.cli

function clean_env() {
    cd $EAP_HOME
    git wipeout
}

function enable_resolve_parameter_values() {
    sed -i 's/values>false/values>true/' ${EAP_HOME}/jboss-cli.xml
}

function enable_echo_command() {
    sed -i 's/command>false/command>true/' ${EAP_HOME}/jboss-cli.xml
}
