package onlineMap;

import drawingStyles.DefenitionTags;
import drawingStyles.EditableDefenitionTags;
import drawingStyles.StyleViewer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import map.MapBounds;
import map.MapPoint;
import map.MapPosition;
import drawingStyles.MapTag;
import map.MapLine;
import map.MapObject;
import map.MapPolygon;
import map.exceptions.*;
import osmXml.OsmNode;
import osmXml.OsmTag;
import osmXml.OsmWay;

/**
 * Creates connection to openstreetmap.org, loads map data and convertes it to
 * online map for rendering. Knows how to fill Map by osm objects
 *
 * @author pgalex
 */
public class OnlineMapLoader
{
	/**
	 * Parser for converting .osm xml data to map objects
	 */
	private OnlineOsmParser onlineParser;

	/**
	 * Default constructor
	 */
	public OnlineMapLoader()
	{
		onlineParser = new OnlineOsmParser();
	}

	/**
	 * Load osm data from web and fill map with loaded objects
	 *
	 * @param pLoadingSectorBounds bounds of loading map sector
	 * @param pStyleViewer viewer, uses to assing style index to objects
	 * @param pFillingMap map, where new object will be added
	 * @throws StyleViewerIsNullException style viewer is null
	 * @throws MapIsNullException online map is null
	 * @throws MapBoundsIsNullRuntimeException loading sector bounds is null
	 * @throws OutOfMemoryError out of memory
	 * @throws ConnectionErrorException error while connecting to osm server
	 * @throws ReadingFromServerErrorException error while reading .osm from
	 * server
	 * @throws OsmParsingErrorException error while parsing readed .osm xml data
	 */
	public void loadToMap(MapBounds pLoadingSectorBounds, StyleViewer pStyleViewer, OnlineMap pFillingMap) throws
					StyleViewerIsNullException, MapIsNullException, MapBoundsIsNullRuntimeException,
					OutOfMemoryError, ConnectionErrorException, ReadingFromServerErrorException, OsmParsingErrorException
	{
		if (pLoadingSectorBounds == null)
		{
			throw new MapBoundsIsNullRuntimeException();
		}
		if (pStyleViewer == null)
		{
			throw new StyleViewerIsNullException();
		}
		if (pFillingMap == null)
		{
			throw new MapIsNullException();
		}
		// nothing to load
		if (pLoadingSectorBounds.isZero())
		{
			return;
		}

		String connectionString = "http://api.openstreetmap.org/api/0.6/map?bbox="
						+ pLoadingSectorBounds.getLongitudeMinimum() + ","
						+ pLoadingSectorBounds.getLatitudeMinimum() + ","
						+ pLoadingSectorBounds.getLongitudeMaximum() + ","
						+ pLoadingSectorBounds.getLatitudeMaximum();
		try
		{
			URL openStreetMapURL = new URL(connectionString);
			URLConnection openStreetMapConnection = openStreetMapURL.openConnection();
			onlineParser.convert(openStreetMapConnection.getInputStream());

			fillMapWithPoints(onlineParser.getNodes(), pStyleViewer, pFillingMap);
			fillMapWithPolygonsAndLine(onlineParser.getNodes(), onlineParser.getWays(), pStyleViewer, pFillingMap);
		}
		catch (MalformedURLException ex)
		{
			throw new ConnectionErrorException(connectionString);
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
			throw new OsmParsingErrorException();
		}
	}

	/**
	 * Create map polygons and line from osm ways, add them to map
	 *
	 * @param pNodes nodes array, using to find points of line or polygon
	 * @param pWays ways array
	 * @param pStyleViewer style viewe for assigning style index
	 * @param pFillingMap map, filling with map polygons and lines
	 */
	protected void fillMapWithPolygonsAndLine(ArrayList<OsmNode> pNodes, ArrayList<OsmWay> pWays,
					StyleViewer pStyleViewer, OnlineMap pFillingMap)
	{
		if (pNodes == null || pWays == null || pStyleViewer == null || pFillingMap == null)
		{
			return;
		}

		for (OsmWay way : pWays)
		{
			MapObject newObject = createMapObjectByWay(way, pNodes);
			if (newObject != null)
			{
				newObject.assignStyleIndex(pStyleViewer);
				pFillingMap.addObject(newObject);
			}
		}
	}

	/**
	 * Create map polygon or map line by osm way
	 *
	 * @param pWay osm way
	 * @param pNodes nodes, using to find points of way
	 * @return map polygon or map line created by osm way. null if can not be
	 * created
	 */
	protected MapObject createMapObjectByWay(OsmWay pWay, ArrayList<OsmNode> pNodes)
	{
		if (pWay == null)
		{
			return null;
		}

		ArrayList<Long> nodesIds = pWay.getNodesIds();
		DefenitionTags creatingObjectTags = createDefentionTagsByOsmTags(pWay.getTags());
		MapPosition[] objectPoints = findPointsInOsmNodes(nodesIds, pNodes);
		if (nodesIds.isEmpty() || objectPoints.length == 0 || creatingObjectTags == null)
		{
			return null;
		}

		MapObject creatingMapObject;

		boolean wayDescribesPolygon = nodesIds.get(0).equals(nodesIds.get(nodesIds.size() - 1));
		if (wayDescribesPolygon)
		{
			creatingMapObject = new MapPolygon(pWay.getId(), creatingObjectTags, objectPoints);
		}
		else
		{
			creatingMapObject = new MapLine(pWay.getId(), creatingObjectTags, objectPoints);
		}

		return creatingMapObject;
	}

	/**
	 * Create array of point for map line or map polygon by finding them in nodes
	 * array, using ids
	 *
	 * @param pNodesIds array of way ids
	 * @param pNodes nodes, using to find points of way
	 * @return array of point for map line or map polygon. Empty if one or more
	 * points not founded
	 */
	protected MapPosition[] findPointsInOsmNodes(ArrayList<Long> pNodesIds, ArrayList<OsmNode> pNodes)
	{
		if (pNodesIds == null || pNodes == null)
		{
			return new MapPosition[0];
		}
		if (pNodesIds.isEmpty() || pNodes.isEmpty())
		{
			return new MapPosition[0];
		}

		MapPosition[] foundPoints = new MapPosition[pNodesIds.size()];
		boolean allNodesFounds = true;
		for (int i = 0; i < pNodesIds.size(); i++)
		{
			boolean nodeFound = false;

			for (OsmNode osmNode : pNodes)
			{
				if (osmNode.getId() == pNodesIds.get(i))
				{
					foundPoints[i] = new MapPosition(osmNode.getLatitude(), osmNode.getLongitude());
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
			return new MapPosition[0];
		}
	}

	/**
	 * Create map points from osm nodes and add them to map
	 *
	 * @param pNodes array of nodes, readed from .osm
	 * @param pStyleViewer style viewer for assigning style index for creating map
	 * points
	 * @param pFillingMap map, filling with map points, created by osm nodes
	 */
	protected void fillMapWithPoints(ArrayList<OsmNode> pNodes, StyleViewer pStyleViewer, OnlineMap pFillingMap)
	{
		if (pFillingMap == null || pStyleViewer == null || pNodes == null)
		{
			return;
		}
		if (pNodes.isEmpty())
		{
			return;
		}

		for (OsmNode currentNode : pNodes)
		{
			MapPoint newPoint = createMapPointByOsmNode(currentNode);
			if (newPoint != null)
			{
				newPoint.assignStyleIndex(pStyleViewer);
				pFillingMap.addObject(newPoint);
			}
		}
	}

	/**
	 * Creates map point by osm node
	 *
	 * @param pNode osm node
	 * @return map point created by osm node
	 */
	protected MapPoint createMapPointByOsmNode(OsmNode pNode)
	{
		if (pNode == null)
		{
			return null;
		}

		DefenitionTags creatingPointTags = createDefentionTagsByOsmTags(pNode.getTags());
		// node without tag is not a MapPoint (can not be displayed), 
		// it will be included in MapLine like MapPosition
		if (creatingPointTags == null)
		{
			return null;
		}
		if (creatingPointTags.isEmpty())
		{
			return null;
		}

		MapPoint creatingPoint = new MapPoint(new MapPosition(pNode.getLatitude(), pNode.getLongitude()),
						pNode.getId(), creatingPointTags);

		return creatingPoint;
	}

	/**
	 * Create defenition tags by osm tags array
	 *
	 * @param pOsmTags array of osm tags
	 * @return defenition tags created by osm tags array
	 */
	protected DefenitionTags createDefentionTagsByOsmTags(ArrayList<OsmTag> pOsmTags)
	{
		if (pOsmTags == null)
		{
			return null;
		}

		EditableDefenitionTags creatingTags = new EditableDefenitionTags();
		for (int i = 0; i < pOsmTags.size(); i++)
		{
			MapTag newTag = createMapTagByOsmTag(pOsmTags.get(i));
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
	 * @param pOsmTag osm tag
	 * @return map tag created by osm tag
	 */
	protected MapTag createMapTagByOsmTag(OsmTag pOsmTag)
	{
		if (pOsmTag == null)
		{
			return null;
		}
		if (pOsmTag.getKey().isEmpty())
		{
			return null;
		}

		return new MapTag(pOsmTag.getKey(), pOsmTag.getValue());
	}
}
