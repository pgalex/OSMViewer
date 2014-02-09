package com.osmviewer.osmXml.exceptions;

/**
 * Error while parsing .osm xml
 *
 * @author pgalex
 */
public class ParsingOsmErrorException extends Exception
{
	public ParsingOsmErrorException(Throwable thrwbl)
	{
		super(thrwbl);
	}
}