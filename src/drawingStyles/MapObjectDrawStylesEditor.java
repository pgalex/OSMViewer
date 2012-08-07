package drawingStyles;

import drawingStyles.exceptions.MapDrawSettingsIsNullException;
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
public class MapObjectDrawStylesEditor extends MapObjectDrawStylesContainer implements StyleEditor
{
	/**
	 * Array of map object styles. All styles sorted by tags
	 * countOfMapObjectDrawSettings
	 */
	private ArrayList<MapObjectDrawSettings> styles;

	/**
	 * Default constructor
	 */
	public MapObjectDrawStylesEditor()
	{
		super();

		styles = new ArrayList<MapObjectDrawSettings>();
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

			writeStylesToStream(styles.toArray(new MapObjectDrawSettings[styles.size()]), pOutput);
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
			MapObjectDrawSettings[] readingStyles = readStylesFromStream(pInput);
			Collections.addAll(styles, readingStyles);
		}
		catch (IOException ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Set draw settings of map object by index
	 *
	 * @param pIndex style index
	 * @param pDrawSettings new draw settings of object with pIndex
	 * @throws StyleIndexOutOfBoundsException style index is out of bounds
	 * @throws MapObjectStyleIsNullException new draw settings is null
	 */
	@Override
	public void setMapObjectDrawSettings(Integer pIndex, MapObjectDrawSettings pDrawSettings) throws StyleIndexOutOfBoundsException, MapObjectStyleIsNullException
	{
		if (pIndex == null)
		{
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());
		}
		if (pIndex < 0 || pIndex >= styles.size())
		{
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());
		}
		if (pDrawSettings == null)
		{
			throw new MapObjectStyleIsNullException(this);
		}

		styles.set(pIndex, pDrawSettings);
	}

	/**
	 * Get count of storing map object draw setting
	 *
	 * @return count of storing map object draw setting
	 */
	@Override
	public int countOfMapObjectDrawSettings()
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
	public Integer findStyleIndex(DefenitionTags pDefenitionTags)
	{
		if (pDefenitionTags == null)
		{
			return null;
		}

		return findStyleIndex(styles.toArray(new MapObjectDrawSettings[styles.size()]), pDefenitionTags);
	}

	/**
	 * Get map object draw settings by index
	 *
	 * @param pIndex index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	@Override
	public MapObjectDrawSettings getMapObjectDrawSettings(Integer pIndex)
	{
		if (pIndex == null)
		{
			return null;
		}
		if (pIndex < 0 || pIndex >= styles.size())
		{
			return null;
		}

		return styles.get(pIndex);
	}

	/**
	 * Add draw settings of map object
	 *
	 * @param pDrawSettings new map object style
	 * @throws MapObjectStyleIsNullException new style is null
	 */
	@Override
	public void addMapObjectDrawSettings(MapObjectDrawSettings pDrawSettings) throws MapObjectStyleIsNullException
	{
		if (pDrawSettings == null)
		{
			throw new MapObjectStyleIsNullException(this);
		}

		styles.add(pDrawSettings);
	}

	/**
	 * Remove draw settings by index
	 *
	 * @param pIndex style index
	 * @throws StyleIndexOutOfBoundsException index out of bounds
	 */
	@Override
	public void removeMapObjectDrawSettings(Integer pIndex) throws StyleIndexOutOfBoundsException
	{
		if (pIndex == null)
		{
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());
		}

		if (pIndex < 0 || pIndex >= styles.size())
		{
			throw new StyleIndexOutOfBoundsException(this, pIndex, 0, styles.size());
		}

		styles.remove((int) pIndex);
	}

	/**
	 * Get map drawing settings
	 *
	 * @return map drawing settings
	 */
	@Override
	public MapDrawSettings getMapDrawSettings()
	{
		return mapDrawSettings;
	}

	/**
	 * Set new map drawing settings
	 *
	 * @param pMapDrawingSettings new map drawing settings
	 * @throws MapDrawSettingsIsNullException new settins is null
	 */
	@Override
	public void setMapDrawSettings(MapDrawSettings pMapDrawingSettings) throws MapDrawSettingsIsNullException
	{
		if (pMapDrawingSettings == null)
		{
			throw new MapDrawSettingsIsNullException(this);
		}

		mapDrawSettings = pMapDrawingSettings;
	}

	/**
	 * Find map object drawing style by index
	 *
	 * @param pIndex index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	@Override
	public MapObjectDrawStyle findMapObjectDrawStyle(Integer pIndex)
	{
		return getMapObjectDrawSettings(pIndex);
	}
}
