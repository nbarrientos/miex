package es.uniovi.aic.miex.input;

import java.io.*;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import javax.xml.XMLConstants;

/** 
 * Determines whether a XML file matches a XML Schema 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class XMLValidator 
{
    
    /** 
     * Prepares a validator to check files using a schema 
     * 
     * @param SchemaURI The URI to a file containing the schema
     */
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

    /** 
     * Runs the validator over an input file 
     * 
     * @param targetFileURI URI to a input file (usually XML-formatted)
     * @return true if the file matches the schema, false otherwise 
     */
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
            return false;
        }
        
    }

    private Validator validator;
}
