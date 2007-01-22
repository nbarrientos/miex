package es.uniovi.aic.miex.exceptions;

import java.lang.Exception;

public class MissingFieldInConfigFileException extends Exception
{

	public MissingFieldInConfigFileException(String rhs)
	{
		super(rhs);
	}

	public String toString()
	{
		return new String("Missing or incomplete field \"" + getMessage() + "\".");
	}

}
