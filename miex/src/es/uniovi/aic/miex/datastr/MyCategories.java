package es.uniovi.aic.miex.datastr;

import java.util.ArrayList;

public class MyCategories
{

	public MyCategories()
	{
		categories = new ArrayList<String>();
	}

	public void add(String rhs)
	{
		categories.add(rhs);
	}

	public ArrayList<String> toArray()
	{
		return categories;
	}

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
