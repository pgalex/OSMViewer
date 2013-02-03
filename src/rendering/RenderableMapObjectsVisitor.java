package rendering;

import map.MapPolygon;

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
	 * Visit polygon
	 *
	 * @param polygon polygon on a map
	 * @throws IllegalArgumentException polygonToRender is null
	 */
	public abstract void visitPolygon(MapPolygon polygon) throws IllegalArgumentException;
}
