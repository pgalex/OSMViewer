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
public class MapDrawingSettings implements ReadableMapData, WritableMapData
{
	/**
	 * Default map background color
	 */
	private static final IOColor DEFAULT_MAP_BACKGROUND_COLOR = new IOColor(Color.GRAY);
	/**
	 * Map background color
	 */
	private IOColor mapBackgroundColor;

	/**
	 * Constructor
	 *
	 * @param pMapBackgroundColor Map background color
	 */
	public MapDrawingSettings(IOColor pMapBackgroundColor)
	{
		mapBackgroundColor = pMapBackgroundColor;

		initializeNullFields();
	}

	/**
	 * Get current default scale levels count
	 *
	 * @return current default scale levels count
	 */
	public static int getScaleLevelsCount()
	{
		return ScaledObjectStyleArray.DEFAULT_SCALE_LEVELS_COUNT;
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
	 * Auto initialize null fields with default values
	 */
	private void initializeNullFields()
	{
		if (mapBackgroundColor == null)
			mapBackgroundColor = DEFAULT_MAP_BACKGROUND_COLOR;
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
			mapBackgroundColor.readFromStream(pInput);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
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
			mapBackgroundColor.writeToStream(pOutput);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}
