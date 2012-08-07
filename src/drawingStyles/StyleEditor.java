package drawingStyles;

import drawingStyles.exceptions.MapDrawSettingsIsNullException;
import drawingStyles.exceptions.MapObjectStyleIsNullException;
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
	 * @param pIndex index of style
	 * @return map object drawing style. null if style with this index not found
	 */
	public abstract MapObjectDrawSettings getMapObjectDrawSettings(Integer pIndex);

	/**
	 * Set draw settings of map object by index
	 *
	 * @param pIndex style index
	 * @param pDrawSettings new draw settings of object with pIndex
	 * @throws StyleIndexOutOfBoundsException style index is out of bounds
	 * @throws MapObjectStyleIsNullException new draw settings is null
	 */
	public abstract void setMapObjectDrawSettings(Integer pIndex, MapObjectDrawSettings pDrawSettings) throws StyleIndexOutOfBoundsException, MapObjectStyleIsNullException;

	/**
	 * Add draw settings of map object
	 *
	 * @param pDrawSettings new map object style
	 * @throws MapObjectStyleIsNullException new style is null
	 */
	public abstract void addMapObjectDrawSettings(MapObjectDrawSettings pDrawSettings) throws MapObjectStyleIsNullException;

	/**
	 * Remove draw settings by index
	 *
	 * @param pIndex style index
	 * @throws StyleIndexOutOfBoundsException index out of bounds
	 */
	public abstract void removeMapObjectDrawSettings(Integer pIndex) throws StyleIndexOutOfBoundsException;

	/**
	 * Get count of storing map object draw setting
	 *
	 * @return count of storing map object draw setting
	 */
	public abstract int countOfMapObjectDrawSettings();

	/**
	 * Set new map drawing settings
	 *
	 * @param pMapDrawingSettings new map drawing settings
	 * @throws MapDrawSettingsIsNullException new settins is null
	 */
	public abstract void setMapDrawSettings(MapDrawSettings pMapDrawingSettings) throws MapDrawSettingsIsNullException;
}
