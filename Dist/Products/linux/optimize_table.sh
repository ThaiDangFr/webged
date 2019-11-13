#!/bin/sh

DBNAME=$2
DBUSER=root
DBPASS=root

printUsage() {
echo "Usage: $0"
echo " --optimize <tablename>"
return
}


doAllTables() {
# get the table names
TABLENAMES=`mysql -u $DBUSER -p$DBPASS -D $DBNAME -e "SHOW TABLES\G;"|grep 'Tables_in_'|sed -n 's/.*Tables_in_.*: \([_0-9A-Za-z]*\).*/\1/p'`

# loop through the tables and optimize them
for TABLENAME in $TABLENAMES
do
mysql -u $DBUSER -p$DBPASS -D $DBNAME -e "OPTIMIZE TABLE $TABLENAME;"
done
}

if [ $# -eq 0 ] ; then
printUsage
exit 1
fi

case $1 in
--optimize) doAllTables;;
--help) printUsage; exit 1;;
*) printUsage; exit 1;;
esac
