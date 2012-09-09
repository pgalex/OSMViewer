package drawingStyles;

import drawingStyles.exceptions.MapDrawSettingsIsNullException;
import drawingStyles.exceptions.MapObjectDrawSettingsIsNullException;
import drawingStyles.exceptions.StyleIndexOutOfBoundsException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
	 * Create with default values
	 */
	public MapObjectDrawStylesEditor()
	{
		super();

		styles = new ArrayList<MapObjectDrawSettings>();
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

			writeStylesToStream(styles.toArray(new MapObjectDrawSettings[styles.size()]), output);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Write to file
	 *
	 * @param fileForWriting file, using for writing
	 * @throws IOException writing error 
	 */
	@Override
	public void writeToFile(File fileForWriting) throws IOException
	{
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(fileForWriting));
			writeToStream(output);
			output.close();
		}
		catch(Exception ex)
		{
			throw new IOException();
		}
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

			styles.clear();
			MapObjectDrawSettings[] readingStyles = readStylesFromStream(input);
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
	 * @param index style index
	 * @param drawSettingsToSet new draw settings of object with index
	 * @throws StyleIndexOutOfBoundsException style index is out of bounds
	 * @throws MapObjectDrawSettingsIsNullException new draw settings is null
	 */
	@Override
	public void setMapObjectDrawSettings(Integer index, MapObjectDrawSettings drawSettingsToSet) throws StyleIndexOutOfBoundsException, MapObjectDrawSettingsIsNullException
	{
		if (index == null)
		{
			throw new StyleIndexOutOfBoundsException(index, 0, styles.size());
		}
		if (index < 0 || index >= styles.size())
		{
			throw new StyleIndexOutOfBoundsException(index, 0, styles.size());
		}
		if (drawSettingsToSet == null)
		{
			throw new MapObjectDrawSettingsIsNullException();
		}

		styles.set(index, drawSettingsToSet);
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
	 * @param objectDefenitionTags tags of map object
	 * @return id of style of object with that defenition tags. null if not found
	 */
	@Override
	public Integer findStyleIndex(DefenitionTags objectDefenitionTags)
	{
		if (objectDefenitionTags == null)
		{
			return null;
		}

		return findStyleIndex(styles.toArray(new MapObjectDrawSettings[styles.size()]), objectDefenitionTags);
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
		if (index < 0 || index >= styles.size())
		{
			return null;
		}

		return styles.get(index);
	}

	/**
	 * Add draw settings of map object
	 *
	 * @param drawSettingsToAdd new map object style
	 * @throws MapObjectDrawSettingsIsNullException new style is null
	 */
	@Override
	public void addMapObjectDrawSettings(MapObjectDrawSettings drawSettingsToAdd) throws MapObjectDrawSettingsIsNullException
	{
		if (drawSettingsToAdd == null)
		{
			throw new MapObjectDrawSettingsIsNullException();
		}

		styles.add(drawSettingsToAdd);
	}

	/**
	 * Remove draw settings by index
	 *
	 * @param index style index
	 * @throws StyleIndexOutOfBoundsException index out of bounds
	 */
	@Override
	public void removeMapObjectDrawSettings(Integer index) throws StyleIndexOutOfBoundsException
	{
		if (index == null)
		{
			throw new StyleIndexOutOfBoundsException(index, 0, styles.size());
		}

		if (index < 0 || index >= styles.size())
		{
			throw new StyleIndexOutOfBoundsException(index, 0, styles.size());
		}

		styles.remove((int) index);
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
	 * @param mapDrawingSettingsToSet new map drawing settings
	 * @throws MapDrawSettingsIsNullException new settins is null
	 */
	@Override
	public void setMapDrawSettings(MapDrawSettings mapDrawingSettingsToSet) throws MapDrawSettingsIsNullException
	{
		if (mapDrawingSettingsToSet == null)
		{
			throw new MapDrawSettingsIsNullException();
		}

		mapDrawSettings = mapDrawingSettingsToSet;
	}

	/**
	 * Find map object drawing style by index
	 *
	 * @param index index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	@Override
	public MapObjectDrawStyle findMapObjectDrawStyle(Integer index)
	{
		return getMapObjectDrawSettings(index);
	}
}
