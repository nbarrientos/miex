package es.uniovi.aic.miex.config;

import com.martiansoftware.jsap.*;

public class ConfigCMDLine
{

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

	public String[] getFiles()
	{
		return config.getStringArray("files");
	}

	public String getConfigFileURI()
	{
		return config.getString("configFileURI");
	}

  private JSAPResult config;
}
