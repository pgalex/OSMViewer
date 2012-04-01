package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import map.DefenitionTags;

/**
 * Main interface of drawingStyle without "set" methods. Optimized for runtime
 * work
 *
 * @author pgalex
 */
public interface StyleViewer extends ReadableMapData, WritableMapData
{
	/**
	 * Get index of map object drawing style
	 *
	 * @param pDefenitionTags tags of map object
	 * @return index of style of object with that defenition tags
	 * @throws ArrayStoreException object not found
	 */
	public abstract int getStyleIndex(DefenitionTags pDefenitionTags) throws ArrayStoreException;

	/**
	 * Get map object drawing style by index
	 *
	 * @param pIndex index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	public abstract MapObjectStyle getMapObjectStyle(int pIndex);
}
