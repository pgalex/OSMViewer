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
	 * @throws IllegalArgumentException pointToRender is null
	 */
	public abstract void renderPoint(MapPoint pointToRender) throws IllegalArgumentException;

	/**
	 * Render lineToRender
	 *
	 * @param lineToRender lineToRender on a map
	 * @throws IllegalArgumentException lineToRender is null
	 */
	public abstract void renderLine(MapLine lineToRender) throws IllegalArgumentException;

	/**
	 * Render polygon
	 *
	 * @param polygonToRender polygon on a map
	 * @throws IllegalArgumentException polygonToRender is null
	 */
	public abstract void renderPolygon(MapPolygon polygonToRender) throws IllegalArgumentException;
}
