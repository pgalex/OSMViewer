package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Keys of tags, value of will be drawn on a map as text under map object.
 *
 * @author pgalex
 */
public class TextTagsKeys implements ReadableMapData, WritableMapData
{
	/**
	 * Default array of tags keys
	 */
	private String[] DEFAULT_TEXT_TAGS_KEYS =
	{
		"name", "description"
	};
	/**
	 * Keys of tags that contains text for drawing
	 */
	private String[] tagsKeys;

	/**
	 * Default contructor
	 */
	public TextTagsKeys()
	{
		tagsKeys = DEFAULT_TEXT_TAGS_KEYS;
	}

	/**
	 * Contructor
	 *
	 * @param pTagsKeys
	 */
	public TextTagsKeys(String[] pTagsKeys)
	{
		tagsKeys = pTagsKeys;
	}

	/**
	 * Get text tags keys
	 *
	 * @return tags keys
	 */
	public String[] getTagsKeys()
	{
		return tagsKeys;
	}

	/**
	 * Auto initialize null fields
	 */
	private void InitializeNullFields()
	{
		if (tagsKeys == null)
			tagsKeys = DEFAULT_TEXT_TAGS_KEYS;
		// length can be == 0
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
			int tagsKeysLength = pInput.readInt();
			tagsKeys = new String[tagsKeysLength];

			for (int i = 0; i < tagsKeysLength; i++)
				tagsKeys[i] = pInput.readUTF();
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
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
			pOutput.writeInt(tagsKeys.length);
			for (int i = 0; i < tagsKeys.length; i++)
				pOutput.writeUTF(tagsKeys[i]);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
