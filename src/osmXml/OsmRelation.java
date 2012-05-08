/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osmXml;

import java.util.ArrayList;
import map.MapTag;

/**
 * Отношение в файле .osm (пока не используется)
 * @author preobrazhentsev
 */
public class OsmRelation
{
	public long id;
	public ArrayList<MapTag> tags;
	public ArrayList<OsmRelationMember> members;

	public OsmRelation()
	{
		tags = new ArrayList<MapTag>();
		members = new ArrayList<OsmRelationMember>();
	}
}
