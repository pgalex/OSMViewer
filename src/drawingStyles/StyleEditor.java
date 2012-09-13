package drawingStyles;

import drawingStyles.exceptions.IncorrectParameterException;
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
	 * @throws IncorrectParameterException new draw settings is null, style index is out of bounds
	 */
	public void setMapObjectDrawSettings(Integer index, MapObjectDrawSettings drawSettingsToSet) throws IncorrectParameterException;

	/**
	 * Add draw settings of map object
	 *
	 * @param drawSettingsToAdd new map object style
	 * @throws IncorrectParameterException new style is null
	 */
	public void addMapObjectDrawSettings(MapObjectDrawSettings drawSettingsToAdd) throws IncorrectParameterException;

	/**
	 * Remove draw settings by index
	 *
	 * @param index style index
	 * @throws IncorrectParameterException index out of bounds
	 */
	public void removeMapObjectDrawSettings(Integer index) throws IncorrectParameterException;

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
	 * @throws IncorrectParameterException new settins is null
	 */
	public void setMapDrawSettings(MapDrawSettings mapDrawingSettingsToSet) throws IncorrectParameterException;

	/**
	 * Write to file
	 *
	 * @param fileForWriting file, using for writing
	 * @throws IOException writing error
	 */
	public void writeToFile(File fileForWriting) throws IOException;
}
