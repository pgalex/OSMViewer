package drawingStyle;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import map.DefenitionTags;

/**
 * Gives access to drawing style without editing but optimized for runtime map
 * drawing
 *
 * @author pgalex
 */
public class MapObjectStyleViewer implements StyleViewer
{
	/**
	 * Static array. Work faster. All styles sorted by tags count
	 */
	private MapObjectStyle[] styles;

	/**
	 * Default constructor
	 */
	public MapObjectStyleViewer()
	{
		styles = new MapObjectStyle[0];
	}

	/**
	 * Load drawing style information from file
	 *
	 * @param pFileName file name
	 * @throws IOException reading error
	 * @throws FileNotFoundException can not load file
	 */
	@Override
	public void loadFromFile(String pFileName) throws IOException, FileNotFoundException
	{
		DataInputStream input = null;
		try
		{
			input = new DataInputStream(new FileInputStream(pFileName));
		}
		catch (Exception ex)
		{
			throw new FileNotFoundException();
		}
		// for easy sorting
		ArrayList<MapObjectStyle> styleList = new ArrayList<MapObjectStyle>();
		try
		{
			int styleSize = input.readInt();
			for (int i = 0; i < styleSize; i++)
			{
				MapObjectStyle currentStyle = new MapObjectStyle();
				currentStyle.readFromStream(input);
				styleList.add(currentStyle);
			}
			input.close();
		}
		catch (IOException ex)
		{
			throw new IOException();
		}

		Collections.sort(styleList);
		styles = new MapObjectStyle[styleList.size()];
		for (int i = 0; i < styleList.size(); i++) // toArray throws excpetion
			styles[i] = styleList.get(i);
	}

	/**
	 * Get index of map object drawing style
	 *
	 * @param pDefenitionTags tags of map object
	 * @return index of style of object with that defenition tags
	 * @throws ArrayStoreException object not found
	 */
	@Override
	public int getStyleIndex(DefenitionTags pDefenitionTags) throws ArrayStoreException
	{
		if (pDefenitionTags == null)
			throw new ArrayStoreException();

		for (int i = 0; i < styles.length; i++)
		{
			if (styles[i].getDefenitionTags().compareTo(pDefenitionTags))
				return i;
		}
		throw new ArrayStoreException();
	}

	/**
	 * Get map object drawing style by index
	 *
	 * @param pIndex index of style
	 * @return map object drawing style
	 * @throws ArrayIndexOutOfBoundsException index is out of bounds
	 */
	@Override
	public MapObjectStyle getMapObjectStyle(int pIndex) throws ArrayIndexOutOfBoundsException
	{
		if (pIndex < 0 || pIndex >= styles.length)
			throw new ArrayIndexOutOfBoundsException();
		return styles[pIndex];
	}
}
