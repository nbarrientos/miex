package es.uniovi.aic.miex.input;

import es.uniovi.aic.miex.input.SAXCollectionUnmarshaller;

import es.uniovi.aic.miex.datastr.MyCollection;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import java.io.*;

import java.lang.Exception;

/** 
 * Discovers the information stored in a XML collection 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class FieldsParser
{
  
  /** 
   * Runs the parser over a file 
   * 
   * @param targetFileURI The file to parse
   * @return a MyCollection instace with the extracted information
   */
  public MyCollection run(File targetFileURI)
  {
    SAXCollectionUnmarshaller saxUms = new SAXCollectionUnmarshaller();
    
    try
    {
      InputSource src = new InputSource(new FileInputStream(targetFileURI));

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
}
