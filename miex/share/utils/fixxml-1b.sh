#!/bin/bash
#
# This script dumps a valid XML file from non-well-formed
# XML documents (provided by IR group).
#
# 1b) <doctrain>, </doctrain> y <doctest>, </doctest>
#
# Copyright (C) 2006 Nacho Barrientos Arias

if [ ! $# -eq 1 ]; then
  echo "Usage: $0 inputFile";
  exit -1;
fi

if [ ! -f $1 ]; then
  echo "ERROR: Input file not found or not readable.";
  exit -1;
fi

awk -f fixxml-1b-parser.awk $1
