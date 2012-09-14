package drawingStyles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
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
		catch (Exception ex)
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
	 * Read from file
	 *
	 * @param fileForReading file to read
	 * @throws IOException reading error
	 */
	@Override
	public void readFromFile(File fileForReading) throws IOException
	{
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(fileForReading));
			readFromStream(input);
			input.close();
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
	 * @throws IllegalArgumentException new draw settings is null, style index
	 * is out of bounds
	 */
	@Override
	public void setMapObjectDrawSettings(Integer index, MapObjectDrawSettings drawSettingsToSet) throws IllegalArgumentException
	{
		if (index == null)
		{
			throw new IllegalArgumentException();
		}
		if (index < 0 || index >= styles.size())
		{
			throw new IllegalArgumentException();
		}
		if (drawSettingsToSet == null)
		{
			throw new IllegalArgumentException();
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
	 * @throws IllegalArgumentException new style is null
	 */
	@Override
	public void addMapObjectDrawSettings(MapObjectDrawSettings drawSettingsToAdd) throws IllegalArgumentException
	{
		if (drawSettingsToAdd == null)
		{
			throw new IllegalArgumentException();
		}

		styles.add(drawSettingsToAdd);
	}

	/**
	 * Remove draw settings by index
	 *
	 * @param index style index
	 * @throws IllegalArgumentException index out of bounds
	 */
	@Override
	public void removeMapObjectDrawSettings(Integer index) throws IllegalArgumentException
	{
		if (index == null)
		{
			throw new IllegalArgumentException();
		}

		if (index < 0 || index >= styles.size())
		{
			throw new IllegalArgumentException();
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
