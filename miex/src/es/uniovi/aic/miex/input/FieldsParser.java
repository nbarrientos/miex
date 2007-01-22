package es.uniovi.aic.miex.input;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import java.util.ArrayList;

public class FieldsParser
{

	public FieldsParser(File file)
   {
		fileHandler = file;
		categories = new ArrayList<String>();
		body = new String();
   }

	public void run()
	{
    try
    {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// Previosly checked by main() and XMLValidator class.
		factory.setValidating(false);

    DocumentBuilder builder = factory.newDocumentBuilder();
    Document tree = builder.parse(fileHandler);

    getFieldsFromDOMTree(tree);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

	}

	private void getFieldsFromDOMTree(Node node)
	{

		if(node != null)
		{
			// d -> #text -> cat_name
      if(node.getNodeName() == "d")
        categories.add(node.getChildNodes().item(0).getNodeValue());

      if(node.getNodeName() == "body")
				body = node.getChildNodes().item(0).getNodeValue();

			// Iterate
      NodeList children = node.getChildNodes();
      for(int i=0; i < children.getLength(); i++)
      {
        Node child = children.item(i);
        getFieldsFromDOMTree(child);
      }

		}

	}

	public String getBody()
	{
		return body;
	}

	public ArrayList getCategories()
	{
		return categories;
	}	

	private File fileHandler;
	private ArrayList<String> categories;
	private String body;

}
