#!/usr/bin/env bash

export EAP_HOME=~/workspace/jboss-eap-7.0/bin

function clean_env() {
    cd $EAP_HOME
    git wipeout
}

function resolve_parameter_values() {
    sed -i 's/values>false/values>true/' ${EAP_HOME}/jboss-cli.xml
}

function echo_command() {
    sed -i 's/command>false/command>true/' ${EAP_HOME}/jboss-cli.xml
}
