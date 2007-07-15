package es.uniovi.aic.miex.datastr;

import java.util.ArrayList;
import java.util.Iterator;

/** 
 * Representative object of a whole collection 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class MyCollection
{

  /** 
   * Creates and empty collection 
   */
  public MyCollection()
  {
    docs = new ArrayList<MyDoc>();
  }

  /** 
   * Adds a document to the collection 
   * 
   * @param rhs The document to add
   */
  public void addDoc(MyDoc rhs)
  {
    docs.add(rhs);
  }

  /** 
   *  
   * 
   * @return An iterator over the list containing the docs of the
   * collection
   */
  public Iterator getDocsIterator()
  {
    return docs.iterator();
  }

  public String toString()
  {
    String newline = System.getProperty( "line.separator" );

    StringBuffer buf = new StringBuffer();
       
    buf.append( "--- Documents ---" ).append(newline);
    for(int i=0; i < docs.size(); i++)
    {
      buf.append(docs.get(i)).append(newline);
    }
        
    return buf.toString();
  }

  public int size()
  {
    return docs.size();
  }

  // Members
  private ArrayList<MyDoc> docs;

}
