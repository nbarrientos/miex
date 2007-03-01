#!/usr/bin/awk -f
#
# This script dumps a valid XML file from non-well-formed
# XML documents (provided by IR group).
#
# 1b) <doctrain>, </doctrain> y <doctest>, </doctest>
#
# Copyright (C) 2006 Nacho Barrientos Arias
#
# AWK Parser

BEGIN{
		isTrain=0;
		print "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>";
		print "<COLLECTION>"
}

		/<DOCTRAIN>/ {isTrain=1;};
	
		/<DOCTEST>/ {isTrain=0;};

		! /<\/DOC>/ {print};

		/<\/DOC>/ { 
				if(isTrain == 1) 
					sub(/DOC/, "DOCTRAIN"); 
				else
					sub(/DOC/, "DOCTEST");
				print };

END{
	print "</COLLECTION>";
}
