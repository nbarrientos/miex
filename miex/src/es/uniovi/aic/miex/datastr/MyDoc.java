package es.uniovi.aic.miex.datastr;

import java.util.ArrayList;

public class MyDoc
{

	public MyDoc()
	{
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
	
	public String toString()
	{
  	String newline = System.getProperty( "line.separator" );

		StringBuffer buf = new StringBuffer();
       
		buf.append( "--- Title ---" ).append( newline );
		buf.append(title).append(newline);

    buf.append( "--- Body ---" ).append( newline );
    buf.append(body).append(newline);

		buf.append(categories).append(newline);
        
		return buf.toString();
	}

	// Members
	private MyCategories categories;
	private String body;
	private String title;

}
