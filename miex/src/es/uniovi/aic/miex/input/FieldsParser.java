package es.uniovi.aic.miex.input;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import java.lang.Exception;

public class FieldsParser
{

	public FieldsParser(File file)
   {
		fileHandler = file;
		categories = new ArrayList<String>();
		parsingTargets = new HashMap<String,String>();
   }

	public void run(String catTag, String[] usefulTags)
	{
    try
    {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

		// Previosly checked by main() and XMLValidator class.
		factory.setValidating(false);

    DocumentBuilder builder = factory.newDocumentBuilder();
    Document tree = builder.parse(fileHandler);

		expectedFields = usefulTags.length;

    getFieldsFromDOMTree(tree,catTag,usefulTags);
    }
    catch(Exception e)
    {
      e.printStackTrace();
    }

	}

	private void getFieldsFromDOMTree(Node node, String catTag, String[] usefulTags)
	{

		if(node != null)
		{

			// catTag -> #text -> cat_name
      if(node.getNodeName().equals(catTag))
        categories.add(node.getChildNodes().item(0).getNodeValue());

			// usefulTag -> #text -> field
			// Adding to HashMap sth like: [body,blah blah]
			for(int j=0; j < usefulTags.length; j++)
				if(node.getNodeName().equals(usefulTags[j]))
				parsingTargets.put(usefulTags[j],node.getChildNodes().item(0).getNodeValue());

			// Iterate
      NodeList children = node.getChildNodes();
      for(int i=0; i < children.getLength(); i++)
      {
        Node child = children.item(i);
        getFieldsFromDOMTree(child,catTag,usefulTags);
      }

		}

	}

	public void coherenceChecks() throws Exception
	{
		if(categories.isEmpty())
			throw new Exception("Error: No categories were found, check your config file (CategoryTag). Exiting...");

		if(parsingTargets.size() < expectedFields)
			throw new Exception("Error: Some fields expecified in UsefulFields were not found. Exiting...");	
	}

	public HashMap getUsefulFields()
	{
		return parsingTargets;
	}

	public ArrayList getCategories()
	{
		return categories;
	}	

	private File fileHandler;
	private ArrayList<String> categories;
	private HashMap<String,String> parsingTargets;
	private int expectedFields;

}
