package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to draw line ( non closed way in osm )
 *
 * @author abc
 */
public class LineDrawStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Solid line pattern
	 */
	public static final float[] SOLID_LINE_PATTERN =
	{
		1
	};
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
	 * User defined pattern ( dash, dot etc )
	 */
	private float[] pattern;

	/**
	 * Default constructor
	 */
	public LineDrawStyle()
	{
		color = new IOColor();
		width = DEFAULT_WIDTH;
		pattern = SOLID_LINE_PATTERN;
	}

	/**
	 * Constrcutor
	 *
	 * @param pColor color. Autocreating if null
	 * @param pWidth width
	 * @param pPattern pattern. Autocreating if null
	 */
	public LineDrawStyle(IOColor pColor, int pWidth, float[] pPattern)
	{
		color = pColor;
		width = pWidth;
		pattern = pPattern;
		InitializeNullFields();
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

			int patternCount = pInput.readInt();
			pattern = new float[patternCount];
			for (int i = 0; i < patternCount; i++)
				pattern[i] = pInput.readFloat();
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

			pOutput.writeInt(pattern.length);
			for (int i = 0; i < pattern.length; i++)
				pOutput.writeFloat(pattern[i]);
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
	public IOColor getColor()
	{
		return color;
	}

	/**
	 * Get line width
	 *
	 * @return line width
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Get line pattern
	 *
	 * @return pattern
	 */
	public float[] getPattern()
	{
		return pattern;
	}

	/**
	 * Autocreate null fields
	 */
	private void InitializeNullFields()
	{
		if (color == null)
			color = new IOColor();
		if (pattern == null)
			pattern = SOLID_LINE_PATTERN;
		if (pattern.length == 0)
			pattern = SOLID_LINE_PATTERN;
	}
}
