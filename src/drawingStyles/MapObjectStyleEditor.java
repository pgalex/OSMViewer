package drawingStyles;

import drawingStyles.exceptions.MapDrawingSettingsIsNullException;
import drawingStyles.exceptions.MapObjectStyleIsNullException;
import drawingStyles.exceptions.StyleIndexOutOfBoundsException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Gives access to drawing style with editing
 *
 * @author pgalex
 */
public class MapObjectStyleEditor extends MapObjectDrawStylesContainer implements StyleEditor
{
	/**
	 * Array of map object styles. All styles sorted by tags
	 * countOfMapObjectStyles
	 */
	private ArrayList<MapObjectStyle> styles;

	/**
	 * Default constructor
	 */
	public MapObjectStyleEditor()
	{
		super();

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
			super.writeToStream(pOutput);

			writeStylesToStream(styles.toArray(new MapObjectStyle[styles.size()]), pOutput);
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
			super.readFromStream(pInput);

			styles.clear();
			MapObjectStyle[] readingStyles = readStylesFromStream(pInput);
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
	public void setMapObjectStyle(Integer pIndex, MapObjectStyle pNewStyle) throws StyleIndexOutOfBoundsException, MapObjectStyleIsNullException
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
	 * Get styles countOfMapObjectStyles
	 *
	 * @return styles countOfMapObjectStyles
	 */
	@Override
	public int countOfMapObjectStyles()
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

		return findStyleIndex(styles.toArray(new MapObjectStyle[styles.size()]), pDefenitionTags);
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
	 * @throws MapObjectStyleIsNullException new style is null
	 */
	@Override
	public void addMapObjectStyle(MapObjectStyle pNewStyle) throws MapObjectStyleIsNullException
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
	public void removeMapObjectStyle(Integer pIndex) throws StyleIndexOutOfBoundsException
	{
		if (pIndex == null)
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());

		if (pIndex < 0 || pIndex >= styles.size())
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());

		styles.remove((int) pIndex);
	}

	/**
	 * Get map drawing settings
	 *
	 * @return map drawing settings
	 */
	@Override
	public MapDrawingSettings getMapDrawingSettings()
	{
		return mapDrawingSettings;
	}

	/**
	 * Set new map drawing settings
	 *
	 * @param pMapDrawingSettings new map drawing settings
	 * @throws MapDrawingSettingsIsNullException new settins is null
	 */
	@Override
	public void setMapDrawingSettings(MapDrawingSettings pMapDrawingSettings) throws MapDrawingSettingsIsNullException
	{
		if (pMapDrawingSettings == null)
			throw new MapDrawingSettingsIsNullException(this);

		mapDrawingSettings = pMapDrawingSettings;
	}
}
