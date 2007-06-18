<!-- 
	EXPERIMENTAL, UNSTABLE AND DIRTY SOFTWARE
	*** DO NOT DISTRIBUTE WITH MIEX ***
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html><head>
<title>foo bar</title>
</head>
<body>
<?php
    $dbcon = mysql_connect('localhost','pfc','pfc0');

    if (!$dbcon) die(mysql_error());

    mysql_select_db('miex', $dbcon);

		count_and_print_rows($dbcon);

    print "<h2>Misc information</h2>";

		$query = 'SELECT document.collection_id, collection.name, document.document_id, document.title, doctype.string FROM collection INNER JOIN document INNER JOIN doctype WHERE document.collection_id = collection.collection_id AND document.doctype_id=doctype.doctype_id';

    $res = mysql_query($query, $dbcon);

    if (!$res) die(mysql_error());

		print "<table border=\"1\">";
		
		print "<tr><th>Collection ID</th><th>Collection Name</th><th>Document ID</th><th>Document Title</th><th>Type</th></tr>";
	
    while ($row = mysql_fetch_assoc($res))
		{
				print "<tr>";
				print "<td>".$row['collection_id']."</td>";
				print "<td>".$row['name']."</td>";
				print "<td>".$row['document_id']."</td>";
				print "<td>".$row['title']."</td>";
				print "<td>".$row['string']."</td>";
				print "</tr>";
    }
    print "</table><br/>";

		mysql_free_result($res);

		$query = "SELECT document.collection_id, document.title, category.string as cat FROM document INNER JOIN doccat INNER JOIN category WHERE document.collection_id=doccat.col_id AND document.document_id=doccat.doc_id AND category.category_id=doccat.cat_id";

		$res = mysql_query($query, $dbcon);

		if (!$res) die(mysql_error());

		print "<table border=\"1\">";
		print "<tr><th>Collection ID</th><th>Document Title</th><th>Category Name</th></tr>";

		while ($row = mysql_fetch_assoc($res))
		{
			print "<tr>";
			print "<td>".$row['collection_id']."</td>";
			print "<td>".$row['title']."</td>";
			print "<td>".$row['cat']."</td>";
			print "</tr>";
		}

		print "</table><br/>";

    mysql_free_result($res);

		print "<h2>Unnormalized words</h2>";

		wordsAndTags($dbcon,0);

		print "<h2>Normalized words</h2>";

		wordsAndTags($dbcon,1);

		/* Dependencies */

		print "<h2>Unnormalized Dependencies</h2>";

		dependencies($dbcon,0);

		print "<h2>Normalized Dependencies</h2>";

		dependencies($dbcon,1);

		mysql_close($dbcon);

		function dependencies($dbcon,$normzd)
		{

		$query = "SELECT document.title as title, property.string as prop, mW.word_id as mid, sW.word_id as sid, mW.string as master, sW.string as slave, wordwordpropdoc.times, wordwordpropdoc.fromTitle FROM wordwordpropdoc INNER JOIN property INNER JOIN word as mW INNER JOIN word as sW INNER JOIN document WHERE wordwordpropdoc.masterWord_id=mW.word_id AND wordwordpropdoc.slaveWord_id=sW.word_id AND wordwordpropdoc.doc_id=document.document_id AND wordwordpropdoc.prop_id=property.property_id AND wordwordpropdoc.normalized='".$normzd."'";
		
    $res = mysql_query($query, $dbcon);

    if (!$res) die(mysql_error());

    print "<table border=\"1\">";
    print "<tr><th>Document Title</th><th>Master Word ID</th><th>Master Word</th><th>Slave Word ID</th><th>Slave Word</th><th>Relationship</th><th>Times</th><th>Where</th></tr>";

    while ($row = mysql_fetch_assoc($res))
    {
      if($row['fromTitle'] == 1)
        $ft = "TITLE";
      else
        $ft = "BODY";

      print "<tr>";
      print "<td>".$row['title']."</td>";
			print "<td>".$row['mid']."</td>";
      print "<td>".$row['master']."</td>";
			print "<td>".$row['sid']."</td>";
      print "<td>".$row['slave']."</td>";
      print "<td>".$row['prop']."</td>";
      print "<td>".$row['times']."</td>";
      print "<td>".$ft."</td>";
      print "</tr>";
    }

    print "</table>";

    mysql_free_result($res);

	}

	function count_and_print_rows($dbcon)
	{
		print "<h2>Database statistics</h2>";

    $query = "select count(*) as count from category";
    $res = mysql_query($query, $dbcon);
    if (!$res) die(mysql_error());
    $row = mysql_fetch_assoc($res);
		mysql_free_result($res);

    print "<p>Categories: ".$row['count']."</p>";

    $query = "select count(*) as count from document";
    $res = mysql_query($query, $dbcon);
    if (!$res) die(mysql_error());
    $row = mysql_fetch_assoc($res);
		mysql_free_result($res);

    print "<p>Documents: ".$row['count']."</p>";

    $query = "select count(*) as count from collection";
    $res = mysql_query($query, $dbcon);
    if (!$res) die(mysql_error());
    $row = mysql_fetch_assoc($res);
		mysql_free_result($res);

    print "<p>Collections: ".$row['count']."</p>";

    $query = "select count(*) as count from word";
    $res = mysql_query($query, $dbcon);
    if (!$res) die(mysql_error());
    $row = mysql_fetch_assoc($res);
    mysql_free_result($res);

    print "<p>Words: ".$row['count']."</p>";

	}

		function wordsAndTags($dbcon,$normzd)
		{

    $query = "SELECT document.title as title, word.string as word, property.string as proplabel, wordpropdoc.fromTitle, wordpropdoc.normalized, wordpropdoc.list_id as ListID FROM wordpropdoc INNER JOIN property INNER JOIN word INNER JOIN document WHERE document.collection_id=wordpropdoc.col_id AND document.document_id=wordpropdoc.doc_id AND property.property_id=wordpropdoc.prop_id AND word.word_id=wordpropdoc.word_id AND wordpropdoc.normalized='".$normzd."'";

    $res = mysql_query($query, $dbcon);

    if (!$res) die(mysql_error());

    print "<table border=\"1\">";
    print "<tr><th>Document Title</th><th>Word</th><th>Where</th><th>Word level tag</th><th>Phrase and clause level tags</th></tr>";

    while ($row = mysql_fetch_assoc($res))
    {
      if($row['fromTitle'] == 1)
        $ft = "TITLE";
      else
        $ft = "BODY";

      print "<tr>";
      print "<td>".$row['title']."</td>";
      print "<td>".$row['word']."</td>";
			print "<td>".$ft."</td>";
      print "<td>".$row['proplabel']."</td>";
			print "<td>";
			propertieslist($dbcon,$row['ListID']);
			print "</td>";
      print "</tr>";
    }

    print "</table>";

    mysql_free_result($res);

		}

    function propertieslist($dbcon,$listNumber)
    {

		$query = "SELECT list_id, property.string as name FROM propproplist INNER JOIN property WHERE propproplist.prop_id=property.property_id AND list_id='".$listNumber."'";

    $res = mysql_query($query, $dbcon);

    if (!$res) die(mysql_error());

    while ($row = mysql_fetch_assoc($res))
    {
			print $row['name']." ";
    }

    mysql_free_result($res);

    }

?>
</body>
</html>
