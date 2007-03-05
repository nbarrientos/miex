package es.uniovi.aic.miex.semantic;

import java.util.ArrayList;
import java.util.List;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.MapLabel;

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

	// Atributes
	LexicalizedParser lp;

	String grammarName;

}
