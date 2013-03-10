package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.osmXml.OsmTag;
import com.osmviewer.osmXml.OsmWay;
import java.util.ArrayList;

/**
 * Converter of osm objects to map objects
 *
 * @author pgalex
 */
public class OsmMapObjectsConverter
{
	/**
	 * Create map polygon or map line by osm way
	 *
	 * @param way osm way
	 * @param nodes nodes, using to find points of way
	 * @return map polygon or map line created by osm way. null if can not be
	 * created
	 * @throws IllegalArgumentException way or nodes is null
	 */
	public static MapObject createMapObjectByWay(OsmWay way, ArrayList<OsmNode> nodes) throws IllegalArgumentException
	{
		if (way == null)
		{
			throw new IllegalArgumentException("way is null");
		}
		if (nodes == null)
		{
			throw new IllegalArgumentException("nodes is null");
		}
		if (isOsmNodesArrayContainsNullElements(nodes))
		{
			throw new IllegalArgumentException("nodes contains null elements");
		}

		DefenitionTags wayTags = new DefenitionTags();
		for (int i = 0; i < way.getTagsCount(); i++)
		{
			wayTags.add(createMapTagByOsmTag(way.getTag(i)));
		}

		Location[] objectPoints = findPointsInOsmNodes(way, nodes);
		if (objectPoints.length == 0)
		{
			return null;
		}

		MapObject creatingMapObject;

		boolean wayDescribesPolygon = (way.getNodeId(0) == way.getNodeId(way.getNodesIdsCount() - 1));
		if (wayDescribesPolygon)
		{
			creatingMapObject = new MapPolygon(way.getId(), wayTags, objectPoints);
		}
		else
		{
			creatingMapObject = new MapLine(way.getId(), wayTags, objectPoints);
		}

		return creatingMapObject;
	}

	/**
	 *
	 *
	 * Is nodes array contains null elements
	 *
	 * @param nodes osm nodes array
	 * @return is nodes array contains null elements
	 * @throws IllegalArgumentException nodes is null
	 */
	private static boolean isOsmNodesArrayContainsNullElements(ArrayList<OsmNode> nodes) throws IllegalArgumentException
	{
		boolean isContainsNull = false;
		for (OsmNode osmNode : nodes)
		{
			if (osmNode == null)
			{
				isContainsNull = true;
				break;
			}
		}

		return isContainsNull;
	}

	/**
	 * Create array of point for map line or map polygon by finding them in nodes
	 * array, using ids
	 *
	 * @param way way, that nodes need to find
	 * @param nodes nodes, using to find points of way
	 * @return array of point for map line or map polygon. Empty if one or more
	 * points not founded
	 * @throws IllegalArgumentException way or nodes is null
	 */
	private static Location[] findPointsInOsmNodes(OsmWay way, ArrayList<OsmNode> nodes) throws IllegalArgumentException
	{
		if (way == null)
		{
			throw new IllegalArgumentException("way is null");
		}
		if (nodes == null)
		{
			throw new IllegalArgumentException("nodes is null");
		}
		if (isOsmNodesArrayContainsNullElements(nodes))
		{
			throw new IllegalArgumentException("nodes contains null elements");
		}

		Location[] foundPoints = new Location[way.getNodesIdsCount()];
		boolean allNodesFounds = true;
		for (int i = 0; i < way.getNodesIdsCount(); i++)
		{
			boolean nodeFound = false;

			for (OsmNode osmNode : nodes)
			{
				if (osmNode.getId() == way.getNodeId(i))
				{
					foundPoints[i] = new Location(osmNode.getLatitude(), osmNode.getLongitude());
					nodeFound = true;
					break;
				}
			}

			if (!nodeFound)
			{
				allNodesFounds = false;
			}
		}

		if (allNodesFounds)
		{
			return foundPoints;
		}
		else
		{
			return new Location[0];
		}
	}

	/**
	 * Creates map point by osm node
	 *
	 * @param node osm node
	 * @return map point created by osm node. Null if can not create
	 * @throws IllegalArgumentException node is null
	 */
	public static MapPoint createMapPointByOsmNode(OsmNode node) throws IllegalArgumentException
	{
		if (node == null)
		{
			throw new IllegalArgumentException("node is null");
		}

		// node without tag is not a MapPoint (can not be displayed), 
		// it will be included in MapLine like Location
		if (node.getTagsCount() == 0)
		{
			return null;
		}

		DefenitionTags nodeTags = new DefenitionTags();
		for (int i = 0; i < node.getTagsCount(); i++)
		{
			nodeTags.add(createMapTagByOsmTag(node.getTag(i)));
		}

		return new MapPoint(new Location(node.getLatitude(), node.getLongitude()), node.getId(), nodeTags);
	}

	/**
	 * Create map tag by osm tag
	 *
	 * @param osmTag osm tag
	 * @return map tag created by osm tag
	 * @throws IllegalArgumentException osmTag is null
	 */
	private static Tag createMapTagByOsmTag(OsmTag osmTag) throws IllegalArgumentException
	{
		if (osmTag == null)
		{
			throw new IllegalArgumentException("osmTag is null");
		}

		return new Tag(osmTag.getKey(), osmTag.getValue());
	}
}
