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
	 * Is need to draw inner part of polygon
	 */
	private boolean needDrawInnerPart;
	/**
	 * How to draw border
	 */
	private LineDrawSettings borderDrawSettings;
	/**
	 * Is need to draw border of polygon
	 */
	private boolean needDrawBorder;

	/**
	 * Create with default values
	 */
	public PolygonDrawSettings()
	{
		filler = DEFAULT_FILLER;
		borderDrawSettings = new LineDrawSettings();
		needDrawInnerPart = true;
		needDrawBorder = false;
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
	 * Is need to draw inner part of polygon
	 *
	 * @return is need to draw inner part
	 */
	public boolean isDrawInnerPart()
	{
		return needDrawInnerPart;
	}

	/**
	 * Set need to draw inner part of polygon
	 */
	public void setDrawInnerPart()
	{
		needDrawInnerPart = true;
	}

	/**
	 * Set not draw inner part of polygon
	 */
	public void setNotDrawInnerPart()
	{
		needDrawInnerPart = false;
	}

	/**
	 * Is need to draw border of polgyon
	 *
	 * @return is need to draw border
	 */
	public boolean isDrawBorder()
	{
		return needDrawBorder;
	}

	/**
	 * Set need to draw border of polygon
	 */
	public void setDrawBorder()
	{
		needDrawBorder = true;
	}

	/**
	 * Set not draw border of polygon
	 */
	public void setNotDrawBorder()
	{
		needDrawBorder = false;
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
			needDrawInnerPart = input.readBoolean();
			needDrawBorder = input.readBoolean();
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
			output.writeBoolean(needDrawInnerPart);
			output.writeBoolean(needDrawBorder);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
