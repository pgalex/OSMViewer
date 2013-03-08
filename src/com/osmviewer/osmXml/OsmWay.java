package com.osmviewer.osmXml;

/**
 * Osm xml way
 *
 * @author pgalex
 */
public interface OsmWay
{
	/**
	 * Get unique global osm id
	 *
	 * @return unique global osm id
	 */
	public long getId();

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

	/**
	 * Get count of nodes ids
	 *
	 * @return count of nodes ids
	 */
	public int getNodesIdsCount();

	/**
	 * Get node id by index
	 *
	 * @param index index of node id
	 * @return node id by index
	 * @throws IllegalArgumentException index is out of bounds
	 */
	public long getNodeId(int index) throws IllegalArgumentException;
}
