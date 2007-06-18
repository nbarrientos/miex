<!--
  EXPERIMENTAL, UNSTABLE AND DIRTY SOFTWARE
  *** DO NOT DISTRIBUTE WITH MIEX ***
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html><head>
<title>foo bar</title>
</head>
<body>

<h2>Filter by category:</h2>

<form action="showcat.php" method="post">

<input type="text" name="cat" size="20" />
<input type="submit" value="Show"/>

</form>

<?php
    $dbcon = mysql_connect('localhost','pfc','pfc0');

    if (!$dbcon) die(mysql_error());

    mysql_select_db('miex', $dbcon);

		if(isset($_POST['cat']))
		{

		$c = $_POST['cat'];

    print "<h3>Unnormalized Words for category: $c</h3>";

		$query = "SELECT document.title as title, word.string as word, property.string as prop, wordpropdoc.fromTitle as fromTitle, wordpropdoc.normalized, word.word_id as wi FROM wordpropdoc INNER JOIN property INNER JOIN word INNER JOIN document INNER JOIN doccat INNER JOIN category WHERE document.collection_id=wordpropdoc.col_id AND document.document_id=wordpropdoc.doc_id AND property.property_id=wordpropdoc.prop_id AND word.word_id=wordpropdoc.word_id AND document.document_id=doccat.doc_id AND document.collection_id=doccat.col_id AND doccat.cat_id=category.category_id AND wordpropdoc.normalized='0' AND category.string='".$c."'";

    $res = mysql_query($query, $dbcon);

    if (!$res) die(mysql_error());

    print "<table border=\"1\">";

		print "<tr><th>Document Title</th><th>Word</th><th>Grammatical category</th><th>From Title</th></tr>";


		while ($row = mysql_fetch_assoc($res))
		{
			if($row['fromTitle'] == 1)
				$ft = "Yes";
			else
				$ft = "No";

			print "<tr>";
			print "<td>".$row['title']."</td>";
			print "<td>".$row['word']."</td>";
			print "<td>".$row['prop']."</td>";
			print "<td>".$ft."</td>";
			print "</tr>";
		}

		print "</table>";

		mysql_free_result($res);

		print "<h3>Normalized Words for category: $c</h3>";

		$query = "SELECT document.title as title, word.string as word, property.string as prop, wordpropdoc.fromTitle, wordpropdoc.normalized, word.word_id as wi FROM wordpropdoc INNER JOIN property INNER JOIN word INNER JOIN document INNER JOIN doccat INNER JOIN category WHERE document.collection_id=wordpropdoc.col_id AND document.document_id=wordpropdoc.doc_id AND property.property_id=wordpropdoc.prop_id AND word.word_id=wordpropdoc.word_id AND document.document_id=doccat.doc_id AND document.collection_id=doccat.col_id AND doccat.cat_id=category.category_id AND wordpropdoc.normalized='1' AND category.string='".$c."'";

    $res = mysql_query($query, $dbcon);

    if (!$res) die(mysql_error());

    print "<table border=\"1\">";

		print "<tr><th>Document Title</th><th>Word</th><th>Grammatical category</th><th>From Title</th></tr>";


		while ($row = mysql_fetch_assoc($res))
		{
			if($row['fromTitle'] == 1)
				$ft = "Yes";
			else
				$ft = "No";

			print "<tr>";
      print "<td>".$row['title']."</td>";
      print "<td>".$row['word']."</td>";
      print "<td>".$row['prop']."</td>";
      print "<td>".$ft."</td>";
			print "</tr>";
		}

		print "</table>";

		mysql_free_result($res);
	
		}

?>

</body>
</html>
