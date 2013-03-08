package com.osmviewer.osmXml;

/**
 * Osm xml tag
 *
 * @author pgalex
 */
public interface OsmTag
{
	/**
	 * Get tag key
	 *
	 * @return tag key
	 */
	public String getKey();

	/**
	 * Get tag value
	 *
	 * @return tag value
	 */
	public String getValue();
}
