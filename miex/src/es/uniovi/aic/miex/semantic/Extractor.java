/* file name  : Extractor.java
 * authors    : Nacho Barrientos Arias <chipi@criptonita.com>
 * created    : 
 * copyright  : GPL
 *
 * modifications:
 *
 */
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

/** 
 * A handler for all sematic issues 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class Extractor
{
	
	/** 
	 * Creates an Extractor using the default grammar file
   * (lib/grammars/englishPCFG.ser.gz)
	 */
	public Extractor()
	{
		lp = null;
		lastParsedSentence = null;
		parse = null;
		grammarName = "lib/grammars/englishPCFG.ser.gz";
	}

	/** 
	 * Creates an Extractor using the specified grammar file 
	 * 
	 * @param grammar Path to a grammar file 
	 */
	public Extractor(String grammar)
	{
		lp = null;
		lastParsedSentence = null;
		parse = null;
		grammarName = grammar;
	}

	/** 
	 * Initializes the parser 
	 */
	public void load()
	{
		lp = new LexicalizedParser(grammarName); // TODO: Check now if lp is null
	}

	/** 
	 * Checks if the Extrator is ready 
	 * 
	 * @return True if it's ready, false otherwise 
	 */
	public boolean isLoaded()
	{
		if(lp == null)
			return false;
		else
			return true; 
	}

  /** 
   * Stores into an internal tree the parse results for 
   * a sentence
   * @param sentence The sentence to parse 
   */
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

	/** 
	 * Processes the dependencies among words for a sentence 
	 * 
	 * @param sentence The sentence to process 
	 * @return The result of this process
	 */
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
	
	/** 
	 * Gets the grammatical property of each word in the sentence 
	 * 
	 * @param sentence The sentence to process
	 * @return The result of this process 
	 */
	public ArrayList<TaggedWord> getProperties(List sentence)
	throws Exception
	{
		letsGetParseReady(sentence);

	  List sent = parse.taggedYield();
	
		return (ArrayList<TaggedWord>)sent;
	}

	/** 
	 * A reference to the parser 
	 */
	private LexicalizedParser lp;

	/** 
	 * A tree to store whole parse 
	 */
	private Tree parse;

	/** 
	 * The path to the grammar file. 
	 */
	private String grammarName;

	/** 
	 * A reference to last parsed sentence, used as cache. 
	 */
	private List lastParsedSentence;

}
