package drawingStyle;

import java.io.IOException;

/**
 * Main interface of drawingStyle with "set" methods. Using in style editing
 * forms
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
	 * Set style by index
	 *
	 * @param pIndex style index
	 * @param pNewStyle new style
	 * @throws ArrayIndexOutOfBoundsException style index is out of bounds
	 * @throws NullPointerException new style is null
	 */
	public void set(int pIndex, MapObjectStyle pNewStyle) throws ArrayIndexOutOfBoundsException, NullPointerException;

	/**
	 * Add style
	 *
	 * @param pNewStyle new map object style
	 * @throws NullPointerException new style is null
	 */
	public void add(MapObjectStyle pNewStyle) throws NullPointerException;

	/**
	 * Remove style with specefied index
	 *
	 * @param pIndex style index
	 * @throws ArrayIndexOutOfBoundsException index out of bounds
	 */
	public void remove(int pIndex) throws ArrayIndexOutOfBoundsException;

	/**
	 * Get styles count
	 *
	 * @return styles count
	 */
	public int count();
}
