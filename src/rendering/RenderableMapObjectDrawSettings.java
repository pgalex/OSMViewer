package rendering;

import drawingStyles.LineDrawSettings;
import drawingStyles.PointDrawSettings;
import drawingStyles.StandartPolygonDrawSettings;
import drawingStyles.StandartTextDrawSettings;
import mapDefenitionUtilities.DefenitionTags;

/**
 * Draw settings of renderable map object
 *
 * @author pgalex
 */
public interface RenderableMapObjectDrawSettings
{
	/**
	 * Can map object be a point (one node)
	 *
	 * @return can map object be a point on a map
	 */
	public boolean isCanBePoint();

	/**
	 * Can map object be a line(non-closed way)
	 *
	 * @return can map object be a line(non-closed way) on a map
	 */
	public boolean isCanBeLine();

	/**
	 * Can map object be a polygon(closed way)
	 *
	 * @return can map object be a polygon(closed way)
	 */
	public boolean isCanBePolygon();

	/**
	 * Get draw priority
	 *
	 * @return draw priority
	 */
	public int getDrawPriority();

	/**
	 * Find draw settings for point on scale level
	 *
	 * @param scaleLevel scale level
	 * @return point draw style on scale level. Null if not found
	 */
	public PointDrawSettings findPointDrawSettings(int scaleLevel);

	/**
	 * Find draw settings of line on scale level
	 *
	 * @param scaleLevel scale level
	 * @return draw settings of line on scale level. Null if not found
	 */
	public LineDrawSettings findLineDrawSettings(int scaleLevel);

	/**
	 * Find draw settings of polygon on scale level
	 *
	 * @param scaleLevel scale level
	 * @return draw settings of polygon on scale level. Null if not found
	 */
	public RenderableMapPolygonDrawSettings findPolygonDrawSettings(int scaleLevel);

	/**
	 * Find draw settings of text style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return draw settings of text style. Null if not found
	 */
	public TextDrawSettings findTextDrawSettings(int scaleLevel);

	/**
	 * Find value of tag in tags that means text subscription of object
	 *
	 * @param tagsWhereFindText tags of object where to find text description
	 * @return text subscription of object in tags. Empty if not found
	 */
	public String findTextInTags(DefenitionTags tagsWhereFindText);

	/**
	 * Get name of object
	 *
	 * @return name of object
	 */
	public String getName();

	/**
	 * Get object description
	 *
	 * @return object description
	 */
	public String getDescription();
}
