// -----------------------------------------------------------------------------
// JaxpXmlDomExample.java
// -----------------------------------------------------------------------------

/*
 * =============================================================================
 * Copyright (c) 1998-2005 Jeffrey M. Hunter. All rights reserved.
 * 
 * All source code and material located at the Internet address of
 * http://www.idevelopment.info is the copyright of Jeffrey M. Hunter, 2005 and
 * is protected under copyright laws of the United States. This source code may
 * not be hosted on any other site without my express, prior, written
 * permission. Application to host any of the material elsewhere can be made by
 * contacting me at jhunter@idevelopment.info.
 *
 * I have made every effort and taken great care in making sure that the source
 * code and other content included on my web site is technically accurate, but I
 * disclaim any and all responsibility for any loss, damage or destruction of
 * data or any other property which may arise from relying on it. I will in no
 * case be liable for any monetary damages arising from such loss, damage or
 * destruction.
 * 
 * As with any code, ensure to test this code in a development environment 
 * before attempting to run it in production.
 * =============================================================================
 */
 
// Core Java APIs
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

// JAXP
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

// DOM
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to build a DOM Tree from an example XML
 * file provided on the command line. This example uses Sun Microsystem's JAXP
 * API's.
 *
 * COMPILE:
 * --------------------------
 * javac JaxpXmlDomExample.java
 *
 * 
 * RUN PROGRAM:
 * --------------------------
 * java JaxpXmlDomExample DatabaseInventory.xml
 *
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
 
public class JaxpXmlDomExample {

    private static void printNode(Node node, String indent)  {

        switch (node.getNodeType()) {

            case Node.DOCUMENT_NODE:
                System.out.println("<xml version=\"1.0\">\n");
                // recurse on each child
                NodeList nodes = node.getChildNodes();
                if (nodes != null) {
                    for (int i=0; i<nodes.getLength(); i++) {
                        printNode(nodes.item(i), "");
                    }
                }
                break;
                
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();
                System.out.print(indent + "<" + name);
                NamedNodeMap attributes = node.getAttributes();
                for (int i=0; i<attributes.getLength(); i++) {
                    Node current = attributes.item(i);
                    System.out.print(
                        " " + current.getNodeName() +
                        "=\"" + current.getNodeValue() +
                        "\"");
                }
                System.out.print(">");
                
                // recurse on each child
                NodeList children = node.getChildNodes();
                if (children != null) {
                    for (int i=0; i<children.getLength(); i++) {
                        printNode(children.item(i), indent + "  ");
                    }
                }
                
                System.out.print("</" + name + ">");
                break;

            case Node.TEXT_NODE:
                System.out.print(node.getNodeValue());
                break;
        }

    }

    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println ("\nUsage: java JaxpXmlDomExample filename\n");
            System.exit (1);
        }

        // Create File object from incoming file
        File xmlFile = new File(args[0]);
 
        try {
        
            // Get Document Builder Factory
            DocumentBuilderFactory factory = 
                    DocumentBuilderFactory.newInstance();

            // Turn on validation, and turn off namespaces
            factory.setValidating(true);
            factory.setNamespaceAware(false);

            // Obtain a document builder object
            DocumentBuilder builder = factory.newDocumentBuilder();

            System.out.println();
            System.out.println("Passed in File         : " + args[0]);
            System.out.println("Object to Parse (File) : " + xmlFile);
            System.out.println("Parser Implementation  : " + builder.getClass());
            System.out.println();

            // Parse the document
            Document doc = builder.parse(xmlFile);

            // Print the document from the DOM tree and feed it an initial 
            // indentation of nothing
            printNode(doc, "");
            
            System.out.println("\n");

        } catch (ParserConfigurationException e) {
            System.out.println("The underlying parser does not support " +
                               "the requested features.");
            e.printStackTrace();
        } catch (FactoryConfigurationError e) {
            System.out.println("Error occurred obtaining Document Builder " +
                               "Factory.");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
