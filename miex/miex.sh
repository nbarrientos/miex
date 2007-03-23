#!/bin/bash
# Small Bash launcher for MIEX
#
# Nacho Barrientos Arias <nacho@criptonita.com>

JAVA=java
CLASSPATH='lib/miex.jar:lib/JSAP-2.1.jar:lib/sax2.jar:lib/stanford-parser-2006-06-11.jar:lib/mysql-connector-java-5.0.5-bin.jar'

#if [ ! -x $JAVA ]; then
#	echo "J2SE 1.5.0 JRE required, set \$JAVA variable properly, check permissions and try again, exiting..."
#	exit -1
#fi

$JAVA -Xmx512M -cp $CLASSPATH es.uniovi.aic.miex.run.Miex $@
