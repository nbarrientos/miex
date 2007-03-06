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

/* JDK */
import java.io.*;
import java.lang.System;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

/* Exceptions */
import com.martiansoftware.jsap.JSAPException;

/* Stanford */
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.trees.TypedDependency;

/* Jakarta (Apache) */
import org.apache.commons.collections.map.MultiValueMap;

/* Miex */
import es.uniovi.aic.miex.input.XMLValidator;
import es.uniovi.aic.miex.input.FieldsParser;
import es.uniovi.aic.miex.input.CMDLineParser;

import es.uniovi.aic.miex.config.Config;

import es.uniovi.aic.miex.datastr.*;
import es.uniovi.aic.miex.semantic.*;

public class Miex 
{
    public static void main(String[] args)
		{

				/* STEP 1: Parsing CMDLine arguments */

				CMDLineParser theCMDParser = new CMDLineParser(args);
				
				try
				{
				if(!theCMDParser.parse())
					System.exit(-1);
				}
				catch(JSAPException e)
				{
					System.err.println(e.toString());
					System.exit(-1);
				}

				System.out.println("Cmdline syntax is OK, continuing...\n");

				/* STEP 2: Reading configuration file */

				String confFileName = theCMDParser.getConfigFileURI();
				Config config = new Config();
			
				try
				{
					config.parseOptionsFromFile(confFileName);
					System.out.println("Config file is OK, continuing...\n");
				}
//				catch(IOException e)
//				{
//					System.err.println(e.toString());
//					System.exit(-1);
//				}
				catch(Exception e)
        {
          System.err.println(e.toString());
          System.exit(-1);
        }

				/* STEP 3: Validating input XML files (if required) */

				if(!theCMDParser.getNoValidateFlag())
				{
					if(!validateXMLInputFiles(theCMDParser.getFiles(), config))
					{
						System.err.println("ERROR: Internal error validating XML input files");
						System.exit(-1);
					}

					System.out.println("Input file's XML syntax is sane (matches XML Schema), parsing files...\n");
				}
				else
					System.out.println("Warning: continuing without validate XML input...\n");

				/* STEP 4: Getting metadata from input files */

				processFiles(theCMDParser.getFiles(),config, theCMDParser);				
				
    }

	/*
   * Begin of a set of auxiliar methods called from main
	 */

	private static boolean validateXMLInputFiles(String[] files, Config config)
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
				System.err.println("Input file's XML " + files[j] + " does not match this Schema.");
				return false;
			 }
		}

		return true;
	}

	/* Before this function is called validation using Schema has been completed 
		 then we only need to process the files with the SP */

	private static void processFiles(String[] files, Config config, CMDLineParser theCMDParser)
	{
		Extractor ex = new Extractor();

		for(int j=0; j < files.length; j++)
		{

			System.out.println("Parsing file " + files[j] + "\n");

			File theFile = new File(files[j]);

			FieldsParser unMarshaller = new FieldsParser(theFile);

			MyCollection collection = unMarshaller.run();
	
			if(!ex.isLoaded()) ex.load();

			for(Iterator it = collection.getDocsIterator(); it.hasNext(); )
				// it.next() is a MyDoc
			{
				MyDoc doc = (MyDoc)it.next();

				// Splitting body in sentences
				List<List<? extends HasWord>> sentences = chopSentences(doc.getBody());

				if(theCMDParser.getDumpFlag())
				{
					// Creating data files
					System.out.println("\tCreating category files for document titled: " + doc.getTitle());
					injectDataToFiles(doc.getCategories().toArray(), sentences, theCMDParser.getDumpDir());
				}
					
				System.out.println("\n\tProcessing document titled: " + doc.getTitle().trim());		
				processDoc(sentences,ex);

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

					System.out.println("\tCreating file " + destination + catName + "...");

					FileWriter fw = new FileWriter(destination + catName + ".txt",true);

					System.out.println("\tInjecting the sentences...");

					for (List sentence : sentences)
					{
						fw.write(sentence.toString());
						fw.write(newline);
					}

					fw.close();

					System.out.println("\tDone");

				}
				catch(IOException e)
				{
          System.err.println(e.toString());
          System.exit(-1);
				}
	
			}
	}

	private static void processDoc(List<List<? extends HasWord>> sentences, Extractor ex)
	{
			int numSentences = 1;
		
			for (List sentence : sentences)
			{
				try
				{
					System.out.println("\t\tProcessing sentence #" + numSentences);

					System.out.print("\t\t\tDependencies... ");
					// TODO: SQL injection HERE.
					ArrayList<TypedDependency> deps = ex.getDependencies(sentence);
					System.out.print("Done\n");

					System.out.print("\t\t\tProperties... ");					
					MultiValueMap props = ex.getProperties(sentence);
					System.out.print("Done\n");

					numSentences++;
				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.err.println(e.toString());
					System.exit(-1);
				}
			}		

	}

}
