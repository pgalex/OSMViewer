package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import drawingStyles.exceptions.LinePatternIncorrectException;
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
	 * @throws LinePatternIncorrectException linePattern is incorrect
	 */
	public LinePattern(float[] linePattern) throws LinePatternIncorrectException
	{
		if (isLinePatternIncorrect(linePattern))
		{
			throw new LinePatternIncorrectException();
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
	 * @throws LinePatternIncorrectException new pattern is incorrect
	 */
	public void setPattern(float[] patternToSet) throws LinePatternIncorrectException
	{
		if (isLinePatternIncorrect(patternToSet))
		{
			throw new LinePatternIncorrectException();
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
