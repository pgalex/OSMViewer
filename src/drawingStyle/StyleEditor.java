package drawingStyle;

import drawingStyle.exceptions.MapDrawingSettingsIsNullException;
import drawingStyle.exceptions.MapObjectStyleIsNullException;
import drawingStyle.exceptions.StyleIndexOutOfBoundsException;

/**
 * Main interface of drawingStyle with "setMapObjectStyle" methods. Using in style editing
 * forms
 *
 * @author pgalex
 */
public interface StyleEditor extends StyleViewer
{
	/**
	 * Set style by index
	 *
	 * @param pIndex style index
	 * @param pNewStyle new style
	 * @throws StyleIndexOutOfBoundsException style index is out of bounds
	 * @throws MapObjectStyleIsNullException new style is null
	 */
	public abstract void setMapObjectStyle(Integer pIndex, MapObjectStyle pNewStyle) throws StyleIndexOutOfBoundsException, MapObjectStyleIsNullException;

	/**
	 * Add style
	 *
	 * @param pNewStyle new map object style
	 * @throws MapObjectStyleIsNullException new style is null
	 */
	public abstract void addMapObjectStyle(MapObjectStyle pNewStyle) throws MapObjectStyleIsNullException;

	/**
	 * Remove style by index
	 *
	 * @param pIndex style index
	 * @throws StyleIndexOutOfBoundsException index out of bounds
	 */
	public abstract void removeMapObjectStyle(Integer pIndex) throws StyleIndexOutOfBoundsException;

	/**
	 * Get styles countOfMapObjectStyles
	 *
	 * @return styles countOfMapObjectStyles
	 */
	public abstract int countOfMapObjectStyles();
	
	/**
	 * Set new map drawing settings
	 *
	 * @param pMapDrawingSettings new map drawing settings
	 * @throws MapDrawingSettingsIsNullException new settins is null
	 */
	public abstract void setMapDrawingSettings(MapDrawingSettings pMapDrawingSettings) throws MapDrawingSettingsIsNullException;
}
