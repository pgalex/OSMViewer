package com.osmviewer.map.onlineMap;

import com.osmviewer.map.MapLine;
import com.osmviewer.map.MapObject;
import com.osmviewer.map.MapPoint;
import com.osmviewer.map.MapPolygon;
import com.osmviewer.map.RenderableMapObjectsDrawSettingsFinder;
import com.osmviewer.map.exceptions.ConnectionErrorException;
import com.osmviewer.map.exceptions.ReadingFromServerErrorException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.mapDefenitionUtilities.Location;
import com.osmviewer.mapDefenitionUtilities.Tag;
import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.osmXml.OsmTag;
import com.osmviewer.osmXml.OsmWay;
import com.osmviewer.osmXml.OsmXmlParsingHandler;
import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import com.osmviewer.osmXmlParsing.OnlineOsmParser;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Creates connection to openstreetmap.org, loads map data and convertes it to
 * online map for rendering. Knows how to fill OnlineMap by osm objects
 *
 * @author pgalex
 */
public class OnlineMapLoader implements OsmXmlParsingHandler
{
	/**
	 * Parser for converting .osm xml data to map objects
	 */
	private OnlineOsmParser onlineParser;
	
	private ArrayList<OsmNode> parsedNodes;
	private ArrayList<OsmWay> parsedWays;

	/**
	 * Create loader
	 */
	public OnlineMapLoader()
	{
		parsedNodes = new ArrayList<OsmNode>();
		parsedWays = new ArrayList<OsmWay>();
		onlineParser = new OnlineOsmParser(this);
	}

	/**
	 * Load osm data from web and fill map with loaded objects
	 *
	 * @param loadingSectorBounds bounds of loading map sector
	 * @param drawSettingsFinder finder of map objects draw settings
	 * @param fillingMap map, where new object will be added
	 * @throws IllegalArgumentException style viewer, fillingMap,
	 * loadingSectorBounds is null
	 * @throws OutOfMemoryError out of memory
	 * @throws ConnectionErrorException error while connecting to osm server
	 * @throws ReadingFromServerErrorException error while reading .osm from
	 * server
	 * @throws ParsingOsmErrorException error while parsing readed .osm xml data
	 */
	public void loadToMap(MapBounds loadingSectorBounds, RenderableMapObjectsDrawSettingsFinder drawSettingsFinder, OnlineMap fillingMap) throws
					IllegalArgumentException,
					OutOfMemoryError, ConnectionErrorException, ReadingFromServerErrorException, ParsingOsmErrorException
	{
		if (drawSettingsFinder == null || fillingMap == null || loadingSectorBounds == null)
		{
			throw new IllegalArgumentException();
		}

		fillingMap.clear();

		// nothing to load
		if (loadingSectorBounds.isZero())
		{
			return;
		}

		/*String connectionString = "http://api.openstreetmap.org/api/0.6/map?bbox="
		 + loadingSectorBounds.getLongitudeMinimum() + ","
		 + loadingSectorBounds.getLatitudeMinimum() + ","
		 + loadingSectorBounds.getLongitudeMaximum() + ","
		 + loadingSectorBounds.getLatitudeMaximum();*/
		try
		{
			/*URL openStreetMapURL = new URL(connectionString);
			 URLConnection openStreetMapConnection = openStreetMapURL.openConnection();
			 onlineParser.convert(openStreetMapConnection.getInputStream());*/
			
			parsedNodes.clear();
			parsedWays.clear();
			onlineParser.convert(new DataInputStream(new FileInputStream(new File("some_map.osm.xml"))));

			fillMapWithPoints(parsedNodes, drawSettingsFinder, fillingMap);
			fillMapWithPolygonsAndLines(parsedNodes, parsedWays, drawSettingsFinder, fillingMap);

			parsedNodes.clear();
			parsedWays.clear();
		}
		catch (MalformedURLException ex)
		{
			//throw new ConnectionErrorException(connectionString);
		}
		catch (IOException ex)
		{
			throw new ReadingFromServerErrorException();
		}
		catch (OutOfMemoryError ex)
		{
			throw ex;
		}
		catch (Exception ex)
		{
			throw new ParsingOsmErrorException(ex);
		}
	}

	/**
	 * Create map polygons and line from osm ways, add them to map
	 *
	 * @param nodes nodes array, using to find points of line or polygon
	 * @param ways ways array
	 * @param drawSettingsFinder style viewer for assigning style index
	 * @param fillingMap map, filling with map polygons and lines
	 */
	protected void fillMapWithPolygonsAndLines(ArrayList<OsmNode> nodes, ArrayList<OsmWay> ways, RenderableMapObjectsDrawSettingsFinder drawSettingsFinder, OnlineMap fillingMap)
	{
		if (nodes == null || ways == null || drawSettingsFinder == null || fillingMap == null)
		{
			return;
		}

		for (OsmWay way : ways)
		{
			MapObject newObject = createMapObjectByWay(way, nodes);
			if (newObject == null)
			{
				continue;
			}

			RenderableMapObjectDrawSettings newObjectDrawSettings = drawSettingsFinder.findMapObjectDrawSettings(newObject.getDefenitionTags());
			if (newObject.canBeDrawenWithStyle(newObjectDrawSettings))
			{
				newObject.setDrawSettings(newObjectDrawSettings);
				fillingMap.addObject(newObject);
			}
		}
	}

	/**
	 * Create map polygon or map line by osm way
	 *
	 * @param way osm way
	 * @param nodes nodes, using to find points of way
	 * @return map polygon or map line created by osm way. null if can not be
	 * created
	 */
	protected MapObject createMapObjectByWay(OsmWay way, ArrayList<OsmNode> nodes)
	{
		if (way == null)
		{
			return null;
		}

		ArrayList<Long> nodesIds = way.getNodesIds();
		DefenitionTags creatingObjectTags = createDefentionTagsByOsmTags(way.getTags());
		Location[] objectPoints = findPointsInOsmNodes(nodesIds, nodes);
		if (nodesIds.isEmpty() || objectPoints.length == 0 || creatingObjectTags == null)
		{
			return null;
		}

		MapObject creatingMapObject;

		boolean wayDescribesPolygon = nodesIds.get(0).equals(nodesIds.get(nodesIds.size() - 1));
		if (wayDescribesPolygon)
		{
			creatingMapObject = new MapPolygon(way.getId(), creatingObjectTags, objectPoints);
		}
		else
		{
			creatingMapObject = new MapLine(way.getId(), creatingObjectTags, objectPoints);
		}

		return creatingMapObject;
	}

	/**
	 * Create array of point for map line or map polygon by finding them in nodes
	 * array, using ids
	 *
	 * @param nodesIds array of way ids
	 * @param nodes nodes, using to find points of way
	 * @return array of point for map line or map polygon. Empty if one or more
	 * points not founded
	 */
	protected Location[] findPointsInOsmNodes(ArrayList<Long> nodesIds, ArrayList<OsmNode> nodes)
	{
		if (nodesIds == null || nodes == null)
		{
			return new Location[0];
		}
		if (nodesIds.isEmpty() || nodes.isEmpty())
		{
			return new Location[0];
		}

		Location[] foundPoints = new Location[nodesIds.size()];
		boolean allNodesFounds = true;
		for (int i = 0; i < nodesIds.size(); i++)
		{
			boolean nodeFound = false;

			for (OsmNode osmNode : nodes)
			{
				if (osmNode.getId() == nodesIds.get(i))
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
	 * Create map points from osm nodes and add them to map
	 *
	 * @param nodes array of nodes, readed from .osm
	 * @param drawSettingsFinder
	 * @param fillingMap map, filling with map points, created by osm nodes
	 */
	protected void fillMapWithPoints(ArrayList<OsmNode> nodes, RenderableMapObjectsDrawSettingsFinder drawSettingsFinder, OnlineMap fillingMap)
	{
		if (fillingMap == null || drawSettingsFinder == null || nodes == null)
		{
			return;
		}
		if (nodes.isEmpty())
		{
			return;
		}

		for (OsmNode currentNode : nodes)
		{
			MapPoint newPoint = createMapPointByOsmNode(currentNode);
			if (newPoint == null)
			{
				continue;
			}

			RenderableMapObjectDrawSettings newPointDrawSettings = drawSettingsFinder.findMapObjectDrawSettings(newPoint.getDefenitionTags());
			if (newPoint.canBeDrawenWithStyle(newPointDrawSettings))
			{
				newPoint.setDrawSettings(newPointDrawSettings);
				fillingMap.addObject(newPoint);
			}
		}
	}

	/**
	 * Creates map point by osm node
	 *
	 * @param node osm node
	 * @return map point created by osm node
	 */
	protected MapPoint createMapPointByOsmNode(OsmNode node)
	{
		if (node == null)
		{
			return null;
		}

		DefenitionTags creatingPointTags = createDefentionTagsByOsmTags(node.getTags());
		// node without tag is not a MapPoint (can not be displayed), 
		// it will be included in MapLine like Location
		if (creatingPointTags == null)
		{
			return null;
		}

		MapPoint creatingPoint = new MapPoint(new Location(node.getLatitude(), node.getLongitude()),
						node.getId(), creatingPointTags);

		return creatingPoint;
	}

	/**
	 * Create defenition tags by osm tags array
	 *
	 * @param osmTags array of osm tags
	 * @return defenition tags created by osm tags array
	 */
	protected DefenitionTags createDefentionTagsByOsmTags(ArrayList<OsmTag> osmTags)
	{
		if (osmTags == null)
		{
			return null;
		}

		DefenitionTags creatingTags = new DefenitionTags();
		for (int i = 0; i < osmTags.size(); i++)
		{
			Tag newTag = createMapTagByOsmTag(osmTags.get(i));
			if (newTag != null)
			{
				creatingTags.add(newTag);
			}
		}
		return creatingTags;
	}

	/**
	 * Create map tag by osm tag
	 *
	 * @param osmTag osm tag
	 * @return map tag created by osm tag
	 */
	protected Tag createMapTagByOsmTag(OsmTag osmTag)
	{
		if (osmTag == null)
		{
			return null;
		}
		if (osmTag.getKey().isEmpty())
		{
			return null;
		}

		return new Tag(osmTag.getKey(), osmTag.getValue());
	}

	@Override
	public void takeNode(OsmNode parsedNode) throws IllegalArgumentException
	{
		parsedNodes.add(parsedNode);
	}

	@Override
	public void takeWay(OsmWay parsedWay) throws IllegalArgumentException
	{
		parsedWays.add(parsedWay);
	}
}
