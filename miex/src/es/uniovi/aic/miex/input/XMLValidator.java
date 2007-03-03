package es.uniovi.aic.miex.input;

import java.io.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import javax.xml.XMLConstants;

import org.xml.sax.SAXException;

public class XMLValidator 
{
		public XMLValidator(File file, String SchemaURI)
		{
			fileHandler = file;
			XMLSchemaURI = new String(SchemaURI);
		}

    public boolean validate()
		{
        // 1. Lookup a factory for the W3C XML Schema language
        SchemaFactory factory =  SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        
        // 2. Compile the schema. 
        // Here the schema is loaded from a java.io.File, but you could use 
        // a java.net.URL or a javax.xml.transform.Source instead.
				File schemaLocation = new File(XMLSchemaURI);
				Schema schema = null;

				try
				{
        	schema = factory.newSchema(schemaLocation);
				}
				catch(SAXException e)
				{
					System.err.println(e.toString());
					return false;
				}
    
        // 3. Get a validator from the schema.
        Validator validator = schema.newValidator();

        // 4. Parse the document you want to check.
        Source source = new StreamSource(fileHandler);

        // 5. Check the document
        try 
				{
            validator.validate(source);
            return true;
        }
        catch (SAXException e) 
				{
						System.err.println(e.toString());
						return false;
        }
				catch (IOException e)
				{
						System.err.println(e.toString());
						return false;
				}
        
    }

		private File fileHandler;
		private String XMLSchemaURI;

}
