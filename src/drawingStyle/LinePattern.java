package drawingStyle;

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
	 * Default constructor
	 */
	public LinePattern()
	{
		pattern = SOLID_LINE_PATTERN;
	}

	/**
	 * Constructor
	 *
	 * @param pPattern pattern
	 */
	public LinePattern(float[] pPattern)
	{
		pattern = pPattern;
		initializeNullFields();
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
			int patternLength = pInput.readInt();
			pattern = new float[patternLength];
			for (int i = 0; i < patternLength; i++)
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
	 * Autocreate null fields with default values
	 */
	private void initializeNullFields()
	{
		if (pattern == null)
			pattern = SOLID_LINE_PATTERN;
		if (pattern.length == 0)
			pattern = SOLID_LINE_PATTERN;
	}
}
