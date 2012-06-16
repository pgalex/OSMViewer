package map.exceptions;

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
	 * Constructor
	 *
	 * @param pConnectionString String used for connecting
	 */
	public ConnectionErrorException(String pConnectionString)
	{
		connectionString = pConnectionString;
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
