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
	 * Filling color
	 */
	private ColorWithIO fillColor;
	/**
	 * How to draw border
	 */
	private LineDrawSettings borderDrawSettings;
	/**
	 * Filling texture if null, using color
	 */
	private ImageWithIO fillImage;

	/**
	 * Create with default values
	 */
	public PolygonDrawSettings()
	{
		fillColor = new ColorWithIO();
		borderDrawSettings = new LineDrawSettings();
		fillImage = new ImageWithIO();
	}

	/**
	 * Create with parameters
	 *
	 * @param polygonFillColor fill color
	 * @param polygonBorderDrawSettings how to draw border of polygon
	 * @param polygonFillImage fill texture. Can be null
	 * @throws IllegalArgumentException polygonFillColor or
	 * polygonBorderDrawSettings is null
	 */
	public PolygonDrawSettings(Color polygonFillColor, LineDrawSettings polygonBorderDrawSettings,
					BufferedImage polygonFillImage) throws IllegalArgumentException
	{
		if (polygonFillColor == null)
		{
			throw new IllegalArgumentException();
		}
		if (polygonBorderDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		fillColor = new ColorWithIO(polygonFillColor);
		borderDrawSettings = polygonBorderDrawSettings;
		fillImage = new ImageWithIO(polygonFillImage);
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
			fillColor.readFromStream(input);
			fillImage.readFromStream(input);
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
			fillColor.writeToStream(output);
			fillImage.writeToStream(output);
			borderDrawSettings.writeToStream(output);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Get fill color
	 *
	 * @return fill color
	 */
	public Color getFillColor()
	{
		return fillColor.getStoringColor();
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
	 * Get fill texture
	 *
	 * @return fill texture
	 */
	public BufferedImage getFillImage()
	{
		return fillImage.getImage();
	}

	/**
	 * Get paint for drawing filled polygon
	 *
	 * @return paint for drawing polygon
	 */
	public Paint getPaint()
	{
		if (getFillImage() != null)
		{
			return new TexturePaint(getFillImage(),
							new Rectangle(0, 0, getFillImage().getWidth(), getFillImage().getHeight()));
		}
		else
		{
			return getFillColor();
		}
	}
}
