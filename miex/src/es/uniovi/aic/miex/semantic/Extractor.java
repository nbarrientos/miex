package es.uniovi.aic.miex.sematic;

import java.util.ArrayList;
//import java.io.*;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.MapLabel;

class Extractor
{
	Extractor()
	{
		lp = null;
		grammarName = new String("englishPCFG.ser.gz");
	}

	Extractor(String grammar)
	{
		lp = null;
		grammarName = grammar;
	}

	void load()
	{
		lp = new LexicalizedParser(grammarName);
	}

	ArrayList<TypedDependency> getDependencies(String sentence)
	{
		if(lp == null)
		{
			throw new UnInitializedParserException();
		}

		Tree parse = (Tree) lp.getBestPCFGParse();

		lp.parse(sentence);

		TreebankLanguagePack tlp = new PennTreebankLanguagePack();
		GrammaticalStructureFactory gsf = tlp.grammaticalStructureFactory();
		GrammaticalStructure gs = gsf.newGrammaticalStructure(parse);

		return (ArrayList<TypedDependency>)gs.getDeps(false);

	}

	// Atributes
	LexicalizedParser lp;

	String grammarName;

}
