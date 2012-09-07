package map.rendering;

import map.MapLine;
import map.MapPoint;
import map.MapPolygon;

/**
 * Interface of visitor, using for draw objects of map. Uses to hide additional
 * methods of objects renders from Map
 *
 * @author pgalex
 */
public interface MapObjectsRenderer
{
	/**
	 * Render pointToRender
	 *
	 * @param pointToRender pointToRender on a map
	 */
	public abstract void renderPoint(MapPoint pointToRender);

	/**
	 * Render lineToRender
	 *
	 * @param lineToRender lineToRender on a map
	 */
	public abstract void renderLine(MapLine lineToRender);

	/**
	 * Render polygon
	 *
	 * @param polygonToRender polygon on a map
	 */
	public abstract void renderPolygon(MapPolygon polygonToRender);
}
