package map;

import drawingStyles.MapObjectDrawSettings;
import mapDefenitionUtilities.DefenitionTags;

/**
 * Finder of map object draw settings
 *
 * @author pgalex
 */
public interface MapObjectsDrawSettingsFinder
{
	/**
	 * Find draw settings of map object, by its defention tags
	 *
	 * @param mapObjectDefenitionTags tags of map object, which draw settings need
	 * to find
	 * @return draw settings of map object. null if not found
	 */
	public MapObjectDrawSettings findMapObjectDrawSettings(DefenitionTags mapObjectDefenitionTags);
}
