# server configuration snippet
<extension module="org.jboss.qe.kremilek"/>
...
<subsystem xmlns="urn:pkremens:kremilek:1.0">
   <child strings="test1, test2"/>
</subsystem>

-----------------------------

# build
mvn install

# add module into EAP
cp -r target/module/org $JBOSS_HOME/modules/system/layers/base/

# start standalone server and connect to CLI
$JBOSS_HOME/bin/standalone.sh &
$JBOSS_HOME/bin/jboss-cli.sh -c

# use CLI to setup extension
/extension=org.jboss.qe.kremilek:add()

# re-connet client to collect commands from extension
connect

# add subsystem
/subsystem=kremilek:add
/subsystem=kremilek/config=child:add(strings=["test1","test2"]
/subsystem=kremilek/config=child:read-resource
/subsystem=kremilek/config=child:read-attribute(name=strings)?

# available commands
increase
decrease
kremilek-hello
kremilek-hello --help

mvn clean install
cp -r /home/pkremens/devel/drunken-octo-shame/subsys-test/target/module/org /home/pkremens/workspace/modules/system/layers/base/

Stary zpusob:
pridat dependency:
<module name="org.jboss.qe.kremilek" optional="true" services="import"/>
do module xml pro org.jboss.as.cli
        