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
import edu.stanford.nlp.ling.Sentence;

import es.uniovi.aic.miex.datastr.ExtendedTaggedWord;

/** 
 * A handler for all sematic issues 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class Extractor
{
	
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
//		lp.setOptionFlags(new String[]{"-noRecoveryTagging"});
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
	 * Gets the grammatical properties for each word in the sentence 
	 * 
	 * @param sentence The sentence to process
	 * @return The result of this process 
	 */
	public ArrayList<ExtendedTaggedWord> getProperties(List sentence)
	throws Exception
	{
		letsGetParseReady(sentence);

	  List sent = myTaggedYield(parse);
	
		return (ArrayList<ExtendedTaggedWord>)sent;
	}

	/** 
	 * Recursively navigates the parse tree storing all the labels 
	 * 
	 * @param t The tree to parse 
	 * @param ty Information captured wrt the sentence until now
	 * @param phrase Phrase tags collected until now
	 * @return The tagged yield we were looking for 
	 */
	private List myTaggedYield(Tree t, Sentence ty, ArrayList<String> phrase)
	{
		Tree[] kids = t.children();

    if (kids.length == 1 && kids[0].isLeaf()) // t's root is a)
    {
      ExtendedTaggedWord stw = new ExtendedTaggedWord(kids[0].label().value(), t.label().value(), phrase);

      ty.add(stw);
    }
    else
    {
      for (int i = 0; i < kids.length; i++)
      {
        if(kids[i].isPhrasal()) // t's root is b)
        {
					ArrayList<String> newphrase = new ArrayList<String>(phrase);
					newphrase.add(kids[i].label().value());
					myTaggedYield(kids[i],ty,newphrase);
        }
				else // c) t's root is c)
					myTaggedYield(kids[i],ty,phrase);						
      }
    }
    return ty;
  }

  /*
	 * Example:
  
                       ROOT
                        |
     ---- b -->         S
                      /   \
     ---- c -->     NP     VP
	      				   / | \    \
     ---- a -->  DT NNP ...  ...
                 |   |
                The cup

   */

	/** 
	 * Gets a special TaggedYield with phrase and clause level tags in
	 * addiction to the word level tags 
	 * 
	 * @param t A tree representing the parse of a sentence 
	 * @return The requested information 
	 */
	private Sentence myTaggedYield(Tree t)
	{
		return (Sentence) myTaggedYield(t,new Sentence(),new ArrayList<String>());
	}

	private LexicalizedParser lp;

	private Tree parse;

	private String grammarName;

	private List lastParsedSentence;

}
