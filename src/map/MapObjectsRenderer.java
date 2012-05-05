package map;

/**
 * Interface of visitor, using for draw objects of map. Uses to hide additional
 * methods of objects renders from Map
 *
 * @author pgalex
 */
public interface MapObjectsRenderer
{
	/**
	 * Render point
	 *
	 * @param pPoint point on a map
	 */
	public abstract void renderPoint(MapPoint pPoint);

	/**
	 * Render line
	 *
	 * @param pLine line on a map
	 */
	public abstract void renderLine(MapLine pLine);

	/**
	 * Render polygon
	 *
	 * @param pPolygon polygon on a map
	 */
	public abstract void renderPolygon(MapPolygon pPolygon);
}
