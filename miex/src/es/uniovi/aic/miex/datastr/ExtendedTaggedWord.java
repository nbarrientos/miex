package es.uniovi.aic.miex.datastr;

import java.util.ArrayList;

import edu.stanford.nlp.ling.TaggedWord;

public class ExtendedTaggedWord
extends TaggedWord
{

public ExtendedTaggedWord(String word, String tag, ArrayList<String> _phrase)
{
  super(word,tag);
	phrase = _phrase;
}

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
