package es.uniovi.aic.miex.input;

import es.uniovi.aic.miex.datastr.*;

import org.xml.sax.*;

import org.xml.sax.helpers.*;

import java.util.Stack;


/** 
 * Implements XML parser' behaviour 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
class SAXCollectionUnmarshaller
extends DefaultHandler
{
  
  public SAXCollectionUnmarshaller()
  {
    stack = new Stack<Object>();
    textPlease = false;
  }

  public MyCollection getCollection()
  {
    return collection;
  }

  public void setDocumentLocator(Locator rhs)
  { 
    locator = rhs; 
  }

  public void startElement(String uri, String localName, String qName, Attributes attribs)
  {
    textPlease = false;

    // Complex members
    if(localName.equals("COLLECTION"))
    {
      stack.push(new MyCollection());
    }
    else if(localName.equals("DOCTRAIN"))
    {
      stack.push(new MyDoc(true));
    }
    else if(localName.equals("DOCTEST"))
    {
      stack.push(new MyDoc(false));
    }
    else if(localName.equals("TOPIC"))
    {
      stack.push(new MyCategories());
    }
    // Single members
    else if(localName.equals("TITLE") || localName.equals("BODY") || localName.equals("D"))
    {
      stack.push( new StringBuffer() );
      textPlease = true;
    }
    else
    {
      // Unexpected field
    }
      
  }

  public void endElement(String uri, String localName, String qName)
  {
    textPlease = false;

    Object tmp = stack.pop();

    // Complex items
    if(localName.equals("COLLECTION"))
    {
      collection = (MyCollection)tmp;
    }
    else if(localName.equals("DOCTRAIN") || localName.equals("DOCTEST"))
    {
      ((MyCollection)stack.peek()).addDoc((MyDoc)tmp);
    }
    else if(localName.equals("TOPIC"))
    {
      ((MyDoc)stack.peek()).addCategories((MyCategories)tmp);
    }
    // Single items
    else if(localName.equals("TITLE"))
    {
      ((MyDoc)stack.peek()).setTitle(tmp.toString());
    }
    else if(localName.equals("BODY"))
    {
      ((MyDoc)stack.peek()).setBody(tmp.toString());
    }
    else if(localName.equals("D"))
    {
      ((MyCategories)stack.peek()).add(tmp.toString());
    }
    else
    {
      stack.push(tmp);
    }

  }

  public void characters(char[] data, int start, int length)
  {
    if(textPlease)
    {
      ((StringBuffer)stack.peek()).append(data, start, length);
    }
    else
    {
        // Impossible, XML files were validated before -> EXCEPTION?
    }
  }

  /* DEPRECATED
  private String resolveAttrib(String uri, String localName, Attributes attribs, String defaultValue)
  {
    String tmp = attribs.getValue(uri, localName);
    if(tmp != null)
      return tmp;
    else
      return defaultValue;
  }
  */

  private Stack<Object> stack;
  private boolean textPlease;
  
  private Locator locator;

  private MyCollection collection;

}
