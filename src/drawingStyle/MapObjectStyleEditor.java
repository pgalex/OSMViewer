package drawingStyle;

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

		try
		{
			return StyleProcessor.findStyleIndex(styles.toArray(new MapObjectStyle[styles.size()]), pDefenitionTags);
		}
		catch (Exception e)
		{
			throw new ArrayStoreException();
		}
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
	}

	/**
	 * Remove style by id
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
