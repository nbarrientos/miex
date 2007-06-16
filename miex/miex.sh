#!/bin/bash
# Small launcher for MIEX
#
# Nacho Barrientos Arias <nacho@criptonita.com>

JAVA=java
CBASEDIR=/usr/lib/java

if [ -z `/usr/bin/which java` ]; then
  echo "J2SE 1.5.0 (or higher) JRE is not in PATH, exiting..."
  exit -1
fi

if [ -f build/lib/miex.jar ]; then
	MIEXJAR=build/lib/miex.jar
else
	MIEXJAR=/usr/lib/miex/miex.jar
fi

CLASSPATH=$MIEXJAR:$CBASEDIR/JSAP-2.1.jar:$CBASEDIR/sax2.jar:$CBASEDIR/stanford-parser-2006-06-11.jar:$CBASEDIR/mysql-connector-java-5.0.5-bin.jar

$JAVA -Xmx1024M -cp $CLASSPATH es.uniovi.aic.miex.run.Miex $@
