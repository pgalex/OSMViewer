/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osmXml;

import java.util.ArrayList;
import drawingStyles.MapTag;

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
