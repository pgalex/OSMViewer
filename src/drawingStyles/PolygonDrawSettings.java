package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.Color;
import java.awt.Paint;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to draw polygon (closed way)
 *
 * @author abc
 */
public class PolygonDrawSettings implements ReadableMapData, WritableMapData
{
	/**
	 * Default polygon filler
	 */
	private static PolygonFiller DEFAULT_FILLER = PolygonFillersFactory.createColorFiller(Color.BLACK);
	/**
	 * How to fill inside part of polygon
	 */
	private PolygonFiller filler;
	/**
	 * How to draw border
	 */
	private LineDrawSettings borderDrawSettings;

	/**
	 * Create with default values
	 */
	public PolygonDrawSettings()
	{
		filler = DEFAULT_FILLER;
		borderDrawSettings = new LineDrawSettings();
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
			filler = PolygonFillersFactory.readFillerFromStream(input);
			borderDrawSettings.readFromStream(input);
		}
		catch (Exception e)
		{
			throw new IOException(e);
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
			PolygonFillersFactory.writeFillerToSream(filler, output);
			borderDrawSettings.writeToStream(output);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Get border drawing settings
	 *
	 * @return how to draw border of polygon
	 */
	public LineDrawSettings getBorderDrawSettings()
	{
		return borderDrawSettings;
	}

	/**
	 * Set new border draw settings (how to draw border of polygon)
	 *
	 * @param settingsToSet new border draw settings
	 * @throws IllegalArgumentException settingsToSet is null
	 */
	public void setBorderDrawSettings(LineDrawSettings settingsToSet) throws IllegalArgumentException
	{
		if (settingsToSet == null)
		{
			throw new IllegalArgumentException();
		}

		borderDrawSettings = settingsToSet;
	}

	/**
	 * Get filler
	 *
	 * @return filler
	 */
	public PolygonFiller getFiller()
	{
		return filler;
	}

	/**
	 * Set new filler (how to full inner part of polygon)
	 *
	 * @param fillerToSet new filler
	 * @throws IllegalArgumentException fillerToSet is null
	 */
	public void setFiller(PolygonFiller fillerToSet) throws IllegalArgumentException
	{
		if (fillerToSet == null)
		{
			throw new IllegalArgumentException();
		}

		filler = fillerToSet;
	}

	/**
	 * Get paint for drawing inner part of polygon
	 *
	 * @return paint for drawing polygon
	 */
	public Paint getPaint()
	{
		return filler.getPaint();
	}
}
