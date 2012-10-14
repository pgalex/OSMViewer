package drawingStyles;

import java.io.File;
import java.io.IOException;

/**
 * Main interface of drawingStyles with "setMapObjectDrawSettings" methods.
 * Using in style editing forms
 *
 * @author pgalex
 */
public interface StyleEditor extends StyleViewer
{
	/**
	 * Add draw settings of map object
	 *
	 * @param drawSettingsToAdd new map object style
	 * @throws IllegalArgumentException new style is null
	 */
	public void addMapObjectDrawSettings(MapObjectDrawSettings drawSettingsToAdd) throws IllegalArgumentException;

	/**
	 * Remove draw settings by index
	 *
	 * @param index style index
	 * @throws IllegalArgumentException index out of bounds
	 */
	public void removeMapObjectDrawSettings(Integer index) throws IllegalArgumentException;

	/**
	 * Write to file
	 *
	 * @param fileForWriting file, using for writing
	 * @throws IOException writing error
	 */
	public void writeToFile(File fileForWriting) throws IOException;

	/**
	 * Read from file
	 *
	 * @param fileForReading file to read
	 * @throws IOException reading error
	 */
	public void readFromFile(File fileForReading) throws IOException;
}
