package map;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Tag
 *
 * @author pgalex
 */
public class MapTag implements WritableMapData, ReadableMapData
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
	 * Default constructor
	 */
	public MapTag()
	{
		key = "";
		value = "";
	}

	/**
	 * Constructor
	 *
	 * @param pKey key
	 * @param pValue value
	 */
	public MapTag(String pKey, String pValue)
	{
		key = pKey;
		value = pValue;
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
	 * Set new key
	 *
	 * @param pKey the key to set
	 */
	public void setKey(String pKey)
	{
		key = pKey;
	}

	/**
	 * Get "value"
	 *
	 * @return the value
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * Set value
	 *
	 * @param pValue the pValue to set
	 */
	public void setValue(String pValue)
	{
		value = pValue;
	}

	/**
	 * Compare. Ingnore case
	 *
	 * @param pTag tag for comparing
	 * @return is tags eqaul
	 */
	public boolean compareTo(MapTag pTag)
	{
		if ((pTag.key.compareToIgnoreCase(key) == 0) && (pTag.value.compareToIgnoreCase(value) == 0))
			return true;
		else
			return false;
	}

	/**
	 * Write into stream
	 *
	 * @param pOutput output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeUTF(key);
			pOutput.writeUTF(value);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Read from stream
	 *
	 * @param pInput reading stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			key = pInput.readUTF();
			value = pInput.readUTF();
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
