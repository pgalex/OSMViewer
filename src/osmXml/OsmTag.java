package osmXml;

/**
 * Tag from .osm file. MapTag not using for better seperating of packages
 *
 * @author pgalex
 */
public class OsmTag
{
	/**
	 * Key of tag
	 */
	private String key;
	/**
	 * Value of tag
	 */
	private String value;

	/**
	 * Default constructor
	 */
	public OsmTag()
	{
		key = "";
		value = "";
	}

	/**
	 * Set key of tag
	 *
	 * @param pKey key of tag
	 */
	public void setKey(String pKey)
	{
		key = pKey;
	}

	/**
	 * Get key of tag
	 *
	 * @return key of tag
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * Set value of tag
	 *
	 * @param pValue value of tag
	 */
	public void setValue(String pValue)
	{
		value = pValue;
	}

	/**
	 * Get value of tag
	 *
	 * @return value of tag
	 */
	public String getValue()
	{
		return value;
	}
}
