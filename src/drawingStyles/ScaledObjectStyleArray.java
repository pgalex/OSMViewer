package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import drawingStyles.exceptions.ScaleLevelOutOfBoundsException;
import drawingStyles.exceptions.ScaledStyleIsNullException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Array of ScaledObjectStyle
 *
 * @author pgalex
 */
public class ScaledObjectStyleArray implements ReadableMapData, WritableMapData
{
	/**
	 * Default minimum scale level of array. Scale levels that used to store
	 * information how to object (not drawing)
	 */
	private static final int DEFAULT_MINIMUM_SCALE_LEVEL = 2;
	/**
	 * Default maximum scale level of array. Scale levels that used to store
	 * information how to object (not drawing)
	 */
	private static final int DEFAULT_MAXIUMUM_SCALE_LEVEL = 18;
	/**
	 * Drawing style on each scale level. Стили на каждом из уровней масштаба
	 */
	private ScaledObjectStyle[] scaledStyles;
	/**
	 * Minimum scale level in array
	 */
	private int minimumScaleLevel;
	/**
	 * Maximum (valid) scale level in array
	 */
	private int maximumScaleLevel;

	/**
	 * Defaul contructor
	 */
	public ScaledObjectStyleArray()
	{
		minimumScaleLevel = DEFAULT_MINIMUM_SCALE_LEVEL;
		maximumScaleLevel = DEFAULT_MAXIUMUM_SCALE_LEVEL;

		scaledStyles = new ScaledObjectStyle[ccomputeStylesArrayLengthByScaleLevelBounds()];

		for (int i = 0; i < scaledStyles.length; i++)
			scaledStyles[i] = new ScaledObjectStyle();
	}

	/**
	 * Compute style array length by minimum and maximum scale level
	 *
	 * @return style array length
	 */
	private int ccomputeStylesArrayLengthByScaleLevelBounds()
	{
		return maximumScaleLevel - minimumScaleLevel + 1;
	}

	/**
	 * Set style on specifiec scale level. If level is out of range value will not
	 * be set
	 *
	 * @param pScaleLevel scale level
	 * @param pNewScaledStyle new style on scale level
	 * @throws ScaleLevelOutOfBoundsException scale level is out of range
	 * @throws ScaledStyleIsNullException new scaled style is null
	 */
	public void setStyleOnScale(int pScaleLevel, ScaledObjectStyle pNewScaledStyle) throws ScaleLevelOutOfBoundsException, ScaledStyleIsNullException
	{
		if (pNewScaledStyle == null)
			throw new ScaledStyleIsNullException(this);

		scaledStyles[convertScaleLevelToArrayIndex(pScaleLevel)] = pNewScaledStyle;
	}

	/**
	 * Convert scale level (defines by minimumScaleLevel and maximumScaleLevel) to
	 * scaledStyles array (from 0 to length)
	 *
	 * @param pScaleLevel
	 */
	private int convertScaleLevelToArrayIndex(int pScaleLevel) throws ScaleLevelOutOfBoundsException
	{
		if (pScaleLevel < minimumScaleLevel || pScaleLevel > maximumScaleLevel)
			throw new ScaleLevelOutOfBoundsException(this, pScaleLevel, minimumScaleLevel, maximumScaleLevel);

		return pScaleLevel - minimumScaleLevel;
	}

	/**
	 * Get style on specifiec scale level. If level is out of range returns
	 * nearest correct value
	 *
	 * @param pScaleLevel scale level
	 * @return style on specifiec scale level
	 */
	public ScaledObjectStyle getStyleOnScale(int pScaleLevel)
	{
		int normalizedScaleLevel = normalizeScaleLevel(pScaleLevel);

		return scaledStyles[convertScaleLevelToArrayIndex(normalizedScaleLevel)];
	}

	/**
	 * Normalize scale level if it out of array bounds and convert it for using in
	 * scaledStyles (from minimumLevel..maximumLevel to 0..scaledStyles.length)
	 *
	 * @param pScaleLevel scale level
	 * @return scale level in array bounds
	 */
	private int normalizeScaleLevel(int pScaleLevel)
	{
		int normalizedScaleLevel = pScaleLevel;

		if (normalizedScaleLevel < minimumScaleLevel)
			normalizedScaleLevel = minimumScaleLevel;

		if (normalizedScaleLevel > maximumScaleLevel)
			normalizedScaleLevel = maximumScaleLevel;

		return normalizedScaleLevel;
	}

	/**
	 * Get minimum scale levels
	 *
	 * @return minimum scale levels
	 */
	public int getMinimumScaleLevel()
	{
		return minimumScaleLevel;
	}

	/**
	 * Get maximum scale level
	 *
	 * @return maximum scale level
	 */
	public int getMaximumScaleLevel()
	{
		return maximumScaleLevel;
	}

	/**
	 * Read from stream
	 *
	 * @param pInput reading stream
	 * @throws IOException reading error
	 */
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			minimumScaleLevel = pInput.readInt();
			maximumScaleLevel = pInput.readInt();

			scaledStyles = new ScaledObjectStyle[ccomputeStylesArrayLengthByScaleLevelBounds()];
			for (int i = 0; i < scaledStyles.length; i++)
			{
				scaledStyles[i] = new ScaledObjectStyle();
				scaledStyles[i].readFromStream(pInput);
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
	 * @param pOutput output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeInt(minimumScaleLevel);
			pOutput.writeInt(maximumScaleLevel);
			for (int i = 0; i < scaledStyles.length; i++)
				scaledStyles[i].writeToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
