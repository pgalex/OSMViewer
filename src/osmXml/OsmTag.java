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
	 * Create tag with parameters
	 * 
	 * @param tagKey Key of tag
	 * @param tagValue Value of tag
	 */
	public OsmTag(String tagKey, String tagValue)
	{
		key = tagKey;
		value = tagValue;
	}

	/**
	 * Set key of tag
	 *
	 * @param keyToSet key of tag
	 */
	public void setKey(String keyToSet)
	{
		key = keyToSet;
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
	 * @param valueToSet value of tag
	 */
	public void setValue(String valueToSet)
	{
		value = valueToSet;
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
