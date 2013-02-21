package com.osmviewer.osmXml;

import java.util.ArrayList;

/**
 * Отношение в файле .osm (пока не используется)
 * @author preobrazhentsev
 */
public class OsmRelation
{
	public long id;
	public ArrayList<OsmTag> tags;
	public ArrayList<OsmRelationMember> members;

	public OsmRelation()
	{
		tags = new ArrayList<OsmTag>();
		members = new ArrayList<OsmRelationMember>();
	}
}