package com.osmviewer.map.exceptions;

/**
 * Error while connecting to server
 *
 * @author pgalex
 */
public class ConnectionErrorException extends MapException
{
	/**
	 * String used for connecting
	 */
	private String connectionString;

	/**
	 * Create exception
	 *
	 * @param stringUsedForConnection String used for connecting
	 */
	public ConnectionErrorException(String stringUsedForConnection)
	{
		connectionString = stringUsedForConnection;
	}

	/**
	 * Get URL to server used for connecting
	 *
	 * @return URL to server used for connecting
	 */
	public String getUrlUsedForConnecting()
	{
		return connectionString;
	}
}
