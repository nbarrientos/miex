package es.uniovi.aic.miex.datastr;

import java.util.ArrayList;

public class MyCollection
{

	public MyCollection()
	{
		docs = new ArrayList<MyDoc>();
	}

	public void addDoc(MyDoc rhs)
	{
		docs.add(rhs);
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

	// Members
	private ArrayList<MyDoc> docs;

}
