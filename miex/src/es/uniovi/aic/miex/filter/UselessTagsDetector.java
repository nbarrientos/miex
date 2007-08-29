package es.uniovi.aic.miex.filter;

import java.util.HashSet;

import es.uniovi.aic.miex.datastr.ExtendedTaggedWord;

/** 
 * Filters useless tags 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
class UselessTagsDetector 
{
  
  /** 
   * Initializes an useless tag detector 
   */
  public UselessTagsDetector()
  {
    
    String[] uselessTagsLocal = new String[]{"``", "''", "-LRB-", "-RRB-",
    "-LCB-", "-RCB-", "--", "...", "*CR*", ",", ":", "."};
  
    uselessTags = new HashSet<String>();

    for(String word: uselessTagsLocal)
      uselessTags.add(word);

  } // constructor

  private boolean is(String tag)
  {
    return uselessTags.contains(tag);
  }

  /** 
   * The filter itself 
   * 
   * @param wordAndProp an object containing the tag to check
   * @return true if the tag is useful
   */
  public boolean isCleanProp(ExtendedTaggedWord wordAndProp)
  {
    return !(is(wordAndProp.tag()));
  }

  private HashSet<String> uselessTags;

} //class

