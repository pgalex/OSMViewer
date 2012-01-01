/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package osmXml;

import java.util.ArrayList;
import map.MapTag;

/**
 * Node из файла .osm
 * @author preobrazhentsev
 */
public class OSMFileNode
{
	public long id;
	public double latitude;
	public double longitude;
	public ArrayList<MapTag> tags;

	public OSMFileNode()
	{
		tags = new ArrayList<MapTag>();
	}
}
