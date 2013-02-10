package rendering;

import drawingStyles.StandartMapDrawSettings;
import drawingStyles.MapObjectDrawSettings;
import java.io.File;
import java.io.IOException;
import mapDefenitionUtilities.DefenitionTags;

/**
 * Draw settings viewer. Provides information about map, map objects, and how to
 * draw them
 *
 * @author pgalex
 */
public interface DrawSettingsViewer
{
	/**
	 * Find draw settings of map object, by its defention tags
	 *
	 * @param mapObjectDefenitionTags tags of map object, which draw settings need
	 * to find
	 * @return draw settings of map object. null if not found
	 */
	public MapObjectDrawSettings findMapObjectDrawSettings(DefenitionTags mapObjectDefenitionTags);

	/**
	 * Get map drawing settings
	 *
	 * @return map drawing settings
	 */
	public StandartMapDrawSettings getMapDrawSettings();

	/**
	 * Read from file
	 *
	 * @param fileForReading file to read
	 * @throws IOException reading error
	 */
	public void readFromFile(File fileForReading) throws IOException;
}
