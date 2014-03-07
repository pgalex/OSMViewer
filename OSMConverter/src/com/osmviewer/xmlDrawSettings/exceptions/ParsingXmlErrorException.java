package com.osmviewer.xmlDrawSettings.exceptions;

/**
 * Error while parsing XML draw settings
 *
 * @author preobrazhentsev
 */
public class ParsingXmlErrorException extends Exception
{
	public ParsingXmlErrorException(String message)
	{
		super(message);
	}

	public ParsingXmlErrorException(Throwable cause)
	{
		super(cause);
	}
}
