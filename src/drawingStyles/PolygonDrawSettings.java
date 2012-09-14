package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import drawingStyles.exceptions.IncorrectParameterException;
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
public class PolygonDrawSettings implements PolygonDrawStyle, ReadableMapData, WritableMapData
{
	/**
	 * Filling color
	 */
	private IOColor fillColor;
	/**
	 * How to draw border
	 */
	private LineDrawSettings borderDrawStyle;
	/**
	 * Filling texture if null, using color
	 */
	private IOIcon fillImage;

	/**
	 * Create with default values
	 */
	public PolygonDrawSettings()
	{
		fillColor = new IOColor();
		borderDrawStyle = new LineDrawSettings();
		fillImage = new IOIcon();
	}

	/**
	 * Create with parameters
	 *
	 * @param polygonFillColor fill color
	 * @param polygonBorderDrawSettings how to draw border of polygon
	 * @param polygonFillImage fill texture. Can be null
	 * @throws IncorrectParameterException fill color is null 
	 */
	public PolygonDrawSettings(Color polygonFillColor, LineDrawSettings polygonBorderDrawSettings, 
					BufferedImage polygonFillImage) throws IncorrectParameterException
	{
		fillColor = new IOColor(polygonFillColor);
		borderDrawStyle = polygonBorderDrawSettings;
		fillImage = new IOIcon(polygonFillImage);

		initializeNullFields();
	}

	/**
	 * Auto initialize null fields
	 */
	private void initializeNullFields()
	{
		if (borderDrawStyle == null)
		{
			borderDrawStyle = new LineDrawSettings();
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
			fillColor.readFromStream(input);
			fillImage.readFromStream(input);
			borderDrawStyle.readFromStream(input);
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
			borderDrawStyle.writeToStream(output);
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
	@Override
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
		return borderDrawStyle;
	}

	/**
	 * Get border drawing style
	 *
	 * @return how to draw border of polygon
	 */
	@Override
	public LineDrawStyle getBorderDrawStyle()
	{
		return borderDrawStyle;
	}

	/**
	 * Get fill texture
	 *
	 * @return fill texture
	 */
	@Override
	public BufferedImage getFillImage()
	{
		return fillImage.getImage();
	}

	/**
	 * Get paint for drawing filled polygon
	 *
	 * @return paint for drawing polygon
	 */
	@Override
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
