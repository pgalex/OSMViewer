package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectDrawStyle;
import drawingStyles.StyleViewer;
import map.rendering.MapObjectsRenderer;

/**
 * Any object on a map. Base class
 *
 * @author pgalex
 */
public class MapObject
{
	/**
	 * ID of object, comes from OpenStreetMap
	 */
	private long id;
	/**
	 * Tags, describes the object. Can not be null, but can be empty ( cuz there
	 * can be object without tags )
	 */
	private DefenitionTags defenitionTags;
	/**
	 * Index of MapObjectDrawSettings in StyleViewer, used to draw this object.
	 * Valid only for StyleViewer that was used to assign this index. null if
	 * index not founded
	 */
	private Integer styleIndex;

	/**
	 * Create with parameters
	 *
	 * @param objectId global OpenStreetMap id of object
	 * @param objectDefenitionTags Tags, describes the object
	 */
	public MapObject(long objectId, DefenitionTags objectDefenitionTags)
	{
		id = objectId;
		defenitionTags = objectDefenitionTags;
		styleIndex = null;

		InitializeNullFields();
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
	protected boolean canBeDrawenWithStyle(MapObjectDrawStyle objectDrawStyle)
	{
		if (objectDrawStyle == null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Render with objects render visitor
	 *
	 * @param objectsRenderer objects renderer
	 */
	public void acceptRenderer(MapObjectsRenderer objectsRenderer)
	{
	}

	/**
	 * Auto-initialize null fields
	 */
	private void InitializeNullFields()
	{
		if (defenitionTags == null)
		{
			defenitionTags = new DefenitionTags();
		}
	}
}
