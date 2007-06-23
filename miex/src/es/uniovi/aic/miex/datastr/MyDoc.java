package es.uniovi.aic.miex.datastr;

import java.util.ArrayList;

/** 
 * Representative object of a single document 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class MyDoc
{

	/** 
	 * Creates a DOCTEST document 
	 */
	public MyDoc()
	{
		isTrain = false;
	}
	
	/** 
	 * Creates a document
	 * 
	 * @param isTrainRHS true if the document is a DOCTRAIN
	 */
	public MyDoc(boolean isTrainRHS)
	{
		isTrain = isTrainRHS;
	}

	/** 
	 * Attaches a bunch of categories to the document 
	 * 
	 * @param rhs 
	 */
	public void addCategories(MyCategories rhs)
	{
		categories = rhs;
	}

	/** 
	 * 
	 * 
	 * @param rhs The string to set the body to
	 */
	public void setBody(String rhs)
	{
		body = rhs;
	}

  /** 
   * 
   * 
   * @param rhs The string to set the title to
   */
  public void setTitle(String rhs)
  {
    title = rhs;
  }

	/** 
	 * Changes the document nature to DOCTRAIN 
	 */
	public void setIsTrain()
	{
		isTrain = true;
	}

	/** 
	 * Observer 
	 * 
	 * @return The body of the document
	 */
	public String getBody()
	{
		return body;
	}

  /** 
   * Observer 
   * 
   * @return The title of the document
   */
  public String getTitle()
  {
    return title;
  }

  /** 
   * Observer 
   * 
   * @return The categories of the document
   */
  public MyCategories getCategories()
  {
    return categories;
  }
	
	/** 
	 * Observer 
	 * 
	 * @return true if it is a DOCTRAIN document
	 */
	public boolean isTrain()
	{
		return isTrain;
	}

	public String toString()
	{
  	String newline = System.getProperty( "line.separator" );

		StringBuffer buf = new StringBuffer();
       
		buf.append( "--- Title ---" ).append( newline );
		buf.append(title).append(newline);

    buf.append( "--- Body ---" ).append( newline );
    buf.append(body).append(newline);

		if(isTrain)
			buf.append( "Is a TRAIN document" ).append( newline );
		else
			buf.append( "Is a TEST document" ).append( newline );

		buf.append(categories).append(newline);
        
		return buf.toString();
	}

	// Members
	private MyCategories categories;
	private String body;
	private String title;

	private boolean isTrain;

}
