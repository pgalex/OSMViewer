package drawingStyle;

import java.io.IOException;

/**
 * Main interface of drawingStyle with "setMapObjectStyle" methods. Using in
 * style editing forms
 *
 * @author pgalex
 */
public interface StyleEditor extends StyleViewer
{
	/**
	 * Write styles to file
	 *
	 * @param pFileName file name
	 * @throws IOException writing error
	 */
	public void saveToFile(String pFileName) throws IOException;

	/**
	 * Set new style for map object, by id
	 *
	 * @param pStyleId style id
	 * @param pNewStyle new style
	 * @throws ArrayIndexOutOfBoundsException style id is out of bounds
	 */
	public void setMapObjectStyle(int pStyleId, MapObjectStyle pNewStyle) throws ArrayIndexOutOfBoundsException;

	/**
	 * Add style
	 *
	 * @param pNewStyle new map object style
	 * @return id of new style
	 */
	public int add(MapObjectStyle pNewStyle);

	/**
	 * Remove style with specefied id
	 *
	 * @param pStyleId style id
	 * @throws ArrayIndexOutOfBoundsException id out of bounds
	 */
	public void remove(int pStyleId) throws ArrayIndexOutOfBoundsException;

	/**
	 * Get all style ids
	 *
	 * @return all style ids
	 */
	public int[] getIds();
}
