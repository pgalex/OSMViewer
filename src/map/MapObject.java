package map;

import drawingStyles.DefenitionTags;
import drawingStyles.MapObjectStyle;
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
	 * Index of MapObjectStyle in StyleViewer, used to draw this object. Valid
	 * only for StyleViewer that was used to assign this index. null if index not
	 * founded
	 */
	private Integer styleIndex;

	/**
	 * Constructor
	 *
	 * @param pId global OpenStreetMap id of object
	 * @param pDefenitionTags Tags, describes the object
	 */
	public MapObject(long pId, DefenitionTags pDefenitionTags)
	{
		id = pId;
		defenitionTags = pDefenitionTags;
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
	 * @param pStyleViewer style viewer, using to find index
	 */
	public void assignStyleIndex(StyleViewer pStyleViewer)
	{
		if (pStyleViewer == null)
			return;

		Integer foundedIndex = pStyleViewer.getStyleIndex(defenitionTags);
		MapObjectStyle foundedStyle = pStyleViewer.getMapObjectStyle(foundedIndex);
		if (canBeDrawenWithStyle(foundedStyle))
			styleIndex = foundedIndex;
		else
			styleIndex = null;
	}

	/**
	 * Can this type of map object be drawen with this style
	 *
	 * @param pStyle drawing style of object
	 * @return Can this type of map object be drawen with this style
	 */
	protected boolean canBeDrawenWithStyle(MapObjectStyle pStyle)
	{
		if (pStyle == null)
			return false;
		else
			return true;
	}

	/**
	 * Render with objects render visitor
	 *
	 * @param pObjectsRenderer objects renderer
	 */
	public void acceptRenderer(MapObjectsRenderer pObjectsRenderer)
	{
	}

	/**
	 * Auto-initialize null fields
	 */
	private void InitializeNullFields()
	{
		if (defenitionTags == null)
			defenitionTags = new DefenitionTags();
	}
}
