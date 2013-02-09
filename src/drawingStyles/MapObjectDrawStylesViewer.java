package drawingStyles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import mapDefenitionUtilities.DefenitionTags;

/**
 * Gives access to drawing style without editing but optimized for runtime map
 * drawing
 *
 * @author pgalex
 */
public class MapObjectDrawStylesViewer extends MapObjectDrawStylesContainer implements StyleViewer
{
	/**
	 * Static array. Work faster. All objectsDrawSettings sorted by tags count
	 */
	private MapObjectDrawSettings[] objectsDrawSettings;

	/**
	 * Create with default values
	 */
	public MapObjectDrawStylesViewer()
	{
		super();

		objectsDrawSettings = new MapObjectDrawSettings[0];
	}

	/**
	 * Find index of map object drawing style
	 *
	 * @param objectDefenitionTags tags of map object
	 * @return index of style of object with that defenition tags. null if not
	 * found
	 */
	@Override
	public Integer findStyleIndex(DefenitionTags objectDefenitionTags)
	{
		if (objectDefenitionTags == null)
		{
			return null;
		}

		return findStyleIndex(objectsDrawSettings, objectDefenitionTags);
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
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			super.readFromStream(input);

			objectsDrawSettings = readStylesFromStream(input);
		}
		catch (IOException ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			super.writeToStream(output);

			writeStylesToStream(objectsDrawSettings, output);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Get map object draw settings by index
	 *
	 * @param index index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	@Override
	public MapObjectDrawSettings getMapObjectDrawSettings(Integer index)
	{
		if (index == null)
		{
			return null;
		}
		if (index < 0 || index >= objectsDrawSettings.length)
		{
			return null;
		}

		return objectsDrawSettings[index];
	}

	/**
	 * Set draw settings of map object by index
	 *
	 * @param index style index
	 * @param drawSettingsToSet new draw settings of object with index
	 * @throws IllegalArgumentException new draw settings is null, style index is
	 * out of bounds
	 */
	@Override
	public void setMapObjectDrawSettings(Integer index, MapObjectDrawSettings drawSettingsToSet) throws IllegalArgumentException
	{
		if (index == null)
		{
			throw new IllegalArgumentException();
		}
		if (index < 0 || index >= objectsDrawSettings.length)
		{
			throw new IllegalArgumentException();
		}
		if (drawSettingsToSet == null)
		{
			throw new IllegalArgumentException();
		}

		objectsDrawSettings[index] = drawSettingsToSet;
	}

	/**
	 * Get count of storing map object draw setting
	 *
	 * @return count of storing map object draw setting
	 */
	@Override
	public int countOfMapObjectDrawSettings()
	{
		return objectsDrawSettings.length;
	}

	/**
	 * Set new map drawing settings
	 *
	 * @param mapDrawingSettingsToSet new map drawing settings
	 * @throws IllegalArgumentException new settins is null
	 */
	@Override
	public void setMapDrawSettings(MapDrawSettings mapDrawingSettingsToSet) throws IllegalArgumentException
	{
		if (mapDrawingSettingsToSet == null)
		{
			throw new IllegalArgumentException();
		}

		mapDrawSettings = mapDrawingSettingsToSet;
	}
}