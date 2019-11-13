#!/bin/sh
#
OOo=/opt/openoffice.org2.0/program/soffice
PIDFILE=/var/run/soffice.pid
# Set any default JVM values
export JAVA_HOME=$HOME/programs/java
export JAVA_OPTS="-Xms256m -Xmx512m -Djava.awt.headless=true -server -XX:CompileCommand=exclude,org/apache/lucene/index/IndexReader\$1,doBody"

case "$1" in
start)
DISPLAY=:99
echo -n "Starting OpenOffice service..."

$OOo -display $DISPLAY -accept="socket,host=localhost,port=8100;urp;StarOffice.ServiceManager" -nologo -headless  &
echo "Done."
;;
stop)
echo -n "Stopping Openoffice service..."
killall soffice.bin
echo "."
;;
restart)
$0 stop
$0 start
;;
*)
echo "Usage: /etc/init.d/sofficed.sh {start|stop|restart}"
exit 1
esac

exit 0
