<?php

require_once 'magpierss/rss_fetch.inc';

class NewsAgent
{

function printNews()
{

	$url = 'http://sourceforge.net/export/rss2_projnews.php?group_id=187602';
	$rss = fetch_rss($url);

	foreach ($rss->items as $item )
	{
		$title = $item[title];
		$url   = $item[link];
		$pubDate = $item[pubdate];

		$date = explode(" ", $pubDate);

		$ret .= "<li><span style=\"font-size: 9pt;\">[$date[1]/$date[2]/$date[3]]</span> <a href=\"$url\">$title</a></li>";
	}

	return $ret;

}

}
?>
