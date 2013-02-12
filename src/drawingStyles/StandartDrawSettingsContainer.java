package drawingStyles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import mapDefenitionUtilities.DefenitionTags;
import rendering.RenderableMapObjectDrawSettings;

/**
 * Gives access to drawing style with editing
 *
 * @author pgalex
 */
public class StandartDrawSettingsContainer implements DrawSettingsViewer
{
	/**
	 * Common draw settings of map
	 */
	private StandartMapDrawSettings mapDrawSettings;
	/**
	 * Array of container's map object draw settings
	 */
	private ArrayList<StandartMapObjectDrawSettings> mapObjectsDrawSettings;

	/**
	 * Create with empty map objects draw settings, and other default values
	 */
	public StandartDrawSettingsContainer()
	{
		mapObjectsDrawSettings = new ArrayList<StandartMapObjectDrawSettings>();
		mapDrawSettings = new StandartMapDrawSettings();
	}

	/**
	 * Get count of storing map object draw setting
	 *
	 * @return count of storing map object draw setting
	 */
	public int countOfMapObjectDrawSettings()
	{
		return mapObjectsDrawSettings.size();
	}

	/**
	 * Find draw settings of map object, by its defention tags
	 *
	 * @param mapObjectDefenitionTags tags of map object, which draw settings need
	 * to find
	 * @return draw settings of map object. null if not found
	 */
	@Override
	public RenderableMapObjectDrawSettings findMapObjectDrawSettings(DefenitionTags mapObjectDefenitionTags)
	{
		if (mapObjectDefenitionTags == null)
		{
			return null;
		}

		ArrayList<StandartMapObjectDrawSettings> suitableDrawSettings = new ArrayList<StandartMapObjectDrawSettings>();

		for (StandartMapObjectDrawSettings drawSettings : mapObjectsDrawSettings)
		{
			if (drawSettings.getDefenitionTags().includingIn(mapObjectDefenitionTags))
			{
				suitableDrawSettings.add(drawSettings);
			}
		}

		Collections.sort(suitableDrawSettings, new MapObjectDrawSettingsTagsCountComparator());

		if (!suitableDrawSettings.isEmpty())
		{
			return suitableDrawSettings.get(0);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Get map object draw settings by index
	 *
	 * @param index index of map object draw settings
	 * @return map object drawing settings
	 * @throws IllegalArgumentException index is out of bounds
	 */
	public StandartMapObjectDrawSettings getMapObjectDrawSettings(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= mapObjectsDrawSettings.size())
		{
			throw new IllegalArgumentException();
		}

		return mapObjectsDrawSettings.get(index);
	}

	/**
	 * Add map object draw settings
	 *
	 * @param drawSettingsToAdd adding map object draw settings
	 * @throws IllegalArgumentException drawSettingsToAdd is null
	 */
	public void addMapObjectDrawSettings(StandartMapObjectDrawSettings drawSettingsToAdd) throws IllegalArgumentException
	{
		if (drawSettingsToAdd == null)
		{
			throw new IllegalArgumentException();
		}

		mapObjectsDrawSettings.add(drawSettingsToAdd);
	}

	/**
	 * Remove map object draw settings by index
	 *
	 * @param index index of map object draw settings
	 * @throws IllegalArgumentException index out of bounds
	 */
	public void removeMapObjectDrawSettings(int index) throws IllegalArgumentException
	{
		if (index < 0 || index >= mapObjectsDrawSettings.size())
		{
			throw new IllegalArgumentException();
		}

		mapObjectsDrawSettings.remove(index);
	}

	/**
	 * Get map drawing settings
	 *
	 * @return map drawing settings
	 */
	@Override
	public StandartMapDrawSettings getMapDrawSettings()
	{
		return mapDrawSettings;
	}

	/**
	 * Set new map drawing settings
	 *
	 * @param mapDrawingSettingsToSet new map drawing settings
	 * @throws IllegalArgumentException new settins is null
	 */
	public void setMapDrawSettings(StandartMapDrawSettings mapDrawingSettingsToSet) throws IllegalArgumentException
	{
		if (mapDrawingSettingsToSet == null)
		{
			throw new IllegalArgumentException();
		}

		mapDrawSettings = mapDrawingSettingsToSet;
	}

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			mapDrawSettings.writeToStream(output);

			output.writeInt(mapObjectsDrawSettings.size());
			for (int i = 0; i < mapObjectsDrawSettings.size(); i++)
			{
				mapObjectsDrawSettings.get(i).writeToStream(output);
			}
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}

	/**
	 * Write to file
	 *
	 * @param fileForWriting file, using for writing
	 * @throws IOException writing error
	 */
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
			throw new IOException(ex);
		}
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			mapDrawSettings.readFromStream(input);

			int drawSettingsCount = input.readInt();
			mapObjectsDrawSettings = new ArrayList<StandartMapObjectDrawSettings>(drawSettingsCount);
			for (int i = 0; i < drawSettingsCount; i++)
			{
				StandartMapObjectDrawSettings readDrawSettings = new StandartMapObjectDrawSettings();
				readDrawSettings.readFromStream(input);
				mapObjectsDrawSettings.add(readDrawSettings);
			}
		}
		catch (IOException ex)
		{
			throw new IOException(ex);
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
			throw new IOException(ex);
		}
	}
}
