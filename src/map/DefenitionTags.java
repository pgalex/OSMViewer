package map;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

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
	 * Constructor
	 *
	 * @param pTags array of tags
	 */
	public DefenitionTags(ArrayList<MapTag> pTags)
	{
		tags = pTags;
		InitializeNullFields();
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
	 * @return tag
	 * @throws ArrayIndexOutOfBoundsException index out of array bounds
	 */
	public MapTag get(int pIndex) throws ArrayIndexOutOfBoundsException
	{
		if (pIndex < 0 || pIndex >= tags.size())
			throw new ArrayIndexOutOfBoundsException();
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
	 * Set default values into null fields
	 */
	private void InitializeNullFields()
	{
		if (tags == null)
			tags = new ArrayList<MapTag>();
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
