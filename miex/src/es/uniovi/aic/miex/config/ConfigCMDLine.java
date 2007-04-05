/* file name  : ConfigCMDLine.java
 * authors    : Nacho Barrientos Arias <chipi@criptonita.com>
 * created    : 
 * copyright  : GPL
 *
 * modifications:
 *
 */
package es.uniovi.aic.miex.config;

import com.martiansoftware.jsap.*;

/** 
 * Command line treatment tool 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class ConfigCMDLine
{

	/** 
	 * Builds a ConfigCMDLine object and runs the parser over
	 * the list of arguments. 
	 * 
	 * @param arguments The list of arguments to parse
	 */
	public ConfigCMDLine(String[] arguments)
	{
		try
		{
			if(!(this.parse(arguments)))
			 System.exit(-1);
		}
		catch(JSAPException e)
		{
			e.printStackTrace();
			System.exit(-1);
		}

	}

	/** 
	 * The parse itself 
	 * 
	 * @param arguments The line of arguments 
	 * @return False If any argument is missing or a weird argument is found 
	 * @throws JSAPException If something weird happens with in the parse process 
	 */
	private boolean parse(String[] arguments) throws JSAPException
  {
		JSAP jsap = new JSAP();

		/*
		 * Cmdline format:
     * -c configfile.conf file1.xml file2.xml ... filen.xml
		 */


		/*
		 * -config file.conf
		 */

		FlaggedOption opt1 = new FlaggedOption("configFileURI")
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault(JSAP.NO_DEFAULT) 
                                .setRequired(true) 
                                .setShortFlag('c') 
																.setAllowMultipleDeclarations(false)
																.setLongFlag("config")
																.setUsageName("confFile.conf");

		opt1.setHelp("URI to config file.");
		jsap.registerParameter(opt1);

		/*
		 * file1, file2, ... , filen
		 */

		UnflaggedOption opt3 = new UnflaggedOption("files")
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault(JSAP.NO_DEFAULT)
                                .setRequired(true)
                                .setGreedy(true);
        
		opt3.setHelp("URIs to collection files(s) to parse.");
		jsap.registerParameter(opt3);

		config = jsap.parse(arguments);

		if (!config.success()) 
		{
			for (java.util.Iterator errs = config.getErrorMessageIterator(); errs.hasNext(); ) 
			{
				System.err.println("Error: " + errs.next());
			}

			System.err.println();
			System.err.println("Usage: ./run");
			System.err.println("                " + jsap.getUsage());
			System.err.println();
			System.err.println(jsap.getHelp());
			return false;
		}
		else
			return true;

	}

	/** 
	 * Observer method 
	 * 
	 * @return Collections to parse (input files)
	 */
	public String[] getFiles()
	{
		return config.getStringArray("files");
	}

	/** 
	 * Observer method 
	 * 
	 * @return Path to the configuration file
	 */
	public String getConfigFileURI()
	{
		return config.getString("configFileURI");
	}

  private JSAPResult config;
}
