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
public class OSMFileRelation
{
	public long id;
	public ArrayList<MapTag> tags;
	public ArrayList<OSMFileRelationMember> members;

	public OSMFileRelation()
	{
		tags = new ArrayList<MapTag>();
		members = new ArrayList<OSMFileRelationMember>();
	}
}
