package es.uniovi.aic.miex.config;

import java.util.Properties;
import java.io.*;
import java.lang.Exception;

public class ConfigFile
{

	public ConfigFile(String fileName)
	{
		properties = new Properties();

		try
		{
			properties.load(new FileInputStream(fileName));
			this.checkFile();
		}
		catch(Exception e)
		{
			System.err.println("ERROR: " + e.getMessage());
			System.exit(-1);
		}

	}

	private void checkFile() throws Exception
	{
		for(int i=0; i < configFileFields.length; i++)
			if("".equals(properties.getProperty(configFileFields[i]))
					|| properties.getProperty(configFileFields[i]) == null)
				throw new Exception("Missing field " + configFileFields[i] + " in config file.");

		for(int i=0; i < configFileBooleanFields.length; i++)
			if( !(properties.getProperty(configFileBooleanFields[i]).toLowerCase().equals("yes"))
					&&
					!(properties.getProperty(configFileBooleanFields[i]).toLowerCase().equals("no")))
			throw new Exception("Boolean keys must only contain \"Yes\" or \"No\" values");

	}

	public String getStringSetting(String stt)
	{
		return properties.getProperty(stt);
	}

	public boolean getBooleanSetting(String stt)
	{
		if(properties.getProperty(stt).toLowerCase().equals("yes"))
			return true;
		else
			return false;
	}

	private Properties properties;

	private static String[] configFileFields, configFileBooleanFields;

  static
  {
    configFileFields = new String[]{"XMLschemaURI",
                                    "BDHostname",
                                    "BDUser",
                                    "BDPassword",
                                    "BDName",
                                    "CreateDB",
                                    "DumpDir",
                                    "Validate",
                                    "Dump" };

		configFileBooleanFields = new String[]{ "Validate", "CreateDB", "Dump" };

  }

}
