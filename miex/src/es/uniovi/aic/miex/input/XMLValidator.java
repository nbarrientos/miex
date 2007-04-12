package es.uniovi.aic.miex.input;

import java.io.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import javax.xml.XMLConstants;

public class XMLValidator 
{
		public XMLValidator(String SchemaURI)
		{
			XMLSchemaURI = new String(SchemaURI);
		}

    public boolean validate(File targetFileURI)
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
				catch(Exception e)
				{
					System.err.println(e.toString());
					return false;
				}
    
        // 3. Get a validator from the schema.
        Validator validator = schema.newValidator();

        // 4. Parse the document you want to check.
        Source source = new StreamSource(targetFileURI);

        // 5. Check the document
        try 
				{
            validator.validate(source);
            return true;
        }
        catch (Exception e) 
				{
						System.err.println(e.toString());
						return false;
        }
        
    }

		private String XMLSchemaURI;

}
