package rendering;

/**
 * Visitor of renderable objects
 *
 * @author pgalex
 */
public interface RenderableMapObjectsVisitor
{
	/**
	 * Visit renderablePoint
	 *
	 * @param renderablePoint pointToRender on a map
	 * @throws IllegalArgumentException pointToRender is null
	 */
	public abstract void visitPoint(RenderableMapPoint renderablePoint) throws IllegalArgumentException;

	/**
	 * Visit renderableLine
	 *
	 * @param renderableLine lineToRender on a map
	 * @throws IllegalArgumentException lineToRender is null
	 */
	public abstract void visitLine(RenderableMapLine renderableLine) throws IllegalArgumentException;

	/**
	 * Visit renderablePolygon
	 *
	 * @param renderablePolygon renderablePolygon on a map
	 * @throws IllegalArgumentException polygonToRender is null
	 */
	public abstract void visitPolygon(RenderableMapPolygon renderablePolygon) throws IllegalArgumentException;
}