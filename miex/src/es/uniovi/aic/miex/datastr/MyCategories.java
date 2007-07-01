/* file name  : miex/datastr/MyCategories.java
 * authors    : Nacho Barrientos Arias <chipi@criptonita.com>
 * created    : 
 * copyright  : GPL
 *
 * modifications:
 *
 */
package es.uniovi.aic.miex.datastr;

import java.util.ArrayList;

/** 
 * A model to represent a set of categories from a document 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class MyCategories
{

  /** 
   * Creates a new MyCategories object 
   */
  public MyCategories()
  {
    categories = new ArrayList<String>();
  }

  /** 
   * Adds a new category to the set 
   * 
   * @param rhs The new category to add 
   */
  public void add(String rhs)
  {
    categories.add(rhs);
  }

  /** 
   * Returns the set as an array 
   * 
   * @return The array
   */
  public ArrayList<String> toArray()
  {
    return categories;
  }

  /** 
   * Transforms the object content to a printable format 
   * 
   * @return A string with an human-friendly format
   */
  public String toString()
  {
    String newline = System.getProperty( "line.separator" );

    StringBuffer buf = new StringBuffer();
       
    buf.append( "--- Categories ---" ).append(newline);
    for(int i=0; i < categories.size(); i++)
    {
      buf.append( categories.get(i) ).append(newline);
    }
    
    return buf.toString();
  }

  // Members
  
  private ArrayList<String> categories;
}
