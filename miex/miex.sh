#!/bin/bash
# Small Bash launcher for MIEX
#
# Nacho Barrientos Arias <nacho@criptonita.com>

JAVA=java
CBASEDIR=/usr/lib/java
CLASSPATH=build/lib/miex.jar:$CBASEDIR/JSAP-2.1.jar:$CBASEDIR/sax2.jar:$CBASEDIR/stanford-parser-2006-06-11.jar:$CBASEDIR/mysql-connector-java-5.0.5-bin.jar

#if [ ! -x $JAVA ]; then
#	echo "J2SE 1.5.0 JRE required, set \$JAVA variable properly, check permissions and try again, exiting..."
#	exit -1
#fi

$JAVA -Xmx1024M -cp $CLASSPATH es.uniovi.aic.miex.run.Miex $@
