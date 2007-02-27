/*
 *	MIEX - Metadata and Information Extractor from small XML documents.
 *	Copyright (C) 2007 - Nacho Barrientos Arias
 *
 *	This program is free software; you can redistribute it and/or modify
 *	it under the terms of the GNU General Public License as published by
 *	the Free Software Foundation; either version 2 of the License.
 * 
 *	This program is distributed in the hope that it will be useful,
 *	but WITHOUT ANY WARRANTY; without even the implied warranty of
 *	MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *	GNU General Public License for more details.
 *
 *	You should have received a copy of the GNU General Public License along
 *	with this program; if not, write to the Free Software Foundation, Inc.,
 *	51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 */

package es.uniovi.aic.miex.run;

import java.io.*;
import java.lang.System;
import java.lang.Exception;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.xml.sax.SAXException;

import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.ling.HasWord;

import es.uniovi.aic.miex.input.XMLValidator;
import es.uniovi.aic.miex.input.FieldsParser;
import es.uniovi.aic.miex.input.CMDLineParser;

import es.uniovi.aic.miex.exceptions.*;

import es.uniovi.aic.miex.config.Config;

import es.uniovi.aic.miex.datastr.*;

public class Miex 
{
    public static void main(String[] args) throws SAXException, IOException, Exception
		{

				/* STEP 1: Parsing CMDLine arguments */

				CMDLineParser theCMDParser = new CMDLineParser(args);

				if(!theCMDParser.parse())
					System.exit(-1);

				System.out.println("Cmdline syntax is OK, continuing...\n");

				/* STEP 2: Reading configuration file */

				String confFileName = theCMDParser.getConfigFileURI();
				Config config = new Config();
			
				try
				{
					config.parseOptionsFromFile(confFileName);
					System.out.println("Config file is OK, continuing...\n");
				}
				catch(IOException e)
				{
					System.err.println(e.toString());
					System.exit(-1);
				}
				catch(MissingFieldInConfigFileException e)
        {
          System.err.println(e.toString());
          System.exit(-1);
        }

				/* STEP 3: Validating input XML files (if required) */

				if(!theCMDParser.getNoValidateFlag())
				{
					if(!validateXMLInputFiles(theCMDParser.getFiles(), config))
						System.exit(-1);

					System.out.println("Input file's XML syntax is sane, parsing files...\n");
				}
				else
					System.out.println("Warning: continuing without validate XML input...\n");

				/* STEP 4: Getting metadata from input files */

				processFiles(theCMDParser.getFiles(),config, theCMDParser);				
				
    }

	/*
   * Begin of a set of auxiliar methods called from main
	 */

	private static boolean validateXMLInputFiles(String[] files, Config config) throws SAXException, IOException
	{
		for(int j=0; j < files.length; j++)
		{
			File theFile = new File(files[j]);

			if(!theFile.canRead())
			{
				System.err.println("Input file " + files[j] + " is not readable or not exists, terminating.");
				return false;
			}

			XMLValidator validator = new XMLValidator(theFile,config.getFieldValue("XMLschemaURI"));

			if(!(validator.validate()))
			{
				System.err.println("Input file's XML " + files[j] + " is not well-formed.");
				return false;
			 }
		}

		return true;
	}

	private static void processFiles(String[] files, Config config, CMDLineParser theCMDParser)
	{
		for(int j=0; j < files.length; j++)
		{

			System.out.println("Parsing file " + files[j] + "\n");

			File theFile = new File(files[j]);

			FieldsParser iParser = new FieldsParser(theFile);

			MyCollection collection = iParser.run();

			for(Iterator it = collection.getDocsIterator(); it.hasNext(); )
				// it.next() is a MyDoc
			{
				MyDoc doc = (MyDoc)it.next();

				// Splitting body in sentences
				List<List<? extends HasWord>> sentences = chopSentences(doc.getBody());

				if(theCMDParser.getDumpFlag())
				{
					// Creating data files
					injectDataToFiles(doc.getCategories().toArray(), sentences, theCMDParser.getDumpDir());
				}

			}

		}

	}

	// Picks up a chunk of text and splits it in sentences.
	private static List<List<? extends HasWord>> chopSentences(String text)
	{
		DocumentPreprocessor documentPreprocessor = new DocumentPreprocessor();

		List<List<? extends HasWord>> sentences = null;

		StringReader theReader = new StringReader(text);

		sentences = documentPreprocessor.getSentencesFromText(theReader, null, null, -1);

		return sentences;	
	}

	// For each category injects document's sentences to category.txt
	private static void injectDataToFiles(ArrayList<String> categories, 
																				List<List<? extends HasWord>> sentences,
																				String destination)
	{
			String newline = System.getProperty( "line.separator" );
	
			for(int i=0; i < categories.size(); i++)
			{
				try
				{
					String catName = categories.get(i).toString().replace(" ","");

					System.out.println("Creating file " + destination + catName + "...");

					FileWriter fw = new FileWriter(destination + catName + ".txt",true);

					System.out.println("Injecting the sentences...");

					for (List sentence : sentences)
					{
						fw.write(sentence.toString());
						fw.write(newline);
					}

					fw.close();
				}
				catch(IOException e)
				{
          System.err.println(e.toString());
          System.exit(-1);
				}
	
			}
	}

}
