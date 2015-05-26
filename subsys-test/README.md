+       <extension module="org.jboss.qe.subsys"/>
         <extension module="org.wildfly.extension.batch"/>
         <extension module="org.wildfly.extension.bean-validation"/>
         <extension module="org.wildfly.extension.io"/>
@@ -136,6 +137,7 @@
             </thread-pool>
         </subsystem>
         <subsystem xmlns="urn:jboss:domain:bean-validation:1.0"/>
+       <subsystem xmlns="urn:mycompany:mysubsystem:1.0"/>
         <subsystem xmlns="urn:jboss:domain:datasources:3.0">
         
cp -r /home/pkremens/devel/drunken-octo-shame/subsys-test/target/module/org /home/pkremens/workspace/modules/system/layers/base/