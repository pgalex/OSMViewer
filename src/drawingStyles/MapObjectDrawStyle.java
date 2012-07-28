package drawingStyles;

/**
 * Drawing style of map object
 *
 * @author pgalex
 */
public interface MapObjectDrawStyle
{
	/**
	 * Can object be a point (one node) on a map
	 *
	 * @return Can object be a point on a map
	 */
	public boolean canBePoint();

	/**
	 * Can object be a line(non-closed way) on a map
	 *
	 * @return Can object be a line(non-closed way) on a map
	 */
	public boolean canBeLine();

	/**
	 * Can object be a polygon(closed way) on a map
	 *
	 * @return Can object be a polygon(closed way) on a map
	 */
	public boolean canBePolygon();

	/**
	 * Get draw priority
	 *
	 * @return draw priority
	 */
	public int getDrawPriority();

	/**
	 * Get object description
	 *
	 * @return object description
	 */
	public String getDescription();

	/**
	 * Find point draw style on scale level
	 *
	 * @param pScaleLevel scale level
	 * @return point draw style on scale level
	 */
	public PointDrawStyle findPointDrawStyle(int pScaleLevel);

	/**
	 * Find line style on scale level
	 *
	 * @param pScaleLevel scale level
	 * @return line draw style on scale level
	 */
	public LineDrawStyle findLineDrawStyle(int pScaleLevel);

	/**
	 * Find polygon style on scale level
	 *
	 * @param pScaleLevel scale level
	 * @return polygon draw style on scale level
	 */
	public PolygonDrawStyle findPolygonDrawStyle(int pScaleLevel);

	/**
	 * Find text style on scale level
	 *
	 * @param pScaleLevel scale level
	 * @return text draw style on scale level
	 */
	public TextDrawStyle findTextDrawStyle(int pScaleLevel);

	/**
	 * Find value of tag that means text description of object
	 *
	 * @param pTags tags of object
	 * @return text description of object founded in tag. Empty if not found
	 */
	public String findTextInTags(DefenitionTags pTags);
}
