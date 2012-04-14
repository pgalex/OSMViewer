package drawingStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.SortedMap;
import java.util.TreeMap;
import map.DefenitionTags;

/**
 * Common methods used in Editor and Viewer.
 *
 * @author pgalex
 */
public class StyleProcessor
{
	/**
	 * Write styles to stream
	 *
	 * @param pStyles styles to write
	 * @param pOutput output stream
	 * @throws IOException writing error
	 */
	public static void writeStylesToStream(MapObjectStyle[] pStyles, DataOutputStream pOutput) throws IOException
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
	public static MapObjectStyle[] readStylesFromStream(DataInputStream pInput) throws IOException
	{
		if (pInput == null)
			throw new IOException();

		try
		{
			int stylesLength = pInput.readInt();
			MapObjectStyle[] readingStyles = new MapObjectStyle[stylesLength];
			for (int i = 0; i < stylesLength; i++)
			{
				readingStyles[i] = new MapObjectStyle();
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
	public static Integer findStyleIndex(MapObjectStyle[] pStyles, DefenitionTags pTags)
	{
		// Необходимо найти среди pStyle стиль у котороге теги будут совпадать с pTags,
		// количество тегов по сравнению с другими стилями(теги которых тоже совпадают) будет максимальным 
		// style with tags like pTags and max tags count

		if (pStyles == null || pTags == null)
			return null;

		SortedMap<MapObjectStyle, Integer> suitableElements = new TreeMap<MapObjectStyle, Integer>();
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
}
