package drawingStyle;

import java.io.IOException;
import java.util.ArrayList;
import map.DefenitionTags;

/**
 * Gives access to drawing style with editing
 *
 * @author pgalex
 */
public class MapObjectStyleEditor implements StyleEditor
{
	/**
	 * Array of map object styles
	 */
	private ArrayList<MapObjectStyle> styles;

	/**
	 * Default constructor
	 */
	public MapObjectStyleEditor()
	{
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
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Set new style for map object, by id
	 *
	 * @param pStyleId style id
	 * @param pNewStyle new style
	 * @throws ArrayIndexOutOfBoundsException style id is out of bounds
	 */
	@Override
	public void setMapObjectStyle(int pStyleId, MapObjectStyle pNewStyle) throws ArrayIndexOutOfBoundsException
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Get all style ids
	 *
	 * @return all style ids
	 */
	@Override
	public int[] getIds()
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Load drawing style information from file
	 *
	 * @param pFileName file name
	 * @throws IOException reading error
	 */
	@Override
	public void loadFromFile(String pFileName) throws IOException
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Get id of map object drawing style
	 *
	 * @param pDefenitionTags tags of map object
	 * @return id of style of object with that defenition tags
	 */
	@Override
	public int getStyleId(DefenitionTags pDefenitionTags)
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Get map object drawing style by id
	 *
	 * @param pStyleId id of style
	 * @return map object drawing style
	 * @throws ArrayIndexOutOfBoundsException if style with this id not found
	 */
	@Override
	public MapObjectStyle getMapObjectStyle(int pStyleId) throws ArrayIndexOutOfBoundsException
	{
		throw new UnsupportedOperationException("Not supported yet.");
	}

	/**
	 * Add style
	 *
	 * @param pNewStyle new map object style
	 * @return id of new style
	 */
	@Override
	public int add(MapObjectStyle pNewStyle)
	{
		throw new UnsupportedOperationException("Not supported yet.");
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
		throw new UnsupportedOperationException("Not supported yet.");
	}
}
