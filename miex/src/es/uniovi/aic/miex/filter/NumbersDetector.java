/* file name  : NumbersDetector.java
 * authors    : Nacho Barrientos Arias <chipi@criptonita.com>
 * created    : 
 * copyright  : GPL
 *
 * modifications:
 *
 */
package es.uniovi.aic.miex.filter;

import java.util.HashSet;

import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.ling.MapLabel;

import es.uniovi.aic.miex.datastr.ExtendedTaggedWord;

// Package class

/** 
 * Removes numbers from sentences 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
class NumbersDetector 
{
  public NumbersDetector()
  {
    regexp = "-?[0-9]+(,[0-9]+)?"; // (-)Natural(,Natural)
  } 

  /** 
   * Filter for typed dependencies among words. 
   * 
   * @param dep
   * @return True if a dependency is clean, false otherwise (word 'a' or word 'b' is a number)
   */
  public boolean isCleanDep(TypedDependency dep)
  {
    MapLabel governorLabel = (MapLabel)dep.gov().label();
    MapLabel depLabel = (MapLabel)dep.dep().label();

    String governorWord = governorLabel.toString("value");
    String depWord = depLabel.toString("value");

    return !(governorWord.matches(regexp) || depWord.matches(regexp));
  }

  /** 
   * Filter for tagged words 
   * 
   * @param wordAndProp 
   * @return True if the word is not a number, false otherwise
   */
  public boolean isCleanProp(ExtendedTaggedWord wordAndProp)
  {
    return !(wordAndProp.word().matches(regexp));
  }

  private String regexp;

} //class
