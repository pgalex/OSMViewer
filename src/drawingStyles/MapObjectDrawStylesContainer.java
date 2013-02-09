package drawingStyles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;
import mapDefenitionUtilities.DefenitionTags;

/**
 * Common part of MapObjectStyleViewer and Editor. Uses to remove duplicating
 *
 * @author pgalex
 */
public class MapObjectDrawStylesContainer
{
	/**
	 * Common draw settings of map
	 */
	protected MapDrawSettings mapDrawSettings;

	/**
	 * Create with default values
	 */
	public MapObjectDrawStylesContainer()
	{
		mapDrawSettings = new MapDrawSettings();
	}

	/**
	 * Write styles to stream
	 *
	 * @param stylesToWrite styles to write
	 * @param output output stream
	 * @throws IOException writing error
	 */
	protected void writeStylesToStream(MapObjectDrawSettings[] stylesToWrite, DataOutputStream output) throws IOException
	{
		if (stylesToWrite == null || output == null)
		{
			throw new IOException();
		}

		try
		{
			output.writeInt(stylesToWrite.length);
			for (int i = 0; i < stylesToWrite.length; i++)
			{
				stylesToWrite[i].writeToStream(output);
			}
		}
		catch (Exception e)
		{
			throw new IOException();
		}
	}

	/**
	 * Read style from stream
	 *
	 * @param input input stream
	 * @return readed styles
	 * @throws IOException reading error
	 */
	protected MapObjectDrawSettings[] readStylesFromStream(DataInputStream input) throws IOException
	{
		if (input == null)
		{
			throw new IOException();
		}

		try
		{
			int stylesLength = input.readInt();
			MapObjectDrawSettings[] readingStyles = new MapObjectDrawSettings[stylesLength];
			for (int i = 0; i < stylesLength; i++)
			{
				readingStyles[i] = new MapObjectDrawSettings();
				readingStyles[i].readFromStream(input);
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
	 * @param styles array of styles where find index if map object draw style
	 * @param objectDefenitionTags defenition tags for search
	 * @return index of founded style. null if not found
	 */
	protected Integer findStyleIndex(MapObjectDrawSettings[] styles, DefenitionTags objectDefenitionTags)
	{
		// Необходимо найти среди pStyle стиль у котороге теги будут совпадать с objectDefenitionTags,
		// количество тегов по сравнению с другими стилями(теги которых тоже совпадают) будет максимальным 
		// style with tags like objectDefenitionTags and max tags count

		if (styles == null || objectDefenitionTags == null)
		{
			return null;
		}

		SortedMap<MapObjectDrawSettings, Integer> suitableElements = new TreeMap<MapObjectDrawSettings, Integer>(new FindStyleIndexComparator());
		for (int i = 0; i < styles.length; i++)
		{
			if (styles[i] == null)
			{
				continue;
			}

			if (styles[i].getDefenitionTags().includingIn(objectDefenitionTags))
			{
				suitableElements.put(styles[i], i);
			}
		}

		if (suitableElements.isEmpty())
		{
			return null;
		}
		else
		{
			return suitableElements.get(suitableElements.firstKey());
		}
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			mapDrawSettings.readFromStream(input);
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			mapDrawSettings.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}
}
