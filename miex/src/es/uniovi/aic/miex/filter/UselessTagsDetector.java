package es.uniovi.aic.miex.filter;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.ling.MapLabel;
import edu.stanford.nlp.ling.TaggedWord;

public class UselessTagsDetector 
{
  public UselessTagsDetector()
	{
		
		String[] otherWordsLocal = new String[]{"``", "''", "-LRB-", "-RRB-",
		"-LCB-", "-RCB-", "--", "...", "*CR*", ",", ":", "."};
	
		otherWords = new HashSet<String>();

		for(String word: otherWordsLocal)
			otherWords.add(word);

	} // constructor

	private boolean is(String tag)
	{
		return otherWords.contains(tag);
/*
		if(otherWords.contains(word.toLowerCase()))
		{
			System.out.println("Found stop word: " + word);
			return true;
		}
		else
			return false;
*/
					
	}
/*
	public boolean isCleanDep(TypedDependency dep)
	{
		MapLabel governorLabel = (MapLabel)dep.gov().label();
		MapLabel depLabel = (MapLabel)dep.dep().label();

		String governorWord = governorLabel.toString("value");
		String depWord = depLabel.toString("value");

		if(!is(governorWord) && !is(depWord))
			return true;
		else
			return false;
	}
*/

	public boolean isCleanProp(TaggedWord wordAndProp)
  {
		if(!is(wordAndProp.tag()))
			return true;
		else
			return false;
	}

	HashSet<String> otherWords;

} //class

