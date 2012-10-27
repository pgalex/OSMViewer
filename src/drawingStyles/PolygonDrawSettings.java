package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
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
	 * Create with parameters
	 *
	 * @param polygonFiller filler for polygon
	 * @param polygonBorderDrawSettings how to draw border of polygon
	 * @throws IllegalArgumentException polygonFiller or polygonBorderDrawSettings
	 * is null
	 */
	public PolygonDrawSettings(PolygonFiller polygonFiller,
					LineDrawSettings polygonBorderDrawSettings) throws IllegalArgumentException
	{
		if (polygonFiller == null)
		{
			throw new IllegalArgumentException();
		}
		if (polygonBorderDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		filler = polygonFiller;
		borderDrawSettings = polygonBorderDrawSettings;
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
	 * Get filler
	 *
	 * @return filler
	 */
	public PolygonFiller getFiller()
	{
		return filler;
	}

	/**
	 * Get paint for drawing filled polygon
	 *
	 * @return paint for drawing polygon
	 */
	public Paint getPaint()
	{
		return filler.getPaint();
	}
}
