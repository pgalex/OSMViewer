package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;

/**
 * Interface of ScaledObjectStyle array. Gives only get methods
 *
 * @author pgalex
 */
public interface ScaledObjectStyleCollection extends ReadableMapData, WritableMapData
{
	/**
	 * Get style on specifiec scale level. If level is out of range returns
	 * nearest correct value (if currect scale levels count is more or less than
	 * defined in constant)
	 *
	 * @param pScaleLevel scale level
	 * @return style on specifiec scale level
	 */
	ScaledObjectStyle getStyleOnScale(int pScaleLevel);

	/**
	 * Get scale levels count
	 *
	 * @return currect scale levels count
	 */
	public int count();
}
