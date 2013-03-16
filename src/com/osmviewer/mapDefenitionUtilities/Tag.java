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
	 * Compare with tag. Ingnore case
	 *
	 * @param tagForComparing tag for comparing
	 * @return is tags eqaul
	 * @throws IllegalArgumentException tagForComparing is null
	 */
	public boolean compareTo(Tag tagForComparing) throws IllegalArgumentException
	{
		if (tagForComparing == null)
		{
			throw new IllegalArgumentException("tagForComparing is null");
		}

		if ((tagForComparing.key.compareToIgnoreCase(key) == 0) && (tagForComparing.value.compareToIgnoreCase(value) == 0))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			output.writeUTF(key);
			output.writeUTF(value);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			key = input.readUTF();
			value = input.readUTF();
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
