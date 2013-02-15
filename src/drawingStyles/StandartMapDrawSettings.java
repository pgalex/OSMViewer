package drawingStyles;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import rendering.RenderableMapDrawSettings;

/**
 * Contains information about map drawing (whole map, not of any object)
 *
 * @author pgalex
 */
public class StandartMapDrawSettings implements RenderableMapDrawSettings
{
	/**
	 * Default map background color
	 */
	private static final Color DEFAULT_MAP_BACKGROUND_COLOR = new Color(242, 239, 233);
	/**
	 * Map background color
	 */
	private ColorWithIO mapBackgroundColor;

	/**
	 * Create with default values
	 */
	public StandartMapDrawSettings()
	{
		mapBackgroundColor = new ColorWithIO(DEFAULT_MAP_BACKGROUND_COLOR);
	}

	/**
	 * Create with parameters
	 *
	 * @param backgroundColor map background color. Must be not null
	 * @throws IllegalArgumentException background color is incorrect
	 */
	public StandartMapDrawSettings(Color backgroundColor) throws IllegalArgumentException
	{
		if (backgroundColor == null)
		{
			throw new IllegalArgumentException();
		}

		mapBackgroundColor = new ColorWithIO(backgroundColor);
	}

	/**
	 * Get map background color
	 *
	 * @return map background color
	 */
	@Override
	public Color getMapBackgroundColor()
	{
		return mapBackgroundColor.getStoringColor();
	}

	/**
	 * Set new map background color
	 *
	 * @param backgroundColorToSet new map background color
	 * @throws IllegalArgumentException new map background color is incorrect
	 */
	public void setMapBackgroundColor(Color backgroundColorToSet) throws IllegalArgumentException
	{
		if (backgroundColorToSet == null)
		{
			throw new IllegalArgumentException();
		}

		mapBackgroundColor.setStoringColor(backgroundColorToSet);
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
			mapBackgroundColor.readFromStream(input);
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
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
			mapBackgroundColor.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}
}
