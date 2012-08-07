package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
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
	 * @param pTagsKeys array of tag keys that can be use as text (object
	 * description)
	 */
	public TextTagsKeys(String[] pTagsKeys)
	{
		tagsKeys = pTagsKeys;
		initializeNullFields();
	}

	/**
	 * Auto initialize null fields
	 */
	private void initializeNullFields()
	{
		if (tagsKeys == null)
		{
			tagsKeys = DEFAULT_TEXT_TAGS_KEYS;
		}
		// length can be == 0
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
	 * Find value of tag that means text description of object. Priority
	 * determines by keys indexes
	 *
	 * @param pTags tags of object
	 * @return text description of object founded in tag. Empty if not found
	 */
	public String findTextInTags(DefenitionTags pTags)
	{
		// Keys priority - from begin to end of tagsKeys
		if (pTags == null)
		{
			return "";
		}

		for (int keyIndex = 0; keyIndex < tagsKeys.length; keyIndex++)
		{
			for (int tagIndex = 0; tagIndex < pTags.size(); tagIndex++)
			{
				MapTag tag = pTags.get(tagIndex);
				if (tag.getKey().equals(tagsKeys[keyIndex]))
				{
					return tag.getValue();
				}
			}
		}

		return "";
	}

	/**
	 * Read from stream
	 *
	 * @param pInput input stream
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
			{
				tagsKeys[i] = pInput.readUTF();
			}
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
			{
				pOutput.writeUTF(tagsKeys[i]);
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
