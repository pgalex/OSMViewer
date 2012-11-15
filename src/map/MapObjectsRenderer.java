package map;

/**
 * Interface of visitor, using for draw objects of map
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
	 * Render polyline
	 *
	 * @param polylineToRender polyline on a map
	 */
	public abstract void renderPolyline(MapPolyline polylineToRender);

	/**
	 * Render polygon
	 *
	 * @param polygonToRender polygon on a map
	 */
	public abstract void renderPolygon(MapPolygon polygonToRender);
}
