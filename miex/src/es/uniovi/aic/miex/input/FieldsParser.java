package es.uniovi.aic.miex.input;

import es.uniovi.aic.miex.input.SAXCollectionUnmarshaller;

import es.uniovi.aic.miex.datastr.MyCollection;

//import javax.xml.parsers.*;
//import org.w3c.dom.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.io.*;

import java.lang.Exception;

public class FieldsParser
{

	public FieldsParser(File file)
   {
		fileHandler = file;	
   }

	public MyCollection run()
	{
		SAXCollectionUnmarshaller saxUms = new SAXCollectionUnmarshaller();
		
    try
    {
			InputSource src = new InputSource(new FileInputStream(fileHandler));

			XMLReader rdr = XMLReaderFactory.createXMLReader();
			rdr.setContentHandler(saxUms);

			rdr.parse(src);

		}
		catch(Exception e)
		{
			System.err.println("Parsing ERROR: " + e);
		}

		// Returns a MyCollection instance that contains all the parsed file.
		return saxUms.getCollection();
	}
						
	private File fileHandler;
}
