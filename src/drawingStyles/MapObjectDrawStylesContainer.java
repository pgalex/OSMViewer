package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Common part of MapObjectStyleViewer and Editor.
 *
 * @author pgalex
 */
public class MapObjectDrawStylesContainer implements ReadableMapData, WritableMapData
{
	/**
	 * Information about map drawing
	 */
	protected MapDrawingSettings mapDrawingSettings;

	/**
	 * Default constructor
	 */
	public MapObjectDrawStylesContainer()
	{
		mapDrawingSettings = new MapDrawingSettings(null);
	}

	/**
	 * Write styles to stream
	 *
	 * @param pStyles styles to write
	 * @param pOutput output stream
	 * @throws IOException writing error
	 */
	protected void writeStylesToStream(MapObjectDrawSettings[] pStyles, DataOutputStream pOutput) throws IOException
	{
		if (pStyles == null || pOutput == null)
			throw new IOException();

		try
		{
			pOutput.writeInt(pStyles.length);
			for (int i = 0; i < pStyles.length; i++)
				pStyles[i].writeToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException();
		}
	}

	/**
	 * Read style from stream
	 *
	 * @param pInput input stream
	 * @return readed styles
	 * @throws IOException reading error
	 */
	protected MapObjectDrawSettings[] readStylesFromStream(DataInputStream pInput) throws IOException
	{
		if (pInput == null)
		{
			throw new IOException();
		}

		try
		{
			int stylesLength = pInput.readInt();
			MapObjectDrawSettings[] readingStyles = new MapObjectDrawSettings[stylesLength];
			for (int i = 0; i < stylesLength; i++)
			{
				readingStyles[i] = new MapObjectDrawSettings();
				readingStyles[i].readFromStream(pInput);
			}

			return readingStyles;
		}
		catch (Exception e)
		{
			throw new IOException();
		}
	}

	/**
	 * Find index map object style with specified tags and max tags count in array
	 *
	 * @param pStyles array of styles
	 * @param pTags defenition tags for search
	 * @return index of founded style. null if not found
	 */
	protected Integer findStyleIndex(MapObjectDrawSettings[] pStyles, DefenitionTags pTags)
	{
		// Необходимо найти среди pStyle стиль у котороге теги будут совпадать с pTags,
		// количество тегов по сравнению с другими стилями(теги которых тоже совпадают) будет максимальным 
		// style with tags like pTags and max tags count

		if (pStyles == null || pTags == null)
			return null;

		SortedMap<MapObjectDrawSettings, Integer> suitableElements = new TreeMap<MapObjectDrawSettings, Integer>();
		for (int i = 0; i < pStyles.length; i++)
		{
			if (pStyles[i] == null)
				continue;
			if (pStyles[i].getDefenitionTags().compareTo(pTags))
				suitableElements.put(pStyles[i], i);
		}

		if (suitableElements.isEmpty())
			return null;
		else
			return suitableElements.get(suitableElements.firstKey());

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
			mapDrawingSettings.readFromStream(pInput);
		}
		catch (Exception ex)
		{
			throw new IOException();
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
			mapDrawingSettings.writeToStream(pOutput);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}
