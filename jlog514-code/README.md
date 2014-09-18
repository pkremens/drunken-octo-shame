Syslog server downloaded from http://sourceforge.net/projects/jlog514/

UDP based syslog listening on port 9998

did some little changes so it listen to port 9998 by default (can be used without sudo) and added some console printing.

To test the EAP audit logging start open project in idea and run the jlog514 class.
 - push start button so the server start to listening on port 9998

EAP configuration - STANDALONE:
 - start the standalone server
 - enable audit log:
/core-service=management/access=audit/logger=audit-log:write-attribute(name=enabled,value=true)
 - by default, audit is logged into file stored in ${JBOSS_HOME}/standalone/data/audit.log
 - add syslog handler:
 - Create a syslog handler named msyslog (binded to port 9998)
batch
/core-service=management/access=audit/syslog-handler=mysyslog:add(formatter=json-formatter)
/core-service=management/access=audit/syslog-handler=mysyslog/protocol=udp:add(host=localhost,port=9998)
run-batch
 - Add a reference to the syslog handler.
/core-service=management/access=audit/logger=audit-log/handler=mysyslog:add
 - dummy operation
/core-service=management/management-interface=http-interface:write-attribute(name=console-enabled, value=true)

EAP configuration - DOMAIN:
 - start the server in domain mode
 - enable audit log:
/host=master/core-service=management/access=audit/logger=audit-log:write-attribute(name=enabled, value=true)
 - add syslog handler:
batch
/host=master/core-service=management/access=audit/syslog-handler=mysyslog:add(formatter=json-formatter)
/host=master/core-service=management/access=audit/syslog-handler=mysyslog/protocol=udp:add(host=localhost,port=9998)
run-batch
 - Add a reference to the syslog handler.
/host=master/core-service=management/access=audit/logger=audit-log/handler=mysyslog:add
 - dummy operation
/host=master/core-service=management/management-interface=http-interface:write-attribute(name=console-enabled, value=true)


Use this to create a new lightweight syslog server for EAP testing purposes.

See EAP commit [WFLY-3162] for simpleSyslogServer example