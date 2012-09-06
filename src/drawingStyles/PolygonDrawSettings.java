package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import drawingStyles.exceptions.ColorIsNullException;
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
	 * Default constructor
	 */
	public PolygonDrawSettings()
	{
		fillColor = new IOColor();
		borderDrawStyle = new LineDrawSettings();
		fillImage = new IOIcon();
	}

	/**
	 * Constrcutor
	 *
	 * @param pFillColor fill color
	 * @param pBorderDrawStyle how to draw border of polygon
	 * @param pFillImage fill texture. Can be null
	 * @throws ColorIsNullException fill color is null 
	 */
	public PolygonDrawSettings(Color pFillColor, LineDrawSettings pBorderDrawStyle, BufferedImage pFillImage) throws ColorIsNullException
	{
		fillColor = new IOColor(pFillColor);
		borderDrawStyle = pBorderDrawStyle;
		fillImage = new IOIcon(pFillImage);

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
	 * @param pInput input stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			fillColor.readFromStream(pInput);
			borderDrawStyle.readFromStream(pInput);
			fillImage.readFromStream(pInput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
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
			fillColor.writeToStream(pOutput);
			borderDrawStyle.writeToStream(pOutput);
			fillImage.writeToStream(pOutput);
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
		return fillColor.getColor();
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
