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
	 * Default text tags keys
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
	 * Create with default tag keys
	 */
	public TextTagsKeys()
	{
		tagsKeys = DEFAULT_TEXT_TAGS_KEYS;
	}

	/**
	 * Create by tag keys array
	 *
	 * @param keys array of tag keys that can be use as text (object description).
	 * Reseting to default if null
	 */
	public TextTagsKeys(String[] keys)
	{
		tagsKeys = keys;
		
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
	 * @param tagsWhereFindText tags of object
	 * @return text description of object founded in tag. Empty if not found
	 */
	public String findTextInTags(DefenitionTags tagsWhereFindText)
	{
		// Keys priority - from begin to end of tagsKeys
		if (tagsWhereFindText == null)
		{
			return "";
		}

		for (int keyIndex = 0; keyIndex < tagsKeys.length; keyIndex++)
		{
			for (int tagIndex = 0; tagIndex < tagsWhereFindText.size(); tagIndex++)
			{
				MapTag tag = tagsWhereFindText.get(tagIndex);
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
	 * @param input input stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			int tagsKeysLength = input.readInt();
			tagsKeys = new String[tagsKeysLength];

			for (int i = 0; i < tagsKeysLength; i++)
			{
				tagsKeys[i] = input.readUTF();
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
	 * @param output output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			output.writeInt(tagsKeys.length);
			for (int i = 0; i < tagsKeys.length; i++)
			{
				output.writeUTF(tagsKeys[i]);
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
