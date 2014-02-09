package com.osmviewer.map.exceptions;

/**
 * Error while fetching map objects
 *
 * @author pgalex
 */
public class FetchingErrorException extends Exception
{
	public FetchingErrorException(Throwable thrwbl)
	{
		super(thrwbl);
	}
}
