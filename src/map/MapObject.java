package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawStyle;
import drawingStyles.StyleViewer;
import map.rendering.MapObjectsRenderer;

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
	 * Index of style, using to draw this object.
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
		MapObjectDrawStyle foundedStyle = styleViewer.findMapObjectDrawStyle(foundedIndex);

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
	 * Can this type of map object be drawen with this style
	 *
	 * @param objectDrawStyle drawing style of object
	 * @return Can this type of map object be drawen with this style
	 */
	protected abstract boolean canBeDrawenWithStyle(MapObjectDrawStyle objectDrawStyle);

	/**
	 * Render with objects render visitor
	 *
	 * @param objectsRenderer objects renderer
	 */
	public abstract void acceptRenderer(MapObjectsRenderer objectsRenderer);
}
