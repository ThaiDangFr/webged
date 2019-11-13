#!/bin/sh
#
XVFB=/usr/X11R6/bin/Xvfb
PIDFILE=/var/run/xvfb.pid

test -x $XVFB || exit 0

case "$1" in
start)
echo -n "Starting virtual X frame buffer: Xvfb"
export DISPLAY=:99.0
$XVFB :99 -screen 0 800x600x16 &
echo "."
;;
stop)
echo -n "Stopping virtual X frame buffer: Xvfb"
killall Xvfb
echo "."
;;
restart)
$0 stop
$0 start
;;
*)
echo "Usage: /etc/init.d/xvfb.sh {start|stop|restart}"
exit 1
esac

exit 0
