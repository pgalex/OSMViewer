package drawingStyles;

import drawingStyles.exceptions.MapDrawSettingsIsNullException;
import drawingStyles.exceptions.MapObjectDrawSettingsIsNullException;
import drawingStyles.exceptions.StyleIndexOutOfBoundsException;

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
	public abstract MapObjectDrawSettings getMapObjectDrawSettings(Integer index);

	/**
	 * Set draw settings of map object by index
	 *
	 * @param index style index
	 * @param drawSettingsToSet new draw settings of object with index
	 * @throws StyleIndexOutOfBoundsException style index is out of bounds
	 * @throws MapObjectDrawSettingsIsNullException new draw settings is null
	 */
	public abstract void setMapObjectDrawSettings(Integer index, MapObjectDrawSettings drawSettingsToSet) throws StyleIndexOutOfBoundsException, MapObjectDrawSettingsIsNullException;

	/**
	 * Add draw settings of map object
	 *
	 * @param drawSettingsToAdd new map object style
	 * @throws MapObjectDrawSettingsIsNullException new style is null
	 */
	public abstract void addMapObjectDrawSettings(MapObjectDrawSettings drawSettingsToAdd) throws MapObjectDrawSettingsIsNullException;

	/**
	 * Remove draw settings by index
	 *
	 * @param index style index
	 * @throws StyleIndexOutOfBoundsException index out of bounds
	 */
	public abstract void removeMapObjectDrawSettings(Integer index) throws StyleIndexOutOfBoundsException;

	/**
	 * Get count of storing map object draw setting
	 *
	 * @return count of storing map object draw setting
	 */
	public abstract int countOfMapObjectDrawSettings();

	/**
	 * Set new map drawing settings
	 *
	 * @param mapDrawingSettingsToSet new map drawing settings
	 * @throws MapDrawSettingsIsNullException new settins is null
	 */
	public abstract void setMapDrawSettings(MapDrawSettings mapDrawingSettingsToSet) throws MapDrawSettingsIsNullException;
}
