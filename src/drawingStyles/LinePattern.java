package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Line drawing pattern (dash, dot etc)
 *
 * @author pgalex
 */
public class LinePattern implements ReadableMapData, WritableMapData
{
	/**
	 * Solid line pattern
	 */
	private static final float[] SOLID_LINE_PATTERN =
	{
		1
	};
	/**
	 * Array that contains patern. 1-draw, 0-no
	 */
	private float[] pattern;

	/**
	 * Create pattern for solid line
	 */
	public LinePattern()
	{
		pattern = SOLID_LINE_PATTERN;
	}

	/**
	 * Create line pattern
	 *
	 * @param linePattern pattern of line
	 * @throws IllegalArgumentException linePattern is null, or contains 0
	 * elements
	 */
	public LinePattern(float[] linePattern) throws IllegalArgumentException
	{
		if (isLinePatternIncorrect(linePattern))
		{
			throw new IllegalArgumentException();
		}

		pattern = linePattern;
	}

	/**
	 * Is line pattern array incorrect?
	 *
	 * @return Is line pattern array incorrect
	 */
	private boolean isLinePatternIncorrect(float[] linePattern)
	{
		if (linePattern == null || linePattern.length == 0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Get line drawing pattern
	 *
	 * @return line drawing pattern
	 */
	public float[] getPattern()
	{
		return pattern;
	}

	/**
	 * Set new pattern
	 *
	 * @param patternToSet new pattern
	 * @throws IllegalArgumentException new pattern is incorrect
	 */
	public void setPattern(float[] patternToSet) throws IllegalArgumentException
	{
		if (isLinePatternIncorrect(patternToSet))
		{
			throw new IllegalArgumentException();
		}

		pattern = patternToSet;
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
			int patternLength = input.readInt();
			pattern = new float[patternLength];
			for (int i = 0; i < patternLength; i++)
			{
				pattern[i] = input.readFloat();
			}
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
			output.writeInt(pattern.length);
			for (int i = 0; i < pattern.length; i++)
			{
				output.writeFloat(pattern[i]);
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
