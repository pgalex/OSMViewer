package com.osmviewer.drawingStyles;

import java.awt.Color;
import java.awt.Paint;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import com.osmviewer.rendering.RenderableMapLineDrawSettings;
import com.osmviewer.rendering.RenderableMapPolygonDrawSettings;

/**
 * How to draw polygon (closed way)
 *
 * @author abc
 */
public class StandartPolygonDrawSettings implements RenderableMapPolygonDrawSettings
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
	private StandartLineDrawSettings borderDrawSettings;
	/**
	 * Is need to draw border of polygon
	 */
	private boolean needDrawBorder;

	/**
	 * Create with default values
	 */
	public StandartPolygonDrawSettings()
	{
		filler = DEFAULT_FILLER;
		borderDrawSettings = new StandartLineDrawSettings();
		needDrawInnerPart = true;
		needDrawBorder = false;
	}

	/**
	 * Get border drawing settings
	 *
	 * @return how to draw border of polygon
	 */
	public StandartLineDrawSettings getBorderDrawSettings()
	{
		return borderDrawSettings;
	}

	/**
	 * Set new border draw settings (how to draw border of polygon)
	 *
	 * @param settingsToSet new border draw settings
	 * @throws IllegalArgumentException settingsToSet is null
	 */
	public void setBorderDrawSettings(StandartLineDrawSettings settingsToSet) throws IllegalArgumentException
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
	@Override
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
	@Override
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
	@Override
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

	/**
	 * Find border drawing settings
	 *
	 * @return draw settings of polygon border
	 */
	@Override
	public RenderableMapLineDrawSettings findBorderDrawSettings()
	{
		return borderDrawSettings;
	}
}
