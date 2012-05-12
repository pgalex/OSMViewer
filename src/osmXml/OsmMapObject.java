package osmXml;

import java.util.ArrayList;

/**
 * Some object of osm map. Common part of nodes and ways
 *
 * @author pgalex
 */
public class OsmMapObject
{
	/**
	 * Unique global osm id of object
	 */
	private long id;
	/**
	 * Object tags
	 */
	private ArrayList<OsmTag> tags;

	/**
	 * Default constructor
	 */
	public OsmMapObject()
	{
		id = 0;
		tags = new ArrayList<OsmTag>();
	}

	/**
	 * Set unique global osm id of object
	 *
	 * @param pId Unique global osm id of object
	 */
	public void setId(long pId)
	{
		id = pId;
	}

	/**
	 * Get unique global osm id of object
	 *
	 * @return Unique global osm id of object
	 */
	public long getId()
	{
		return id;
	}

	/**
	 * Set object tags
	 *
	 * @param pTags Object tags
	 */
	public void setTags(ArrayList<OsmTag> pTags)
	{
		tags.clear();

		if (pTags != null)
			tags.addAll(pTags);
	}

	/**
	 * Get object tags
	 *
	 * @return Object tags
	 */
	public ArrayList<OsmTag> getTags()
	{
		return tags;
	}
}
