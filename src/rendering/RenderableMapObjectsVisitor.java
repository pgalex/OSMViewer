package rendering;

/**
 * Visitor of renderable objects
 *
 * @author pgalex
 */
public interface RenderableMapObjectsVisitor
{
	/**
	 * Visit renderable point-like map object
	 *
	 * @param renderablePoint visiting renderable point
	 * @throws IllegalArgumentException renderablePoint is null
	 */
	public abstract void visitPoint(RenderableMapPoint renderablePoint) throws IllegalArgumentException;

	/**
	 * Visit renderable line-like map object
	 *
	 * @param renderableLine visiting renderable line
	 * @throws IllegalArgumentException renderableLine is null
	 */
	public abstract void visitLine(RenderableMapLine renderableLine) throws IllegalArgumentException;

	/**
	 * Visit renderable polygon-like map object
	 *
	 * @param renderablePolygon visiting renderable polygon
	 * @throws IllegalArgumentException renderablePolygon is null
	 */
	public abstract void visitPolygon(RenderableMapPolygon renderablePolygon) throws IllegalArgumentException;
}
