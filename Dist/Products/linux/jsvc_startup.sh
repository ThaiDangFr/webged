#!/bin/sh



export JAVA_HOME=/home/thai/programs/java
CATALINA_HOME=/home/thai/programs/tomcat
TOMCAT_USER=thai
CATALINA_PID=$CATALINA_HOME/jsvc.pid



case "$1" in
start)
cd $CATALINA_HOME 
./bin/jsvc -Djava.endorsed.dirs=./common/endorsed -cp ./bin/bootstrap.jar \
-user $TOMCAT_USER -pidfile $CATALINA_PID \
-outfile ./logs/catalina.out -errfile ./logs/catalina.err \
-Dcatalina.home="$CATALINA_HOME" \
-Xms256m -Xmx600m -Djava.awt.headless=true \
org.apache.catalina.startup.Bootstrap start
echo "Done."
;;
stop)
kill -9 `cat $CATALINA_PID`
echo "."
;;
restart)
$0 stop
$0 start
;;
*)
echo "Usage $0 {start|stop|restart}"
exit 1
esac

exit 0
