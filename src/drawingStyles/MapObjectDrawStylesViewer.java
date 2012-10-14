package drawingStyles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Gives access to drawing style without editing but optimized for runtime map
 * drawing
 *
 * @author pgalex
 */
public class MapObjectDrawStylesViewer extends MapObjectDrawStylesContainer implements StyleViewer
{
	/**
	 * Static array. Work faster. All styles sorted by tags count
	 */
	private MapObjectDrawSettings[] styles;

	/**
	 * Create with default values
	 */
	public MapObjectDrawStylesViewer()
	{
		super();

		styles = new MapObjectDrawSettings[0];
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

		return findStyleIndex(styles, objectDefenitionTags);
	}

	/**
	 * Find map object drawing style by index
	 *
	 * @param index index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	@Override
	public MapObjectDrawSettings findMapObjectDrawStyle(Integer index)
	{
		if (index == null)
		{
			return null;
		}
		if (index < 0 || index >= styles.length)
		{
			return null;
		}

		return styles[index];
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

			styles = readStylesFromStream(input);
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

			writeStylesToStream(styles, output);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}