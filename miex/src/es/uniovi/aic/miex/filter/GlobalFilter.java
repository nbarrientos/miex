package es.uniovi.aic.miex.filter;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.ling.MapLabel;
import edu.stanford.nlp.ling.TaggedWord;

import java.util.ArrayList;

public class GlobalFilter
{

	public GlobalFilter()
	{
		swd = new StopWordsDetector();
		utd = new UselessTagsDetector();
	}

  public ArrayList<TypedDependency> cleanDependencies(ArrayList<TypedDependency> rs)
  {

    ArrayList<TypedDependency> cleanDeps = new ArrayList<TypedDependency>();

    for(TypedDependency dep: rs)
    {
			if(swd.isCleanDep(dep))
				cleanDeps.add(dep);
    }

    System.out.print("(Removed " + (rs.size()-cleanDeps.size()) + ") ");

    return cleanDeps;
  }

  public ArrayList<TaggedWord> cleanProperties(ArrayList<TaggedWord> words)
  {

    ArrayList<TaggedWord> cleanProps = new ArrayList<TaggedWord>();

    for(TaggedWord wordAndProp: words)
    {
      if(swd.isCleanProp(wordAndProp) && utd.isCleanProp(wordAndProp))
        cleanProps.add(wordAndProp);
    }

    System.out.print("(Removed " + (words.size()-cleanProps.size()) + ") ");

    return cleanProps;
  }

	StopWordsDetector swd;
	UselessTagsDetector utd;
}
