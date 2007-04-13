package es.uniovi.aic.miex.filter;

import java.util.HashSet;

import edu.stanford.nlp.ling.TaggedWord;

// Package class
class UselessTagsDetector 
{
  public UselessTagsDetector()
	{
		
		String[] uselessTagsLocal = new String[]{"``", "''", "-LRB-", "-RRB-",
		"-LCB-", "-RCB-", "--", "...", "*CR*", ",", ":", "."};
	
		uselessTags = new HashSet<String>();

		for(String word: uselessTagsLocal)
			uselessTags.add(word);

	} // constructor

	private boolean is(String tag)
	{
		return uselessTags.contains(tag);
/*
		if(uselessTags.contains(word.toLowerCase()))
		{
			System.out.println("Found stop word: " + word);
			return true;
		}
		else
			return false;
*/
					
	}

	public boolean isCleanProp(TaggedWord wordAndProp)
  {
		return !(is(wordAndProp.tag()));
	}

	private HashSet<String> uselessTags;

} //class

