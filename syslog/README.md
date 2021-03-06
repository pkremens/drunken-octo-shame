UDPSyslogListener - UDP based syslog listening on port 9998 -> prints to console
TCPSyslogListener - TCP based syslog listening on port 9998 -> prints to console
PortBlockingHelper - TCP port blocker - 9998

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

Recconection timeout is valid only for TCP and TLS protocol
host=station5.brq.redhat.com, port=514
service rsyslog start
service rsyslog stop
vim /var/log/messages

####################################################################################
/core-service=management/access=audit/logger=audit-log:write-attribute(name=enabled,value=true)
batch
/core-service=management/access=audit/syslog-handler=mysyslog:add(formatter=json-formatter, max-failure-count=2)
/core-service=management/access=audit/syslog-handler=mysyslog/protocol=tcp:add(host=station5.brq.redhat.com, port=514, reconnect-timeout=30)
run-batch
/core-service=management/access=audit/logger=audit-log/handler=mysyslog:add

/core-service=management/management-interface=http-interface:write-attribute(name=console-enabled, value=true)

####################################################################################

syslog stats:
ls -l /core-service=management/access=audit/syslog-handler=mysyslog

change UPD to TCP
batch
/core-service=management/access=audit/syslog-handler=mysyslog/protocol=udp:remove()
/core-service=management/access=audit/syslog-handler=mysyslog/protocol=tcp:add(host=localhost,port=9998)
run-batch

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
