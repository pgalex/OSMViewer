package drawingStyles;

import mapDefenitionUtilities.DefenitionTags;
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
	public Integer findStyleIndex(DefenitionTags objectDefenitionTags);

	/**
	 * Get map object draw settings by index
	 *
	 * @param index index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	public MapObjectDrawSettings getMapObjectDrawSettings(Integer index);

	/**
	 * Set draw settings of map object by index
	 *
	 * @param index style index
	 * @param drawSettingsToSet new draw settings of object with index
	 * @throws IllegalArgumentException new draw settings is null, style index is
	 * out of bounds
	 */
	public void setMapObjectDrawSettings(Integer index, MapObjectDrawSettings drawSettingsToSet) throws IllegalArgumentException;

	/**
	 * Get count of storing map object draw setting
	 *
	 * @return count of storing map object draw setting
	 */
	public int countOfMapObjectDrawSettings();

	/**
	 * Set new map drawing settings
	 *
	 * @param mapDrawingSettingsToSet new map drawing settings
	 * @throws IllegalArgumentException new settins is null
	 */
	public void setMapDrawSettings(MapDrawSettings mapDrawingSettingsToSet) throws IllegalArgumentException;

	/**
	 * Get map drawing settings
	 *
	 * @return map drawing settings
	 */
	public MapDrawSettings getMapDrawSettings();
}
