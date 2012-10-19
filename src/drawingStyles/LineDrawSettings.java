package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.BasicStroke;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to draw line (non closed way)
 *
 * @author abc
 */
public class LineDrawSettings implements ReadableMapData, WritableMapData
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
	public LineDrawSettings()
	{
		color = new ColorWithIO();
		width = DEFAULT_WIDTH;
		pattern = new LinePattern();
	}

	/**
	 * Create with parameters
	 *
	 * @param lineColor line color
	 * @param lineWidth line width
	 * @param linePattern pattern of line
	 * @throws IllegalArgumentException lineColor is null or line pattern null or
	 * contains 0 elements
	 */
	public LineDrawSettings(Color lineColor, float lineWidth, float[] linePattern) throws IllegalArgumentException
	{
		if (lineColor == null)
		{
			throw new IllegalArgumentException();
		}
		if (linePattern == null)
		{
			throw new IllegalArgumentException();
		}
		if (linePattern.length == 0)
		{
			throw new IllegalArgumentException();
		}

		color = new ColorWithIO(lineColor);
		width = lineWidth;
		pattern = new LinePattern(linePattern);
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
	@Override
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

	/**
	 * Set new line color
	 *
	 * @param colorToSet new line color
	 */
	public void setColor(Color colorToSet)
	{
		color.setStoringColor(colorToSet);
	}

	/**
	 * Get line color
	 *
	 * @return line color
	 */
	public Color getColor()
	{
		return color.getStoringColor();
	}

	/**
	 * Get line width
	 *
	 * @return line width
	 */
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
	 */
	public void setPattern(float[] patternToSet)
	{
		pattern.setPattern(patternToSet);
	}

	/**
	 * Get stroke for line drawing
	 *
	 * @return stroke for line drawing
	 */
	public BasicStroke getStroke()
	{
		return new BasicStroke(getWidth(), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND,
						1.0f, getPattern(), 0.0f);
	}
}
