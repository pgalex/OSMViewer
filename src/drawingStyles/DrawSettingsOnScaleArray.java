package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Array of DrawSettingsOnScale
 *
 * @author pgalex
 */
public class DrawSettingsOnScaleArray implements ReadableMapData, WritableMapData
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
	private DrawSettingsOnScale[] settingsOnScales;
	/**
	 * Minimum scale level in array. Can be not default if read from file
	 */
	private int minimumScaleLevel;
	/**
	 * Maximum (valid) scale level in array. Can be not default if read from file
	 */
	private int maximumScaleLevel;

	/**
	 * Create with default values
	 */
	public DrawSettingsOnScaleArray()
	{
		minimumScaleLevel = DEFAULT_MINIMUM_SCALE_LEVEL;
		maximumScaleLevel = DEFAULT_MAXIUMUM_SCALE_LEVEL;
		
		settingsOnScales = new DrawSettingsOnScale[computeStylesArrayLengthByScaleLevelBounds()];
		
		for (int i = 0; i < settingsOnScales.length; i++)
		{
			settingsOnScales[i] = new DrawSettingsOnScale();
		}
	}

	/**
	 * Compute style array length by minimum and maximum scale level
	 *
	 * @return style array length
	 */
	private int computeStylesArrayLengthByScaleLevelBounds()
	{
		return maximumScaleLevel - minimumScaleLevel + 1;
	}

	/**
	 * Get style on specifiec scale level
	 *
	 * @param scaleLevel scale level
	 * @return style on specifiec scale level. If level is out of range returns
	 * nearest accessible draw settings. It helps to work with different scale
	 * level count, without changes of other parts of application
	 */
	public DrawSettingsOnScale getDrawSettingsOnScale(int scaleLevel)
	{
		int normalizedScaleLevel = normalizeScaleLevel(scaleLevel); // in bounds

		return settingsOnScales[convertScaleLevelToArrayIndex(normalizedScaleLevel)];
	}

	/**
	 * Set style on specifiec scale level
	 *
	 * @param scaleLevel scale level of draw settings
	 * @param drawSettingToSet draw settings that will be set on scale level
	 * @throws IllegalArgumentException drawSettingToSet is null or scaleLevel is
	 * out of bounds
	 */
	public void setDrawSettingsOnScale(int scaleLevel, DrawSettingsOnScale drawSettingToSet) throws IllegalArgumentException
	{
		if (drawSettingToSet == null)
		{
			throw new IllegalArgumentException();
		}
		if (scaleLevel < minimumScaleLevel || scaleLevel > maximumScaleLevel)
		{
			throw new IllegalArgumentException();
		}
		
		settingsOnScales[convertScaleLevelToArrayIndex(scaleLevel)] = drawSettingToSet;
	}

	/**
	 * Convert scale level (defines by minimumScaleLevel and maximumScaleLevel) to
	 * settingsOnScales array index (from 0 to length)
	 *
	 * @param scaleLevelToConvert scale level for converting
	 * @return settingsOnScales array index
	 * @throws IllegalArgumentException converting scale level is out of bounds
	 */
	private int convertScaleLevelToArrayIndex(int scaleLevelToConvert) throws IllegalArgumentException
	{
		if (scaleLevelToConvert < minimumScaleLevel || scaleLevelToConvert > maximumScaleLevel)
		{
			throw new IllegalArgumentException();
		}
		
		return scaleLevelToConvert - minimumScaleLevel;
	}

	/**
	 * Normalize scale level if it out of array bounds. Scale level will be
	 * normalize to nearest bound (minimumScaleLevel or maximumScaleLevel)
	 *
	 * @param scaleLevelToNormalize scale level for
	 * @return scale level in array bounds
	 */
	private int normalizeScaleLevel(int scaleLevelToNormalize)
	{
		int normalizedScaleLevel = scaleLevelToNormalize;
		
		if (normalizedScaleLevel < minimumScaleLevel)
		{
			normalizedScaleLevel = minimumScaleLevel;
		}
		
		if (normalizedScaleLevel > maximumScaleLevel)
		{
			normalizedScaleLevel = maximumScaleLevel;
		}
		
		return normalizedScaleLevel;
	}

	/**
	 * Get minimum scale level
	 *
	 * @return minimum scale level
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
	 * Find point draw style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return point draw style on scale level. Null if not found
	 */
	public PointDrawSettings findPointDrawSettings(int scaleLevel)
	{
		DrawSettingsOnScale drawSettingsOnScale = getDrawSettingsOnScale(scaleLevel);
		if (drawSettingsOnScale == null)
		{
			return null;
		}
		
		if (drawSettingsOnScale.isDrawPoint())
		{
			return drawSettingsOnScale.getPointDrawSettings();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Find line style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return line draw style on scale level. Null if not found
	 */
	public LineDrawSettings findLineDrawSettings(int scaleLevel)
	{
		DrawSettingsOnScale drawSettingsOnScale = getDrawSettingsOnScale(scaleLevel);
		if (drawSettingsOnScale == null)
		{
			return null;
		}
		
		if (drawSettingsOnScale.isDrawLine())
		{
			return drawSettingsOnScale.getLineDrawSettings();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Find polygon style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return polygon draw style on scale level. Null if not found
	 */
	public PolygonDrawSettings findPolygonDrawSettings(int scaleLevel)
	{
		DrawSettingsOnScale drawSettingsOnScale = getDrawSettingsOnScale(scaleLevel);
		if (drawSettingsOnScale == null)
		{
			return null;
		}
		
		if (drawSettingsOnScale.isDrawPolygon())
		{
			return drawSettingsOnScale.getPolygonDrawSettings();
		}
		else
		{
			return null;
		}
	}

	/**
	 * Find text style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return text draw style on scale level. Null if not found
	 */
	public TextDrawSettings findTextDrawSettings(int scaleLevel)
	{
		DrawSettingsOnScale drawSettingsOnScale = getDrawSettingsOnScale(scaleLevel);
		if (drawSettingsOnScale == null)
		{
			return null;
		}
		
		if (drawSettingsOnScale.isDrawText())
		{
			return drawSettingsOnScale.getTextDrawSettings();
		}
		else
		{
			return null;
		}
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
			minimumScaleLevel = input.readInt();
			maximumScaleLevel = input.readInt();
			
			settingsOnScales = new DrawSettingsOnScale[computeStylesArrayLengthByScaleLevelBounds()];
			for (int i = 0; i < settingsOnScales.length; i++)
			{
				settingsOnScales[i] = new DrawSettingsOnScale();
				settingsOnScales[i].readFromStream(input);
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
			output.writeInt(minimumScaleLevel);
			output.writeInt(maximumScaleLevel);
			for (int i = 0; i < settingsOnScales.length; i++)
			{
				settingsOnScales[i].writeToStream(output);
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
