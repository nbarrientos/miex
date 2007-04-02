package es.uniovi.aic.miex.filter;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.ling.TaggedWord;

import java.util.ArrayList;

public class GlobalFilter
{

	public GlobalFilter()
	{
		swd = new StopWordsDetector();
		utd = new UselessTagsDetector();
		nd = new NumbersDetector();
		pt = new Porter();
	}

  public ArrayList<TypedDependency> cleanDependencies(ArrayList<TypedDependency> rs)
  {

    ArrayList<TypedDependency> cleanDeps = new ArrayList<TypedDependency>();

    for(TypedDependency dep: rs)
    {
			if(swd.isCleanDep(dep) && nd.isCleanDep(dep))
				cleanDeps.add(dep);
    }

    System.out.print(" R(" + (rs.size()-cleanDeps.size()) + ") ");

    return cleanDeps;
  }

  public ArrayList<TaggedWord> cleanProperties(ArrayList<TaggedWord> words)
  {

    ArrayList<TaggedWord> cleanProps = new ArrayList<TaggedWord>();

    for(TaggedWord wordAndProp: words)
    {
      if(swd.isCleanProp(wordAndProp) && 
				 utd.isCleanProp(wordAndProp) &&
				 nd.isCleanProp(wordAndProp))
        cleanProps.add(wordAndProp);
    }

    System.out.print(" R(" + (words.size()-cleanProps.size()) + ") ");

    return cleanProps;
  }

	/* Normalization utils */

	public ArrayList<TaggedWord> normalizeProperties(ArrayList<TaggedWord> words)
	{
		ArrayList<TaggedWord> normalizedProps = new ArrayList<TaggedWord>();

		for(TaggedWord wordAndProp: words)
		{
			String nword = pt.stripAffixes(wordAndProp.word());

			String ncat = normalizeCat(wordAndProp.tag());

			normalizedProps.add(new TaggedWord(nword, ncat)); 
		}

		return normalizedProps;
	}

	private String normalizeCat(String cat)
	{
		if(cat.matches("VB.*"))
			return "VB";	
		else
			return "X";
	}

	Porter pt;
	StopWordsDetector swd;
	UselessTagsDetector utd;
	NumbersDetector nd;
}
