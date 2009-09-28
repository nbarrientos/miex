<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
  <meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
	<meta name="keywords" content="MIEX, parser, xml, stanford parser, java, mysql" />
  <link rel="stylesheet" href="style.css" type="text/css" />
  <title>MIEX: Metadata and Information Extractor from small XML documents</title>
</head>
<body>

<div id="wrapperAll">

<!-- Intro header -->

<div id="top">
	<div style="padding-top:26px; padding-right:28px; padding-bottom:0px; padding-left:0px;">
		<a href="http://www.aic.uniovi.es/ir/">Information Retrieval Group</a> - University of Oviedo
  </div>
</div>

 <div id="content">

<!-- Real header, project title and menu -->

	<div id="topWrapper">
		<div id="logo"><span style="color: #718F49;">MIEX</span></div>
		<div id="menu">
			<div style="padding-top:15px;">
        <span style="color: #718F49;">&raquo;</span>&nbsp;<a href="#overview">Overview</a>
				<span style="color: #718F49;">&raquo;</span>&nbsp;<a href="#people">People</a>
				<span style="color: #718F49;">&raquo;</span>&nbsp;<a href="#news">News</a>
				<span style="color: #718F49;">&raquo;</span>&nbsp;<a href="#contact">Contact</a>
				<span style="color: #718F49;">&raquo;</span>&nbsp;<a href="#download">Download</a>
        <span style="color: #718F49;">&raquo;</span>&nbsp;<a href="#legal">Legal</a>
			</div>
		</div>
	</div>
    
	<div id="sep">&nbsp;</div>
	<div id="archive"><b>Metadata and Information Extractor from small XML documents.</b></div>
	<div id="wrapperContent">
		<div id="wrapperNote">
			<div id="noteTop">&nbsp;</div>

        <div class="noteContent">
          <div class="title"><a name="overview">OVERVIEW</a></div>
						<p>
						The aim of <b>MIEX</b> (Metadata and Information Extractor from small XML documents) is to create a 
						wrapper for the <a href="http://nlp.stanford.edu/software/lex-parser.shtml">Stanford Parser</a>, developed 
						by <a href="http://nlp.stanford.edu/">The Stanford Natural Language Processing Group</a>, to extract and 
						store metadata (syntactic structures, relationships among words...) from simple XML documents in order to 
						apply IR methodologies to retrieve useful information afterwards.
						</p>
						
						<p>
						<b>MIEX</b> is being developed as my final year project at the 
						<a href="http://www.uniovi.es">University of Oviedo</a>. This application analyses a batch of XML 
						documents and navigates through Stanford Parser's output trees to store all the extracted semantic 
						information into a <a href="http://www.mysql.org">MySQL database</a>.</p>
						
						<p>This project was mainly developed to process small collections with this structure:</p>

						<div class="code">
						&lt;collection&gt;<br/>

            &lt;doctrain&gt;<br/>
            &lt;topic&gt;
            &lt;d&gt;debian&lt;/d&gt;
            &lt;d&gt;linux&lt;/d&gt;
            &lt;d&gt;OS&lt;/d&gt;
            &lt;/topic&gt;<br/>
            &lt;title&gt;Debian project&lt;/title&gt;<br/>
            &lt;body&gt;
            Debian is a free operating system (OS) for your computer. An operating system is the set of basic
            programs and utilities that make your computer run. Debian uses the Linux kernel
            (the core of an operating system), but most of the basic OS tools come from the GNU project;
            hence the name GNU/Linux
            &lt;/body&gt;<br/>
            &lt;/doctrain&gt;<br/>


						&lt;doctrain&gt;<br/>
						&lt;topic&gt;
						&lt;d&gt;computing&lt;/d&gt;
						&lt;d&gt;search&lt;/d&gt;
						&lt;d&gt;engine&lt;/d&gt;
						&lt;/topic&gt;<br/>
						&lt;title&gt;Google&lt;/title&gt;<br/>
						&lt;body&gt;
						Google Inc. (NASDAQ: GOOG and LSE: GGEA) is an American public corporation, specializing 
						in Internet search and online advertising. The company had 10,674 full-time employees
						as of December 31, 2006, and is based in Mountain View, California.
						&lt;/body&gt;<br/>
						&lt;/doctrain&gt;<br/>

						&lt;/collection&gt;
						
						</div>

						<p>It's planned to provide a configuration mechanism to point <b>MIEX</b> which fields are suitable
						for extract semantic information, on the other hand, XML input files are validated using
				    a <a href="http://www.w3.org/XML/Schema">XML schema</a> supplied by the end user. Let's see how
						<b>MIEX</b> works.</p>

					<div id="miexlogo"><img src="images/miex.png" alt="Miex structure"/></div>

            <p>
            It's also available a <a href="resources/flow.png">flow diagram</a> and the current
            <a href="resources/bdd-e-r.png">DB entity-relationship diagram</a> to help non-spanish 
            speakers figure out what happens inside the processing step.
            </p>

						<p>For more information, development and end-user tools (Documentation, Git, feature requests, 
						project status...) look at <a href="http://sourceforge.net/projects/miex/">the project page at SourceForge</a>.</p>

        </div>

        <div class="noteContent">
          <div class="title"><a name="people">PEOPLE</a></div>
					<p>The following people are involved in this project.</p>
          <ul>
            <li><a href="http://criptonita.com/~nacho/">Nacho Barrientos Arias</a> (main developer)</li>
						<li><a href="http://www.aic.uniovi.es/sirene/">Susana Irene Díaz Rodríguez</a> (project advisor)</li>
						<li><a href="http://www.aic.uniovi.es/elena/">Elena Montañés Roces</a> (project advisor)</li>
          </ul>
        </div>

				<div class="noteContent">
          <div class="title">
						<a name="news">NEWS</a> 
						<a href="http://sourceforge.net/export/rss2_projnews.php?group_id=187602">
							<img class="title" src="images/rss.jpg" alt="rss available"/>
						</a>
					</div>
					<ul>
					<?php
							require 'NewsAgent.php';

							$na = new NewsAgent();

							echo $na->printNews();

					?>
					</ul>
        </div>

        <div class="noteContent">
          <div class="title"><a name="contact">CONTACT</a></div>
          <ul>
            <li>Miex-announce - Miscellaneous announces 
							<a href="http://lists.sourceforge.net/mailman/listinfo/miex-announce">(subscribe)</a>.</li>
          </ul>
        </div>

        <div class="noteContent">
          <div class="title"><a name="download">DOWNLOAD</a></div>
          <ul>
            <li>
            <a href="http://sourceforge.net/project/showfiles.php?group_id=187602&amp;package_id=219063&amp;release_id=539835">MIEX 0.1</a>
            </li>
          </ul>
          <p>
          In order to get the latest changes and features, it is recommended to grab a fresh snapshot from git repository:
          </p>
        <div class="code">
          git clone git://miex.git.sourceforge.net/gitroot/miex/miex
        </div> 
        </div>

        <div class="noteContent">
          <div class="title"><a name="legal">LEGAL STUFF</a></div>
					<p><b>MIEX</b> is licensed under a <a href="http://www.gnu.org/copyleft/gpl.html">GPLv2 License</a>, 
					otherwise <b>MIEX</b> works thanks to a set of third-party
          libraries detailed in the following list.</p>
          <ul>
						<li>Natural language parser: <a href="http://nlp.stanford.edu/software/lex-parser.shtml">Stanford Parser</a> (GPL).</li>
						<li>Command line argument parser: <a href="http://www.martiansoftware.com/jsap/">JSAP</a> (LGPL - <a href="http://www.martiansoftware.com/jsap/license.html">see JSAP license notes</a>).</li>
			
						<li>XML parser: <a href="http://www.saxproject.org/">Simple API for XML (SAX)</a> (Public Domain).</li>

						<li>MySQL Java driver: <a href="http://www.mysql.com/products/connector/j/">MySQL Connector/J</a> (GPL).</li>

						<li>Project building tools: <a href="http://ant.apache.org/">Apache Ant</a> (Apache) or <a href="http://www.gnu.org/software/make/">GNU Make</a> (GPL).</li>

          </ul>
        </div>

				<div id="noteBottom">
          &nbsp;
        </div>

		</div> <!-- wrapperNote -->
  </div> <!-- wrapperContent -->
      
  </div> <!-- content -->
  
  <div id="bottom">
    &copy;&nbsp;Copyleft 2006-2007 Nacho Barrientos Arias. Last update: Mon, 28 Sep 2009 14:22:11 +0200.
	</div>

<!-- Meta logos -->

<a href="http://validator.w3.org/check/referer"><img src="images/xhtml.png" alt="Valid XHTML" /></a>&nbsp;<a href="http://jigsaw.w3.org/css-validator/check/referer"><img src="images/css.png" alt="Valid CSS" /></a>
<br/>
<a href="http://sourceforge.net"><img src="http://sflogo.sourceforge.net/sflogo.php?group_id=187602&amp;type=1" width="88" height="31" alt="SourceForge.net Logo" /></a>
	
</div> <!-- wrapperAll -->

</body>
</html>

