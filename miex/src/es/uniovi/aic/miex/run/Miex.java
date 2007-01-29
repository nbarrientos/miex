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

import org.xml.sax.SAXException;

import es.uniovi.aic.miex.input.XMLValidator;
import es.uniovi.aic.miex.input.FieldsParser;
import es.uniovi.aic.miex.input.CMDLineParser;

import es.uniovi.aic.miex.exceptions.*;

import es.uniovi.aic.miex.config.Config;

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

				processFiles(theCMDParser.getFiles(),config);				
				
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

	private static void processFiles(String[] files, Config config)
	{
		for(int j=0; j < files.length; j++)
		{

			System.out.println("Parsing file " + files[j] + "\n");

			File theFile = new File(files[j]);

			FieldsParser iParser = new FieldsParser(theFile);

			iParser.run(config.getFieldValue("CategoryTag"),config.getFieldValue("UsefulFields").split(" "));

			// Final checks after final parsing.
			// - User mistyped CategoryTag or UsefulFields in conf file (but XML file agrees the schema).
			// - XML wasn't validated with Schema (-n) and the expected fields are not found.
			try
			{
				iParser.coherenceChecks();
			}
				catch(Exception e)
			{
				System.err.println(e.getMessage());
				System.exit(-1);
			}

				System.out.println("Fields: " + iParser.getUsefulFields().toString());
				System.out.println("Categories: " + iParser.getCategories().toString() + "\n");
	}


	}

}
