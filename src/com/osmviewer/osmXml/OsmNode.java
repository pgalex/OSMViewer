package com.osmviewer.osmXml;

/**
 * Osm xml node (one point on map)
 *
 * @author pgalex
 */
public interface OsmNode
{
	/**
	 * Get unique global osm id
	 *
	 * @return unique global osm id
	 */
	public long getId();

	/**
	 * Get position latitude
	 *
	 * @return position latitude
	 */
	public double getLatitude();

	/**
	 * Get position longitude
	 *
	 * @return position longitude
	 */
	public double getLongitude();

	/**
	 * Get count of tags
	 *
	 * @return count of tags
	 */
	public int getTagsCount();

	/**
	 * Get tag by index
	 *
	 * @param index index of tag
	 * @return tag by index
	 * @throws IllegalArgumentException index is out of bounds
	 */
	public OsmTag getTag(int index) throws IllegalArgumentException;
}
