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
			// 1. Lookup a factory for the W3C XML Schema language
			SchemaFactory factory =  SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        
			// 2. Compile the schema. 
			File schemaLocation = new File(SchemaURI);
			Schema schema = null;

			try
			{
				schema = factory.newSchema(schemaLocation);
			}
			catch(Exception e)
			{
				System.err.println(e.toString());
				System.exit(-1);
			}
    
      // 3. Get a validator from the schema.
			validator = schema.newValidator();
		}

    public boolean validate(File targetFileURI)
    {
        // Parse the document you want to check.
        Source source = new StreamSource(targetFileURI);

        // Check the document
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

		private Validator validator;
}
