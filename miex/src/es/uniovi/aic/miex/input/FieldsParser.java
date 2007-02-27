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
		collection = new MyCollection();
   }

	public void run()
	{
    try
    {
			InputSource src = new InputSource(new FileInputStream(fileHandler));

			SAXCollectionUnmarshaller saxUms = new SAXCollectionUnmarshaller();

//			XMLReader rdr = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
			XMLReader rdr = XMLReaderFactory.createXMLReader();
			rdr.setContentHandler(saxUms);

			rdr.parse(src);

			collection = saxUms.getCollection();

			// TEST
			System.out.println(collection.toString());
		}
		catch(Exception e)
		{
			System.err.println("Exception: " + e);
		}
	}
						
	private File fileHandler;
	private MyCollection collection;
}
