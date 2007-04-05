package es.uniovi.aic.miex.semantic;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.MapLabel;
import edu.stanford.nlp.ling.TaggedWord;

public class Extractor
{
	public Extractor()
	{
		lp = null;
		lastParsedSentence = null;
		parse = null;
		grammarName = "lib/grammars/englishPCFG.ser.gz";
	}

	public Extractor(String grammar)
	{
		lp = null;
		parse = null;
		grammarName = grammar;
	}

	public void load()
	{
		lp = new LexicalizedParser(grammarName);
	}

	public boolean isLoaded()
	{
		if(lp == null)
			return false;
		else
			return true; 
	}

  private void letsGetParseReady(List sentence)
  throws Exception
  {
    if(lp == null)
    {
      throw new Exception("UnInitializedParser");
    }

    if(!(lastParsedSentence == sentence))
    {
      lp.parse(sentence);
      parse = (Tree) lp.getBestPCFGParse();
      lastParsedSentence = sentence;
    }
		else
			System.out.print(" C ");
  }

	public ArrayList<TypedDependency> getDependencies(List sentence)
	throws Exception
	{

		letsGetParseReady(sentence);

		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);

		return (ArrayList<TypedDependency>)gs.getDeps(false);

	}

	/* taggedYield() returns a Sentence object, which inheritates from
	   java.util.ArrayList<HasWord> so I'm sure it is an 
     ArrayList<TaggedWord> */
	@SuppressWarnings("unchecked")
	public ArrayList<TaggedWord> getProperties(List sentence)
	throws Exception
	{
		letsGetParseReady(sentence);

	  List sent = parse.taggedYield();
	
		return (ArrayList<TaggedWord>)sent;
	}

	private LexicalizedParser lp;

	private Tree parse;

	private String grammarName;

	private List lastParsedSentence;

}
