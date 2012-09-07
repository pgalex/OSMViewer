package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;

/**
 * Main interface of drawingStyles without "set" methods. Optimized for runtime
 * work. Style Index is Integer (not special class) cuz StyleViewer and
 * StyleEditor works like array, and uses that index like an index of object in
 * array (we using static array for optimization)
 *
 * @author pgalex
 */
public interface StyleViewer extends ReadableMapData, WritableMapData
{
	/**
	 * Find index of map object drawing style
	 *
	 * @param objectDefenitionTags tags of map object
	 * @return index of style of object with that defenition tags. null if not
	 * found
	 */
	public abstract Integer findStyleIndex(DefenitionTags objectDefenitionTags);

	/**
	 * Find map object drawing style by index
	 *
	 * @param index index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	public abstract MapObjectDrawStyle findMapObjectDrawStyle(Integer index);

	/**
	 * Get map drawing settings
	 *
	 * @return map drawing settings
	 */
	public abstract MapDrawSettings getMapDrawSettings();
}
