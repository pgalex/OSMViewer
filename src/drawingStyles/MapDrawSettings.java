package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import drawingStyles.exceptions.IncorrectParameterException;
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
	private static final Color DEFAULT_MAP_BACKGROUND_COLOR = new Color(242, 239, 233);
	/**
	 * Map background color
	 */
	private IOColor mapBackgroundColor;

	/**
	 * Create with default values
	 */
	public MapDrawSettings()
	{
		mapBackgroundColor = new IOColor(DEFAULT_MAP_BACKGROUND_COLOR);
	}

	/**
	 * Create with parameters
	 *
	 * @param backgroundColor map background color. Must be not null
	 * @throws IncorrectParameterException background color is incorrect
	 */
	public MapDrawSettings(Color backgroundColor) throws IncorrectParameterException
	{
		if (backgroundColor == null)
		{
			throw new IncorrectParameterException();
		}
		
		mapBackgroundColor = new IOColor(backgroundColor);
	}

	/**
	 * Get map background color
	 *
	 * @return map background color
	 */
	public Color getMapBackgroundColor()
	{
		return mapBackgroundColor.getStoringColor();
	}

	/**
	 * Set new map background color
	 *
	 * @param backgroundColorToSet new map background color
	 * @throws IncorrectParameterException new map background color is incorrect
	 */
	public void setMapBackgroundColor(Color backgroundColorToSet) throws IncorrectParameterException
	{
		if (backgroundColorToSet == null)
		{
			throw new IncorrectParameterException();
		}

		mapBackgroundColor.setStoringColor(backgroundColorToSet);
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
