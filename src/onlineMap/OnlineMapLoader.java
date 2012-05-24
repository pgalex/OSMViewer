package onlineMap;

import drawingStyle.StyleViewer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import map.*;
import map.exceptions.ConnectionErrorException;
import map.exceptions.MapBoundsIsNullRuntimeException;
import map.exceptions.MapIsNullRutimeException;
import map.exceptions.StyleViewerIsNullException;
import osmXml.OsmNode;
import osmXml.OsmTag;

/**
 * Creates connection to openstreetmap.org and loads map data and convertes it
 * to online map for rendering. Knows how to fill Map by osm objects
 *
 * @author pgalex
 */
public class OnlineMapLoader
{
	/**
	 * Parser for converting .osm xml data to map objects
	 */
	private OnlineOSMParser onlineParser;

	/**
	 * Default constructor
	 */
	public OnlineMapLoader()
	{
		onlineParser = new OnlineOSMParser();
	}

	/**
	 * Load osm data from web and fill map with loaded objects
	 *
	 * @param pLoadingSectorBounds bounds of loading map sector
	 * @param pStyleViewer viewer, uses to assing style index to objects
	 * @param pFillingMap map, where new object will be added
	 * @throws StyleViewerIsNullException style viewer is null
	 * @throws MapIsNullRutimeException online map is null
	 * @throws MapBoundsIsNullRuntimeException loading sector bounds is null
	 * @throws ConnectionErrorException error while connecting to osm server
	 * @throws NullPointerException some parameters are null
	 */
	public void loadToMap(MapBounds pLoadingSectorBounds, StyleViewer pStyleViewer,
					OnlineMap pFillingMap) throws StyleViewerIsNullException, MapIsNullRutimeException, MapBoundsIsNullRuntimeException,
					ConnectionErrorException
	{
		if (pLoadingSectorBounds == null)
			throw new MapBoundsIsNullRuntimeException();
		if (pStyleViewer == null)
			throw new StyleViewerIsNullException();
		if (pFillingMap == null)
			throw new MapIsNullRutimeException();

		if (pLoadingSectorBounds.isZero())
			return;

		String connectionString = "http://api.openstreetmap.org/api/0.6/map?bbox=35.7155,53.9239,35.7811,53.971";
		try
		{
			URL openStreetMapURL = new URL(connectionString);
			URLConnection openStreetMapConnection = openStreetMapURL.openConnection();
			onlineParser.convert(openStreetMapConnection.getInputStream());
			
			fillMapWithPoints(onlineParser.getParserNodes(), pStyleViewer, pFillingMap);
		}
		catch (MalformedURLException ex)
		{
			throw new ConnectionErrorException(connectionString);
		}
		catch (IOException ex)
		{
			// getting data error
		}
		catch (OutOfMemoryError ex)
		{
			// out of memory error
		}
		catch (Exception ex)
		{
			// parsing error
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
		if (pFillingMap == null)
			return;
		if (pStyleViewer == null)
			return;
		if (pNodes == null)
			return;
		if (pNodes.isEmpty())
			return;

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
			return null;
		if (pNode.getTags() == null)
			return null;
		if (pNode.getTags().isEmpty())
			return null;

		DefenitionTags creatingPointTags = createDefentionTagsByOsmTags(pNode.getTags());
		if (creatingPointTags == null)
			return null;

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
			return null;

		EditableDefenitionTags creatingTags = new EditableDefenitionTags();
		for (int i = 0; i < pOsmTags.size(); i++)
		{
			MapTag newTag = createMapTagByOsmTag(pOsmTags.get(i));
			if (newTag != null)
				creatingTags.add(newTag);
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
			return null;
		if (pOsmTag.getKey().isEmpty())
			return null;

		return new MapTag(pOsmTag.getKey(), pOsmTag.getValue());
	}
}
