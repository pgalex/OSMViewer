package drawingStyle;

import drawingStyle.exceptions.MapObjectStyleIsNullException;
import drawingStyle.exceptions.StyleIndexOutOfBoundsException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import map.DefenitionTags;

/**
 * Gives access to drawing style with editing
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
			StyleProcessor.writeStylesToStream(styles.toArray(new MapObjectStyle[styles.size()]), pOutput);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
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
			styles.clear();
			MapObjectStyle[] readingStyles = StyleProcessor.readStylesFromStream(pInput);
			Collections.addAll(styles, readingStyles);
		}
		catch (IOException ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Set new style by index
	 *
	 * @param pIndex style index
	 * @param pNewStyle new style
	 * @throws StyleIndexOutOfBoundsException style index is out of bounds
	 * @throws MapObjectStyleIsNullException new style is null
	 */
	@Override
	public void set(Integer pIndex, MapObjectStyle pNewStyle) throws StyleIndexOutOfBoundsException, MapObjectStyleIsNullException
	{
		if (pIndex == null)
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());

		if (pIndex < 0 || pIndex >= styles.size())
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());

		if (pNewStyle == null)
			throw new MapObjectStyleIsNullException(this);

		styles.set(pIndex, pNewStyle);
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
	 * @return id of style of object with that defenition tags. null if not found
	 */
	@Override
	public Integer getStyleIndex(DefenitionTags pDefenitionTags)
	{
		if (pDefenitionTags == null)
			return null;

		return StyleProcessor.findStyleIndex(styles.toArray(new MapObjectStyle[styles.size()]), pDefenitionTags);
	}

	/**
	 * Get map object drawing style by id
	 *
	 * @param pIndex id of style
	 * @return map object drawing style. null if style with this id not found
	 */
	@Override
	public MapObjectStyle getMapObjectStyle(Integer pIndex)
	{
		if (pIndex == null)
			return null;
		if (pIndex < 0 || pIndex >= styles.size())
			return null;

		return styles.get(pIndex);
	}

	/**
	 * Add style
	 *
	 * @param pNewStyle new map object style
	 * @throws NullPointerException new style is null
	 */
	@Override
	public void add(MapObjectStyle pNewStyle) throws MapObjectStyleIsNullException
	{
		if (pNewStyle == null)
			throw new MapObjectStyleIsNullException(this);

		styles.add(pNewStyle);
	}

	/**
	 * Remove style by id
	 *
	 * @param pIndex style id
	 * @throws StyleIndexOutOfBoundsException id out of bounds
	 */
	@Override
	public void remove(Integer pIndex) throws StyleIndexOutOfBoundsException
	{
		if (pIndex == null)
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());

		if (pIndex < 0 || pIndex >= styles.size())
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());

		styles.remove((int) pIndex);
	}
}
