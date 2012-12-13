package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawSettings;
import drawingStyles.StyleViewer;

/**
 * Some object on a map
 *
 * @author pgalex
 */
public abstract class MapObject
{
	/**
	 * ID of object, comes from OpenStreetMap
	 */
	private long id;
	/**
	 * Tags, describes the object.Can be empty ( cuz in openstreetmap can be
	 * object without tags )
	 */
	private DefenitionTags defenitionTags;
	/**
	 * Index of style, using to identity object on map, and find how to draw this
	 * object
	 */
	private Integer styleIndex;

	/**
	 * Create with parameters
	 *
	 * @param objectId global OpenStreetMap id of object
	 * @param objectDefenitionTags Tags, describes the object
	 * @throws IllegalArgumentException objectDefenitionTags is null
	 */
	public MapObject(long objectId, DefenitionTags objectDefenitionTags) throws IllegalArgumentException
	{
		if (objectDefenitionTags == null)
		{
			throw new IllegalArgumentException();
		}

		id = objectId;
		defenitionTags = objectDefenitionTags;
		styleIndex = null;
	}

	/**
	 * Get ID
	 *
	 * @return ID of object, comes from OpenStreetMap
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Get defenition tags
	 *
	 * @return defenition tags
	 */
	public DefenitionTags getDefenitionTags()
	{
		return defenitionTags;
	}

	/**
	 * Get drawing style index
	 *
	 * @return style index. null if not assigned
	 */
	public Integer getStyleIndex()
	{
		return styleIndex;
	}

	/**
	 * Assign style index by founding it in style viewer, using defenition tags
	 * and canBeDrawenWithStyle method
	 *
	 * @param styleViewer style viewer, using to find index
	 */
	public void assignStyleIndex(StyleViewer styleViewer)
	{
		if (styleViewer == null)
		{
			return;
		}

		Integer foundedIndex = styleViewer.findStyleIndex(defenitionTags);
		MapObjectDrawSettings foundedStyle = styleViewer.getMapObjectDrawSettings(foundedIndex);

		if (canBeDrawenWithStyle(foundedStyle))
		{
			styleIndex = foundedIndex;
		}
		else
		{
			styleIndex = null;
		}
	}

	/**
	 * Is object visible in given area
	 *
	 * @param area area to test visibility in
	 * @return is object visible in area
	 * @throws IllegalArgumentException area is null
	 */
	public abstract boolean isVisibleInArea(MapBounds area) throws IllegalArgumentException;

	/**
	 * Render with objects render
	 *
	 * @param objectsRenderer objects renderer
	 */
	public abstract void acceptRenderer(MapObjectsRenderer objectsRenderer);

	/**
	 * Can this type of map object be drawen with this style
	 *
	 * @param objectDrawStyle drawing style of object
	 * @return Can this type of map object be drawen with this style
	 */
	protected abstract boolean canBeDrawenWithStyle(MapObjectDrawSettings objectDrawStyle);
}
