package es.uniovi.aic.miex.config;

import es.uniovi.aic.miex.exceptions.MissingFieldInConfigFileException;

import java.util.Properties;
import java.io.*;

public class Config
{

	public Config()
	{
		properties = new Properties();
	}

	public void parseOptionsFromFile(String fileName) throws IOException, MissingFieldInConfigFileException
	{
		properties.load(new FileInputStream(fileName));
		this.checkFile();
	}

	private void checkFile() throws MissingFieldInConfigFileException
	{
		for(int i=0; i < configFileFields.length; i++)
			if("".equals(properties.getProperty(configFileFields[i]))
					|| properties.getProperty(configFileFields[i]) == null)
				throw new MissingFieldInConfigFileException(configFileFields[i]); 
	}

	public String getFieldValue(String field)
	{
		return properties.getProperty(field);
	}

	private Properties properties;

	private static String[] configFileFields;

	static
	{
		configFileFields = new String[]{"XMLschemaURI", "BDHostname", "BDUser", "BDPassword", "BDName"};
	}

}
