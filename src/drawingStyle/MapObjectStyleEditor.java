package drawingStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import map.DefenitionTags;

/**
 * Gives access to drawing style with editing. All styles sorted by tags count
 *
 * @author pgalex
 */
public class MapObjectStyleEditor implements StyleEditor
{
	/**
	 * Array of map object styles. All styles sorted by tags count
	 */
	private ArrayList<MapObjectStyle> styles;

	/**
	 * Default constructor
	 */
	public MapObjectStyleEditor()
	{
		styles = new ArrayList<MapObjectStyle>();
	}

	/**
	 * Write styles to file
	 *
	 * @param pFileName file name
	 * @throws IOException writing error
	 */
	@Override
	public void saveToFile(String pFileName) throws IOException
	{
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(pFileName));

			output.writeInt(styles.size());
			for (int i = 0; i < styles.size(); i++)
				styles.get(i).writeToStream(output);

			output.close();
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
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
		// opening file - other exception
		DataInputStream input = null;
		try
		{
			input = new DataInputStream(new FileInputStream(pFileName));
		}
		catch (Exception ex)
		{
			throw new FileNotFoundException();
		}

		// reading styles
		try
		{
			styles.clear();

			int styleSize = input.readInt();
			for (int i = 0; i < styleSize; i++)
			{
				MapObjectStyle currentStyle = new MapObjectStyle();
				currentStyle.readFromStream(input);
				styles.add(currentStyle);
			}

			input.close();
		}
		catch (IOException ex)
		{
			throw new IOException();
		}
		
		Collections.sort(styles);
	}

	/**
	 * Set new style by index
	 *
	 * @param pIndex style index
	 * @param pNewStyle new style
	 * @throws ArrayIndexOutOfBoundsException style index is out of bounds
	 * @throws NullPointerException new style is null
	 */
	@Override
	public void set(int pIndex, MapObjectStyle pNewStyle) throws ArrayIndexOutOfBoundsException, NullPointerException
	{
		if (pIndex < 0 || pIndex >= styles.size())
			throw new ArrayIndexOutOfBoundsException();
		if (pNewStyle == null)
			throw new NullPointerException();

		styles.set(pIndex, pNewStyle);
		
		Collections.sort(styles);
	}

	/**
	 * Get styles count
	 *
	 * @return styles count
	 */
	@Override
	public int count()
	{
		return styles.size();
	}

	/**
	 * Get id of map object drawing style
	 *
	 * @param pDefenitionTags tags of map object
	 * @return id of style of object with that defenition tags
	 * @throws ArrayStoreException object not found
	 */
	@Override
	public int getStyleIndex(DefenitionTags pDefenitionTags) throws ArrayStoreException
	{
		if (pDefenitionTags == null)
			throw new ArrayStoreException();
		
		for (int i = 0; i < styles.size(); i++)
		{
			if (styles.get(i).getDefenitionTags().compareTo(pDefenitionTags))
				return i;
		}
		throw new ArrayStoreException();
	}

	/**
	 * Get map object drawing style by id
	 *
	 * @param pIndex id of style
	 * @return map object drawing style
	 * @throws ArrayIndexOutOfBoundsException if style with this id not found
	 */
	@Override
	public MapObjectStyle getMapObjectStyle(int pIndex) throws ArrayIndexOutOfBoundsException
	{
		if (pIndex < 0 || pIndex >= styles.size())
			throw new ArrayIndexOutOfBoundsException();
		return styles.get(pIndex);
	}

	/**
	 * Add style
	 *
	 * @param pNewStyle new map object style
	 * @throws NullPointerException new style is null
	 */
	@Override
	public void add(MapObjectStyle pNewStyle) throws NullPointerException
	{
		if (pNewStyle == null)
			throw new NullPointerException();
		styles.add(pNewStyle);
		
		Collections.sort(styles);
	}

	/**
	 * Remove style with specefied id
	 *
	 * @param pStyleId style id
	 * @throws ArrayIndexOutOfBoundsException id out of bounds
	 */
	@Override
	public void remove(int pStyleId) throws ArrayIndexOutOfBoundsException
	{
		if (pStyleId < 0 || pStyleId >= styles.size())
			throw new ArrayIndexOutOfBoundsException();
		styles.remove(pStyleId);
	}
}
