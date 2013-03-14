package com.osmviewer.drawingStyles;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import com.osmviewer.rendering.RenderableMapLineDrawSettings;

/**
 * How to draw line (non closed way)
 *
 * @author abc
 */
public class StandartLineDrawSettings implements RenderableMapLineDrawSettings
{
	/**
	 * Default line width
	 */
	private static final float DEFAULT_WIDTH = 1.0f;
	/**
	 * Line color
	 */
	private ColorWithIO color;
	/**
	 * Line width
	 */
	private float width;
	/**
	 * Line pattern (dash, dot etc )
	 */
	private LinePattern pattern;

	/**
	 * Create with default values
	 */
	public StandartLineDrawSettings()
	{
		color = new ColorWithIO();
		width = DEFAULT_WIDTH;
		pattern = new LinePattern();
	}

	/**
	 * Set new line color
	 *
	 * @param colorToSet new line color
	 * @throws IllegalArgumentException colorToSet is null
	 */
	public void setColor(Color colorToSet) throws IllegalArgumentException
	{
		if (colorToSet == null)
		{
			throw new IllegalArgumentException("colorToSet is null");
		}

		color.setStoringColor(colorToSet);
	}

	/**
	 * Get line color
	 *
	 * @return line color
	 */
	@Override
	public Color getColor()
	{
		return color.getStoringColor();
	}

	/**
	 * Get line width
	 *
	 * @return line width
	 */
	@Override
	public float getWidth()
	{
		return width;
	}

	/**
	 * Set new line width
	 *
	 * @param widthToSet new line width
	 */
	public void setWidth(float widthToSet)
	{
		width = widthToSet;
	}

	/**
	 * Get line pattern
	 *
	 * @return pattern
	 */
	public float[] getPattern()
	{
		return pattern.getPattern();
	}

	/**
	 * Set new line pattern
	 *
	 * @param patternToSet new line pattern
	 * @throws IllegalArgumentException patternToSet is null or empty
	 */
	public void setPattern(float[] patternToSet) throws IllegalArgumentException
	{
		if (patternToSet == null)
		{
			throw new IllegalArgumentException("patternToSet is null");
		}
		if (patternToSet.length == 0)
		{
			throw new IllegalArgumentException("patternToSet is empty");
		}

		pattern.setPattern(patternToSet);
	}

	/**
	 * Get stroke for line drawing
	 *
	 * @return stroke for line drawing
	 */
	@Override
	public BasicStroke getStroke()
	{
		return new BasicStroke(getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
						1.0f, getPattern(), 0.0f);
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
			color.readFromStream(input);
			width = input.readFloat();
			pattern.readFromStream(input);
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
			color.writeToStream(output);
			output.writeFloat(width);
			pattern.writeToStream(output);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
