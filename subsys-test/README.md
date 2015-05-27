+       <extension module="org.jboss.qe.kremilek"/>
         <extension module="org.wildfly.extension.batch"/>
         <extension module="org.wildfly.extension.bean-validation"/>
         <extension module="org.wildfly.extension.io"/>


+       <subsystem xmlns="urn:pkremens:kremilek:1.0"/>
        <subsystem xmlns="urn:jboss:domain:datasources:3.0">
         
mvn clean install ; cp -r /home/pkremens/devel/drunken-octo-shame/subsys-test/target/module/org /home/pkremens/workspace/modules/system/layers/base/

./standalone.sh &
./jboss-cli.sh -c
[standalone@localhost:9990 /] kremilek-hello
hello world from Kremilek!

[standalone@localhost:9990 /] cd subsystem=kremilek
[standalone@localhost:9990 subsystem=kremilek] :read-resource-description
{
    "outcome" => "success",
    "result" => {
        "description" => "This is my subsystem",
        ...
        }
    }
}

[standalone@localhost:9990 subsystem=kremilek] :read-operation-description(name=add)
{
    "outcome" => "success",
    "result" => {
        "operation-name" => "add",
        "description" => "Operation Adds kremilek subsystem",
        ...
        }
    }
}