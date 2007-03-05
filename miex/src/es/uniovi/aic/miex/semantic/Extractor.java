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

import org.apache.commons.collections.map.MultiValueMap;

public class Extractor
{
	public Extractor()
	{
		lp = null;
		grammarName = "lib/grammars/englishPCFG.ser.gz";
	}

	public Extractor(String grammar)
	{
		lp = null;
		grammarName = grammar;
	}

	public void load()
	{
		lp = new LexicalizedParser(grammarName);
	}

	public ArrayList<TypedDependency> getDependencies(List sentence)
	throws Exception
	{

		if(lp == null)
		{
			throw new Exception("UnInitializedParser");
		}

		lp.parse(sentence);

		Tree parse = (Tree) lp.getBestPCFGParse();

		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);

		return (ArrayList<TypedDependency>)gs.getDeps(false);

	}

	public MultiValueMap getProperties(List sentence)
	throws Exception
	{
		if(lp == null)
		{
			throw new Exception("UnInitializedParser");
		}

		lp.parse(sentence);

		Tree parse = (Tree) lp.getBestPCFGParse();

	  List sent = parse.taggedYield();

		MultiValueMap wordAndTag = new MultiValueMap();

		for(Iterator it = sent.iterator(); it.hasNext();)
		{
			TaggedWord tw = (TaggedWord) it.next();
			wordAndTag.put(tw.word(),tw.tag());
		}
		
		return wordAndTag;
	}

	LexicalizedParser lp;

	String grammarName;

}
