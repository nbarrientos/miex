package es.uniovi.aic.miex.datastr;

import java.util.ArrayList;

public class MyDoc
{

	public MyDoc()
	{
		isTrain = false;
	}
	
	public MyDoc(boolean isTrainRHS)
	{
		isTrain = isTrainRHS;
	}

	public void addCategories(MyCategories rhs)
	{
		categories = rhs;
	}

	public void setBody(String rhs)
	{
		body = rhs;
	}

  public void setTitle(String rhs)
  {
    title = rhs;
  }

	public void setIsTrain()
	{
		isTrain = true;
	}

	public String getBody()
	{
		return body;
	}

  public String getTitle()
  {
    return title;
  }

  public MyCategories getCategories()
  {
    return categories;
  }
	
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
