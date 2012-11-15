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
	 * Render lineToRender
	 *
	 * @param lineToRender lineToRender on a map
	 */
	public abstract void renderLine(MapPolyline lineToRender);

	/**
	 * Render polygon
	 *
	 * @param polygonToRender polygon on a map
	 */
	public abstract void renderPolygon(MapPolygon polygonToRender);
}
