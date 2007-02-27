package es.uniovi.aic.miex.input;

import com.martiansoftware.jsap.*;

public class CMDLineParser
{

public CMDLineParser(String[] arguments)
{
	args = arguments;
}

public boolean parse() throws Exception
  {
		JSAP jsap = new JSAP();

		/*
		 * Cmdline format:
     * [-v|--no-validate] -c configfile.conf file1.xml file2.xml ... filen.xml
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
		 * -dump-dir foodir
		 */

    FlaggedOption opt2 = new FlaggedOption("dumpDestDir")
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault(".")
                                .setRequired(false)
                                .setShortFlag('x')
                                .setAllowMultipleDeclarations(false)
                                .setLongFlag("dump-dir")
                                .setUsageName("fooDir");

    opt2.setHelp("Directory where dump files will be saved.");
    jsap.registerParameter(opt2);


		UnflaggedOption opt3 = new UnflaggedOption("files")
                                .setStringParser(JSAP.STRING_PARSER)
                                .setDefault(JSAP.NO_DEFAULT)
                                .setRequired(true)
                                .setGreedy(true);
        
		opt3.setHelp("URIs to files(s) to parse.");
		jsap.registerParameter(opt3);

		/*
  	 * Flags:
     */

		Switch sw1 = new Switch("noValidate")
												.setShortFlag('n')
                        .setLongFlag("no-validate");
    
		sw1.setHelp("Do not validate input XML files (using XML Schema).");    
		jsap.registerParameter(sw1);

    Switch sw2 = new Switch("dump")
                        .setShortFlag('d')
                        .setLongFlag("dump");

    sw2.setHelp("Dump sentences to category files.");
    jsap.registerParameter(sw2);
		
		config = jsap.parse(args);

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

	public String getDumpDir()
	{
		return config.getString("dumpDestDir");
	}	

	public Boolean getNoValidateFlag()
	{
		return config.getBoolean("noValidate");
	}

	public Boolean getDumpFlag()
	{
		return config.getBoolean("dump");
	}

	private String[] args;
  private JSAPResult config;
}
