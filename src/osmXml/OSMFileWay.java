/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osmXml;

import java.util.ArrayList;
import map.MapTag;

/**
 * Way .osm файла
 * @author preobrazhentsev
 */
public class OSMFileWay
{
	public long id;
	public ArrayList<MapTag> tags;
	public ArrayList nodesIds;

	public OSMFileWay()
	{
		tags = new ArrayList<MapTag>();
		nodesIds = new ArrayList();
	}
}
