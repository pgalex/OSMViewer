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
	 * @param pIndex index
	 * @return tag with pIndex. null if index is out of bounds
	 * @throws TagIndexOutOfBoundsException index is out of bounds 
	 */
	public MapTag get(int pIndex) throws TagIndexOutOfBoundsException
	{
		if (pIndex < 0 || pIndex >= tags.size())
		{
			throw new TagIndexOutOfBoundsException(this, pIndex, 0, tags.size());
		}

		return tags.get(pIndex);
	}

	/**
	 * Smart comparing defenition tags
	 *
	 * @param pTags tags for comparing
	 * @return is pTags contains this tags
	 */
	public boolean compareTo(DefenitionTags pTags)
	{
		// (Сравнить теги без учета их порядка. Каждый тег из defenitionTags
		// должен входить в pTags )

		// deliberately noncoincidenting tags
		if (pTags == null)
		{
			return false;
		}
		if (tags.size() > pTags.size())
		{
			return false;
		}
		// null tags are equal
		if (tags.isEmpty() && pTags.isEmpty())
		{
			return true;
		}
		if (tags.isEmpty() && !pTags.isEmpty())
		{
			return false;
		}

		for (MapTag thisTag : tags)
		{
			boolean thisTagFoundInCompatingTags = false;

			for (int i = 0; i < pTags.size(); i++)
			{
				MapTag comparingTag = pTags.get(i);

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
	 * @param pInput input stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			tags.clear();
			int tagsCount = pInput.readInt();
			for (int i = 0; i < tagsCount; i++)
			{
				MapTag readingTag = new MapTag();
				readingTag.readFromStream(pInput);
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
	 * @param pOutput output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeInt(tags.size());
			for (MapTag tag : tags)
			{
				tag.writeToStream(pOutput);
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
