#!/bin/bash
#
# 1b) <doctrain>, </doctrain> y <doctest>, </doctest>
#
# This script dumps a valid XML file from non-well-formed
# XML documents (provided by IR group) and is part of:
#
# MIEX - Metadata and Information Extractor from small XML documents.
# Copyright (C) 2007 - Nacho Barrientos Arias
#
# This program is free software; you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation; either version 2 of the License.
# 
# This program is distributed in the hope that it will be useful,
# but WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
# GNU General Public License for more details.
# 
# You should have received a copy of the GNU General Public License along
# with this program; if not, write to the Free Software Foundation, Inc.,
# 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.

if [ ! $# -eq 1 ]; then
  echo "Usage: $0 inputFile";
  exit -1;
fi

if [ ! -f $1 ]; then
  echo "ERROR: Input file not found or not readable.";
  exit -1;
fi

awk -f fixxml-1b-parser.awk $1
