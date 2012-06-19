package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import map.MapTag;

/**
 * Map object tags
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
	 * Default constructor
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
	 * Is empty
	 *
	 * @return is tags array empty
	 */
	public boolean isEmpty()
	{
		return tags.isEmpty();
	}

	/**
	 * Get tag with index
	 *
	 * @param pIndex index
	 * @return tag, null if index is out of bounds
	 */
	public MapTag get(int pIndex)
	{
		if (pIndex < 0 || pIndex >= tags.size())
			return null;
		return tags.get(pIndex);
	}

	/**
	 *
	 * Compare defenition tags. (Сравнить теги без учета их порядка. Каждый тег из
	 * defenitionTags должен входить в pTags )
	 *
	 * @param pTags tags for comparing
	 * @return is pTags defines this objects
	 */
	public boolean compareTo(DefenitionTags pTags)
	{
		//заведомо несопадающие теги
		if (pTags == null)
			return false;
		if (tags.size() > pTags.size())
			return false;
		// пустые списки считаются равны
		if (tags.isEmpty() && pTags.isEmpty())
			return true;

		if (tags.isEmpty() && !pTags.isEmpty())
			return false;

		for (MapTag defenitionTempTag : tags)
		{
			boolean currentCompareResult = false;
			for (MapTag parameterTempTag : pTags.tags)
			{
				if (defenitionTempTag.compareTo(parameterTempTag))
				{
					currentCompareResult = true;
					break;
				}
			}
			if (!currentCompareResult)
				return false;
		}
		return true;
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
			tags.clear();
			int tagsCount = pInput.readInt();
			for (int i = 0; i < tagsCount; i++)
			{
				MapTag tempTag = new MapTag();
				tempTag.readFromStream(pInput);
				tags.add(tempTag);
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
			for (MapTag tempTag : tags)
				tempTag.writeToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
