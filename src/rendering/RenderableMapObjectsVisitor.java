package rendering;

import map.MapLine;
import map.MapPoint;
import map.MapPolygon;

/**
 * Visitor of renderable objects
 *
 * @author pgalex
 */
public interface RenderableMapObjectsVisitor
{
	/**
	 * Visit point
	 *
	 * @param point pointToRender on a map
	 * @throws IllegalArgumentException pointToRender is null
	 */
	public abstract void visitPoint(MapPoint point) throws IllegalArgumentException;

	/**
	 * Visit line
	 *
	 * @param line lineToRender on a map
	 * @throws IllegalArgumentException lineToRender is null
	 */
	public abstract void visitLine(MapLine line) throws IllegalArgumentException;

	/**
	 * Visit polygon
	 *
	 * @param polygon polygon on a map
	 * @throws IllegalArgumentException polygonToRender is null
	 */
	public abstract void visitPolygon(MapPolygon polygon) throws IllegalArgumentException;
}
