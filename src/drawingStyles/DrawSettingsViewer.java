package drawingStyles;

import java.io.File;
import java.io.IOException;
import map.RenderableMapObjectsDrawSettingsFinder;

/**
 * Draw settings viewer. Provides information about map, map objects, and how to
 * draw them
 *
 * @author pgalex
 */
public interface DrawSettingsViewer extends RenderableMapObjectsDrawSettingsFinder
{
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
