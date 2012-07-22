package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import drawingStyles.exceptions.LinePatternIncorrectException;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to draw line (non closed way in osm)
 *
 * @author abc
 */
public class LineDrawSettings implements LineDrawStyle, ReadableMapData, WritableMapData
{
	/**
	 * Default line width
	 */
	private static final int DEFAULT_WIDTH = 1;
	/**
	 * Color
	 */
	private IOColor color;
	/**
	 * Width
	 */
	private int width;
	/**
	 * User defined pattern (dash, dot etc )
	 */
	private LinePattern pattern;

	/**
	 * Default constructor
	 */
	public LineDrawSettings()
	{
		color = new IOColor();
		width = DEFAULT_WIDTH;
		pattern = new LinePattern();
	}

	/**
	 * Constrcutor
	 *
	 * @param pColor color
	 * @param pWidth width
	 * @param pPattern pattern. Autocreating if null
	 */
	public LineDrawSettings(Color pColor, int pWidth, LinePattern pPattern)
	{
		color = new IOColor(pColor);
		width = pWidth;
		pattern = pPattern;

		initializeNullFields();
	}

	/**
	 * Autocreate null fields
	 */
	private void initializeNullFields()
	{
		if (pattern == null)
			pattern = new LinePattern();
	}

	/**
	 * Read from stream
	 *
	 * @param pInput reading stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			color.readFromStream(pInput);
			width = pInput.readInt();
			pattern.readFromStream(pInput);
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
			color.writeToStream(pOutput);
			pOutput.writeInt(width);
			pattern.writeToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Get line color
	 *
	 * @return line color
	 */
	@Override
	public Color getColor()
	{
		return color.getColor();
	}

	/**
	 * Set new color
	 * @param pColor new color
	 */
	public void setColor(Color pColor)
	{
		color = new IOColor(pColor);
	}

	/**
	 * Get line width
	 *
	 * @return line width
	 */
	@Override
	public int getWidth()
	{
		return width;
	}
	
	/**
	 * Set new width
	 * @param pWidth new width
	 */
	public void setWidth(int pWidth)
	{
		width = pWidth;
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
	 * @param pPattern new pattern
	 * @throws LinePatternIncorrectException new pattern icorrect
	 */
	public void setPattern(float[] pPattern) throws LinePatternIncorrectException
	{
		pattern.setPattern(pPattern);
	}
}
