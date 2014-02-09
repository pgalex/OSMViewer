package com.osmviewer.mapDefenitionUtilities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
	 * @param tagKey key of tag
	 * @param tagValue value of tag
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
			throw new IllegalArgumentException("objectToCompare is not instance of Tag");
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

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	public void writeToStream(DataOutputStream output) throws IOException
	{
		output.writeUTF(key);
		output.writeUTF(value);
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	public void readFromStream(DataInputStream input) throws IOException
	{
		key = input.readUTF();
		value = input.readUTF();
	}
}
