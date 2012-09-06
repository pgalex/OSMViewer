package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Contains information about map drawing (whole map, not of any object)
 *
 * @author pgalex
 */
public class MapDrawSettings implements ReadableMapData, WritableMapData
{
	/**
	 * Default map background color
	 */
	private static final IOColor DEFAULT_MAP_BACKGROUND_COLOR = new IOColor(new Color(242, 239, 233));
	/**
	 * Map background color
	 */
	private IOColor mapBackgroundColor;

	/**
	 * Create with parameters
	 *
	 * @param backgroundColor map background color. Resetting to default if null
	 */
	public MapDrawSettings(Color backgroundColor)
	{
		mapBackgroundColor = new IOColor(backgroundColor);
	}

	/**
	 * Get map background color
	 *
	 * @return map background color
	 */
	public IOColor getMapBackgroundColor()
	{
		return mapBackgroundColor;
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
			mapBackgroundColor.readFromStream(input);
		}
		catch (Exception ex)
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
			mapBackgroundColor.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}
