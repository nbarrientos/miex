/* file name  : ConfigFile.java
 * authors    : Nacho Barrientos Arias <chipi@criptonita.com>
 * created    : 
 * copyright  : GPL
 *
 * modifications:
 *
 */
package es.uniovi.aic.miex.config;

import java.util.Properties;
import java.util.HashMap;
import java.io.*;
import java.lang.Exception;

/** 
 * Configuration files handler 
 * 
 * @author Nacho Barrientos Arias <chipi@criptonita.com>
 * @version 0.1
 */
public class ConfigFile
{

	/** 
	 * Creates the object, reads the configuration file and checks
	 * if it's in a good shape. 
	 * 
	 * @param fileName Path to the configuration file to read 
	 */
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

	/** 
	 * Checks configuration file consistency 
	 * 
	 * @throws Exception If something weird happens, i.e.:
   *				    - Missing mandatory fields
   *            - Boolean fields set as non-boolean 
	 */
	private void checkFile() throws Exception
	{
		
		for(int i=0; i < configFileFields.length; i++)
			if("".equals(properties.getProperty(configFileFields[i]))
					|| properties.getProperty(configFileFields[i]) == null)
				throw new Exception("Missing field " + configFileFields[i] + " in config file.");
		
		
		for(int i=0; i < configFileBooleanFields.length; i++)
			if(properties.getProperty(configFileBooleanFields[i]) != null)
			{
				if( !(properties.getProperty(configFileBooleanFields[i]).toLowerCase().equals("yes"))
						&&
						!(properties.getProperty(configFileBooleanFields[i]).toLowerCase().equals("no")))
			throw new Exception("Boolean keys must only contain \"Yes\" or \"No\" values");
			}
	}

	/** 
	 * Observer method for non-boolean settings
	 * 
	 * @param stt The property to fetch
	 * @return The value 
	 */
	public String getStringSetting(String stt)
	{
		if(properties.getProperty(stt) == null)
			return defaultValuesString.get(stt.toLowerCase());
		else
			return properties.getProperty(stt);
	}

	/** 
	 * Observer method for boolean settings 
	 * 
	 * @param stt The property to fetch 
	 * @return The value
	 */
	public boolean getBooleanSetting(String stt)
	{
		if(properties.getProperty(stt) == null)
			return defaultValuesBoolean.get(stt.toLowerCase()).booleanValue();
		else
			if(properties.getProperty(stt).toLowerCase().equals("yes"))
				return true;
			else
				return false;
	}

	/** 
	 * Storage for all settings after reading the configuration file 
	 */
	private Properties properties;

	/** 
	 * Self-explaining 
	 */
	private static String[] configFileFields, configFileBooleanFields; 
	
	/** 
	 * Self-explaining 
	 */
	private static HashMap<String,String> defaultValuesString;
	
	/** 
	 * Self-explaining 
	 */
	private static HashMap<String,Boolean> defaultValuesBoolean;

  static
  {
    configFileFields = new String[]{"BDHostname", "BDUser", "BDPassword", "BDName"};

		configFileBooleanFields = new String[]{"Validate", "CreateDB", "Dump"};
		
		defaultValuesString = new HashMap<String,String>();
		defaultValuesBoolean = new HashMap<String,Boolean>();

		// Loading default values to boolean settings
		defaultValuesBoolean.put("validate", new Boolean(false));
		defaultValuesBoolean.put("dump", new Boolean(false));
		defaultValuesBoolean.put("createdb", new Boolean(false));

		// Loading default values to string settings
		defaultValuesString.put("xmlschemauri", "/usr/share/miex/schemas/default.xsd");
    defaultValuesString.put("dumpdir", "/tmp");
    defaultValuesString.put("sqlskeleton", "/usr/share/miex/sql/skeleton.sql");

  }

}
