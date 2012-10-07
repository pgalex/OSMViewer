package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Keys of tags, value of, can be use as text under map object.
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
	 * Keys of tags that contains text
	 */
	private ArrayList<String> tagsKeys;

	/**
	 * Create with default tags keys
	 */
	public TextTagsKeys()
	{
		tagsKeys = new ArrayList<String>(DEFAULT_TEXT_TAGS_KEYS.length);
		tagsKeys.addAll(Arrays.asList(DEFAULT_TEXT_TAGS_KEYS));
	}

	/**
	 * Get text tags keys count
	 *
	 * @return text tags keys count
	 */
	public int getKeysCount()
	{
		return tagsKeys.size();
	}

	/**
	 * Get tag key with index
	 *
	 * @param index index of tag key to get. Must be from 0 to keysCount - 1
	 * @return tag key by index
	 * @throws IllegalArgumentException index is out of bounds
	 */
	public String getKey(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= tagsKeys.size())
		{
			throw new IllegalArgumentException();
		}

		return tagsKeys.get(index);
	}

	/**
	 * Add tag key
	 *
	 * @param keyToAdd key of tag to add. Must be not null and not empty
	 * @throws IllegalArgumentException key of tag is null or empty
	 */
	public void addKey(String keyToAdd) throws IllegalArgumentException
	{
		if (keyToAdd == null)
		{
			throw new IllegalArgumentException();
		}
		if (keyToAdd.isEmpty())
		{
			throw new IllegalArgumentException();
		}

		tagsKeys.add(keyToAdd);
	}
	
	/**
	 * Remove all tag keys
	 */
	public void removeAllKeys()
	{
		tagsKeys.clear();
	}

	/**
	 * Find value of tag that means text description of object. Priority
	 * determines by keys indexes
	 *
	 * @param tagsWhereFindText tags of object
	 * @return text description of object founded in tag. Empty if not found
	 * @throws IllegalArgumentException tagsWhereFindText is null
	 */
	public String findTextInTags(DefenitionTags tagsWhereFindText) throws IllegalArgumentException
	{
		if (tagsWhereFindText == null)
		{
			throw new IllegalArgumentException();
		}

		for (int keyIndex = 0; keyIndex < tagsKeys.size(); keyIndex++)
		{
			for (int tagIndex = 0; tagIndex < tagsWhereFindText.size(); tagIndex++)
			{
				MapTag tag = tagsWhereFindText.get(tagIndex);
				if (tag.getKey().equals(tagsKeys.get(keyIndex)))
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
			tagsKeys = new ArrayList<String>(tagsKeysLength);

			for (int i = 0; i < tagsKeysLength; i++)
			{
				String readTagKey = input.readUTF();
				tagsKeys.add(readTagKey);
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
			output.writeInt(tagsKeys.size());
			for (int i = 0; i < tagsKeys.size(); i++)
			{
				output.writeUTF(tagsKeys.get(i));
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
