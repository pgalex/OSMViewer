package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import map.exceptions.TagIndexOutOfBoundsException;

/**
 * Tags, describes map object
 *
 * @author pgalex
 */
public class DefenitionTags implements ReadableMapData, WritableMapData
{
	/**
	 * Array of tags
	 */
	protected ArrayList<MapTag> tags;

	/**
	 * Create empty defenition tags
	 */
	public DefenitionTags()
	{
		tags = new ArrayList<MapTag>();
	}

	/**
	 * Get tags count
	 *
	 * @return tags count
	 */
	public int size()
	{
		return tags.size();
	}

	/**
	 * Is tags array empty
	 *
	 * @return is tags array empty
	 */
	public boolean isEmpty()
	{
		return tags.isEmpty();
	}

	/**
	 * Get tag by index
	 *
	 * @param index index of tag
	 * @return tag at index
	 * @throws TagIndexOutOfBoundsException index is out of bounds 
	 */
	public MapTag get(int index) throws TagIndexOutOfBoundsException
	{
		if (index < 0 || index >= tags.size())
		{
			throw new TagIndexOutOfBoundsException(index, 0, tags.size());
		}

		return tags.get(index);
	}

	/**
	 * Is all tags of this defenition tags including in tagsForComparing
	 *
	 * @param tagsForComparing tags for comparing
	 * @return is tagsForComparing contains this all of this tags
	 */
	public boolean includingIn(DefenitionTags tagsForComparing)
	{
		// (Сравнить теги без учета их порядка. Каждый тег из defenitionTags
		// должен входить в tagsForComparing )

		// deliberately noncoincidenting tags
		if (tagsForComparing == null)
		{
			return false;
		}
		if (tags.size() > tagsForComparing.size())
		{
			return false;
		}
		// null tags are equal
		if (tags.isEmpty() && tagsForComparing.isEmpty())
		{
			return true;
		}
		if (tags.isEmpty() && !tagsForComparing.isEmpty())
		{
			return false;
		}

		for (MapTag thisTag : tags)
		{
			boolean thisTagFoundInCompatingTags = false;

			for (int i = 0; i < tagsForComparing.size(); i++)
			{
				MapTag comparingTag = tagsForComparing.get(i);

				if (thisTag.compareTo(comparingTag))
				{
					thisTagFoundInCompatingTags = true;
					break;
				}
			}

			if (!thisTagFoundInCompatingTags)
			{
				return false;
			}
		}

		return true;
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
			tags.clear();
			int tagsCount = input.readInt();
			for (int i = 0; i < tagsCount; i++)
			{
				MapTag readingTag = new MapTag();
				readingTag.readFromStream(input);
				tags.add(readingTag);
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
			output.writeInt(tags.size());
			for (MapTag tag : tags)
			{
				tag.writeToStream(output);
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
