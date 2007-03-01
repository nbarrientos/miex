#!/bin/bash
#
# This scripts creates a valid XML file from non-well-formed
# XML documents (provided by IR group).
#
# 2b) <doc train="1">, </doc> y <doc test="1">, </doc>
#
# DEPRECATED DEPRECATED DEPRECATED DEPRECATED
# DEPRECATED DEPRECATED DEPRECATED DEPRECATED

if [ ! $# -eq 1 ]; then
	echo "Usage: $0 inputFile";
	exit -1;
fi

if [ ! -f $1 ]; then
  echo "ERROR: Input file not found or not readable.";
  exit -1;
fi

echo "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>" > $1.new
echo "<COLLECTION>" >> $1.new
sed -e 's/DOCTRAIN/DOC TRAIN="1"/g' -e 's/DOCTEST/DOC TEST="1"/g' $1 >> $1.new
echo "</COLLECTION>" >> $1.new
