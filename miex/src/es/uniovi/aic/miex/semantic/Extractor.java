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
 * A handler for all the sematic issues 
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

  /** 
   * Gets the grammatical properties for each word in the sentence 
   * 
   * @param sentence The sentence to analyse
   * @return The result of this process 
   */
  public ArrayList<ExtendedTaggedWord> getProperties(List sentence)
  throws Exception
  {
    // WARNING: Refreshing the parse with the current sentence
    letsGetParseReady(sentence);

    return myTaggedYield();
  }

  /**
   * Gets a special TaggedYield with phrase and clause level tags in
   * addition to the word level tags from the CURRENTLY stored parse
   * tree
   *
   * @param t The target object to store the captured information
   * @return The parsed information from THE WHOLE parse tree
   */
  private ArrayList<ExtendedTaggedWord> myTaggedYield()
  {
    ArrayList<ExtendedTaggedWord> blankTarget = new ArrayList<ExtendedTaggedWord>();

    myTaggedYield(parse,blankTarget,new ArrayList<String>());

    return blankTarget;
  }

  /** 
   * Recursively navigates through the parse tree storing all the labels 
   * 
   * @param t The tree to parse 
   * @param ty Information captured wrt the sentence until now
   * @param phrase Phrase tags collected until now
   */
  private void myTaggedYield(Tree t, ArrayList<ExtendedTaggedWord> ty, ArrayList<String> phrase)
  {

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
   *
   */

    Tree[] kids = t.children();

    // We've got a new word (t's root is something like a)), all the recursive calls leads here
    if (kids.length == 1 && kids[0].isLeaf()) 
    {
      ExtendedTaggedWord stw = new ExtendedTaggedWord(kids[0].label().value(), t.label().value(), phrase);
      ty.add(stw);
    }
    else // Recursively navigating through all the non-final nodes
    {
      for (int i=0; i < kids.length; i++)
      {
        if(kids[i].isPhrasal()) // t's root is something like b)
        {
          ArrayList<String> newphrase = new ArrayList<String>(phrase);
          newphrase.add(kids[i].label().value()); // new phrase tag found in the path
          myTaggedYield(kids[i],ty,newphrase);
        }
        else // t's root is something like c)
          myTaggedYield(kids[i],ty,phrase);           
      }
    }
  }

  private LexicalizedParser lp;

  private Tree parse;

  private String grammarName;

  private List lastParsedSentence;
}
