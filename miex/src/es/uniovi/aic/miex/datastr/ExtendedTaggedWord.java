package es.uniovi.aic.miex.datastr;

import edu.stanford.nlp.ling.TaggedWord;

public class ExtendedTaggedWord
extends TaggedWord
{

public ExtendedTaggedWord(String word, String tag, String _zone)
{
  super(word,tag);
  zone = new String(_zone);
}

public String zone()
{
  return zone;
}

public String toString()
{
  String old = super.toString();

  old = old + "/" + zone;

  return old;
}

private String zone;

}
