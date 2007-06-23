package es.uniovi.aic.miex.datastr;

import java.util.ArrayList;

import edu.stanford.nlp.ling.TaggedWord;

/** 
 * Extended TaggedWord featuring phrase and clause level tags storage 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class ExtendedTaggedWord
extends TaggedWord
{

public ExtendedTaggedWord(String word, String tag, ArrayList<String> _phrase)
{
  super(word,tag);
	phrase = _phrase;
}

/** 
 * Observer method 
 * 
 * @return The list of clause and phrase tags
 */
public ArrayList<String> phrase()
{
  return phrase;
}

public String toString()
{
  String old = super.toString();

  old = old + "/" + phrase;

  return old;
}

private ArrayList<String> phrase;

}
