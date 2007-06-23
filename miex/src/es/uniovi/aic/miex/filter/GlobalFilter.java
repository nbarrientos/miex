package es.uniovi.aic.miex.filter;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.ling.MapLabel;

import edu.stanford.nlp.ling.Word;

import java.util.ArrayList;

import es.uniovi.aic.miex.datastr.ExtendedTaggedWord;

/** 
 * Filter handler 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class GlobalFilter
{

	/** 
	 * Initialize all filters 
	 */
	public GlobalFilter()
	{
		swd = new StopWordsDetector();
		utd = new UselessTagsDetector();
		nd = new NumbersDetector();
		pt = new Porter();
	}

  /** 
   * Cleans a set of dependencies using the initialized filters 
   * 
   * @param rs The dependencies to clean
   * @return The cleaned up dependencies
   */
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

  /** 
   * Cleans a set of properties using the initialized filters 
   * 
   * @param words The properties to clean
   * @return The cleaned up properties
   */
  public ArrayList<ExtendedTaggedWord> cleanProperties(ArrayList<ExtendedTaggedWord> words)
  {

    ArrayList<ExtendedTaggedWord> cleanProps = new ArrayList<ExtendedTaggedWord>();

    for(ExtendedTaggedWord wordAndProp: words)
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

	/** 
	 * Normalizes a set of properties 
	 * 
	 * @param words The properties to normalize
	 * @return The normalized properties
	 */
	public ArrayList<ExtendedTaggedWord> normalizeProperties(ArrayList<ExtendedTaggedWord> words)
	{
		ArrayList<ExtendedTaggedWord> normalizedProps = new ArrayList<ExtendedTaggedWord>();

		for(ExtendedTaggedWord wordAndProp: words)
		{
			String nword = pt.stripAffixes(wordAndProp.word());

			String ncat = normalizeCat(wordAndProp.tag());

			if(!(nword.equals("")))
				normalizedProps.add(new ExtendedTaggedWord(nword, ncat, wordAndProp.phrase())); 
		}

		return normalizedProps;
	}

	/** 
	 * Normalizes a set of dependencies 
	 * 
	 * @param rs The dependencies to normalize
	 * @return The normalized dependencies
	 */
	public ArrayList<TypedDependency> normalizeDependencies(ArrayList<TypedDependency> rs)
	{
		ArrayList<TypedDependency> normalizedDeps = new ArrayList<TypedDependency>();

		for(TypedDependency dep: rs)
		{
			MapLabel governorLabel = (MapLabel)dep.gov().label();
			MapLabel depLabel = (MapLabel)dep.dep().label();

			String governorWord = governorLabel.toString("value");
			String depWord = depLabel.toString("value");

			String nmword = pt.stripAffixes(governorWord);
			String nsword = pt.stripAffixes(depWord);

			if( !(nmword.equals("")) && !(nsword.equals("")) )
				normalizedDeps.add(new TypedDependency(dep.reln(), new TreeGraphNode(new Word(nmword)), new TreeGraphNode(new Word(nsword)))); 
		}

		return normalizedDeps;

	}

	private String normalizeCat(String cat)
	{
		if(cat.matches("VB.*"))
			return "VB";	
		else
    if(cat.matches("RB.*"))
      return "RB";
    else
    if(cat.matches("NN.*"))
      return "NN";
    else
    if(cat.matches("JJ.*"))
      return "JJ";
    else
			return "X";
	}

	private Porter pt;
	private StopWordsDetector swd;
	private UselessTagsDetector utd;
	private NumbersDetector nd;
}
