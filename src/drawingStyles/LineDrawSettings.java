package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import drawingStyles.exceptions.IncorrectParameterException;
import drawingStyles.exceptions.LinePatternIncorrectException;
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
public class LineDrawSettings implements LineDrawStyle, ReadableMapData, WritableMapData
{
	/**
	 * Default line width
	 */
	private static final float DEFAULT_WIDTH = 1.0f;
	/**
	 * Line color
	 */
	private IOColor color;
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
		color = new IOColor();
		width = DEFAULT_WIDTH;
		pattern = new LinePattern();
	}

	/**
	 * Create with parameters
	 *
	 * @param lineColor line color
	 * @param lineWidth line width
	 * @param linePattern pattern of line
	 * @throws LinePatternIncorrectException pattern of line is incorrect
	 * @throws IncorrectParameterException line color is null
	 */
	public LineDrawSettings(Color lineColor, float lineWidth, float[] linePattern) throws LinePatternIncorrectException,
					IncorrectParameterException
	{
		color = new IOColor(lineColor);
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
	 * @throws IncorrectParameterException new line color is null
	 */
	public void setColor(Color colorToSet) throws IncorrectParameterException
	{
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
	@Override
	public float[] getPattern()
	{
		return pattern.getPattern();
	}

	/**
	 * Set new line pattern
	 *
	 * @param patternToSet new line pattern
	 * @throws LinePatternIncorrectException new pattern incorrect
	 */
	public void setPattern(float[] patternToSet) throws LinePatternIncorrectException
	{
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
}
