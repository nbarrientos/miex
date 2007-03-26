package es.uniovi.aic.miex.filter;

import java.util.HashSet;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.ling.MapLabel;
import edu.stanford.nlp.ling.TaggedWord;

// Package class
class NumbersDetector 
{
  public NumbersDetector()
	{
		regexp = "-?[0-9]+(,[0-9]+)?"; // (-)Natural(,Natural)
	} 

	public boolean isCleanDep(TypedDependency dep)
	{
		MapLabel governorLabel = (MapLabel)dep.gov().label();
		MapLabel depLabel = (MapLabel)dep.dep().label();

		String governorWord = governorLabel.toString("value");
		String depWord = depLabel.toString("value");

		return !(governorWord.matches(regexp) || depWord.matches(regexp));
	}

	public boolean isCleanProp(TaggedWord wordAndProp)
  {
		return !(wordAndProp.word().matches(regexp));
	}

	String regexp;

} //class
