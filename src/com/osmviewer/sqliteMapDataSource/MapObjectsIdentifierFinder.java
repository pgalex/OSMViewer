/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.osmviewer.sqliteMapDataSource;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Tag;

/**
 *
 * @author preobrazhentsev
 */
public interface MapObjectsIdentifierFinder
{
	/**
	 * Find id for closed way
	 *
	 * @param wayTags tags of closed way to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	public String findClosedWayIdentifier(DefenitionTags wayTags) throws IllegalArgumentException;

	/**
	 * Find id for non-closed way
	 *
	 * @param wayTags tags of non-closed way to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException wayTags is null
	 */
	public String findNonClosedIdentifier(DefenitionTags wayTags) throws IllegalArgumentException;

	/**
	 * Find id for node
	 *
	 * @param nodeTags tags of node to find id of. Must be not null
	 * @return found id. Null if not found
	 * @throws IllegalArgumentException nodeTags is null
	 */
	public String findNodeIdentifier(DefenitionTags nodeTags) throws IllegalArgumentException;
}
