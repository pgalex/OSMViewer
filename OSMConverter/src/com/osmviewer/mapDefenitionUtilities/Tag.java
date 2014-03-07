package com.osmviewer.mapDefenitionUtilities;

/**
 * Tag
 *
 * @author pgalex
 */
public class Tag
{
	/**
	 * key
	 */
	private String key;
	/**
	 * value
	 */
	private String value;

	/**
	 * Create with empty key and value
	 */
	public Tag()
	{
		key = "";
		value = "";
	}

	/**
	 * Create with parameters
	 *
	 * @param tagKey key of tag. Must be not null
	 * @param tagValue value of tag. Must be not null
	 * @throws IllegalArgumentException tagKey or tagValue is null
	 */
	public Tag(String tagKey, String tagValue) throws IllegalArgumentException
	{
		if (tagKey == null)
		{
			throw new IllegalArgumentException("tagKey is null");
		}
		if (tagValue == null)
		{
			throw new IllegalArgumentException("tagValue is null");
		}

		key = tagKey;
		value = tagValue;
	}

	/**
	 * Get key
	 *
	 * @return the key
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 * Get value
	 *
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Is equals to object
	 *
	 * @param objectToCompare object for comparing. Must be isntance of Tag class
	 * @return is equals to given object
	 */
	@Override
	public boolean equals(Object objectToCompare)
	{
		if (!(objectToCompare instanceof Tag))
		{
			return false;
		}

		Tag tagToCompare = (Tag) objectToCompare;
		return (tagToCompare.key.compareToIgnoreCase(key) == 0) && (tagToCompare.value.compareToIgnoreCase(value) == 0);
	}

	/**
	 * Get hash code value
	 *
	 * @return hash code value
	 */
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 17 * hash + (this.key != null ? this.key.hashCode() : 0);
		hash = 17 * hash + (this.value != null ? this.value.hashCode() : 0);
		return hash;
	}
}
