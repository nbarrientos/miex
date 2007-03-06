package es.uniovi.aic.miex.config;

import java.util.Properties;
import java.io.*;
import java.lang.Exception;

public class Config
{

	public Config()
	{
		properties = new Properties();
	}

	public void parseOptionsFromFile(String fileName) throws IOException, Exception
	{
		properties.load(new FileInputStream(fileName));
		this.checkFile();
	}

	private void checkFile() throws Exception
	{
		for(int i=0; i < configFileFields.length; i++)
			if("".equals(properties.getProperty(configFileFields[i]))
					|| properties.getProperty(configFileFields[i]) == null)
				throw new Exception("Missing field " + configFileFields[i] + " in config file.");
	}

	public String getFieldValue(String field)
	{
		return properties.getProperty(field);
	}

	private Properties properties;

	private static String[] configFileFields;

	static
	{
		configFileFields = new String[]{"XMLschemaURI", "BDHostname", "BDUser", 
																		"BDPassword", "BDName"};
	}

}
