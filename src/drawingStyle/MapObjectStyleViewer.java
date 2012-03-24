package drawingStyle;

import java.io.*;
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

		try
		{
			styles = StyleProcessor.readStylesFromStream(input);
			input.close();
		}
		catch (IOException ex)
		{
			throw new IOException();
		}
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

		try
		{
			return StyleProcessor.findStyleIndex(styles, pDefenitionTags);
		}
		catch (ArrayStoreException e)
		{
			throw new ArrayStoreException();
		}
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
			StyleProcessor.writeStylesToStream(styles, output);
			output.close();
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}