package drawingStyle;

/**
 * Main interface of drawingStyle with "set" methods. Using in style editing
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
	 * @throws ArrayIndexOutOfBoundsException style index is out of bounds
	 * @throws NullPointerException new style is null
	 */
	public abstract void set(int pIndex, MapObjectStyle pNewStyle) throws ArrayIndexOutOfBoundsException, NullPointerException;

	/**
	 * Add style
	 *
	 * @param pNewStyle new map object style
	 * @throws NullPointerException new style is null
	 */
	public abstract void add(MapObjectStyle pNewStyle) throws NullPointerException;

	/**
	 * Remove style by index
	 *
	 * @param pIndex style index
	 * @throws ArrayIndexOutOfBoundsException index out of bounds
	 */
	public abstract void remove(int pIndex) throws ArrayIndexOutOfBoundsException;

	/**
	 * Get styles count
	 *
	 * @return styles count
	 */
	public abstract int count();
}
