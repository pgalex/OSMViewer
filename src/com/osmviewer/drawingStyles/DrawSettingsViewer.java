package com.osmviewer.drawingStyles;

import java.io.File;
import java.io.IOException;

/**
 * Draw settings viewer. Provides information about how to draw map and map
 * objects
 *
 * @author pgalex
 */
public interface DrawSettingsViewer
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
