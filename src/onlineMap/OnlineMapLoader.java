package onlineMap;

import drawingStyle.StyleViewer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import map.*;
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
	 * @throws NullPointerException some parameters are null
	 */
	public void loadToMap(MapBounds pLoadingSectorBounds, StyleViewer pStyleViewer,
					OnlineMap pFillingMap) throws StyleViewerIsNullException, MapIsNullRutimeException, MapBoundsIsNullRuntimeException
	{
		if (pLoadingSectorBounds == null)
			throw new MapBoundsIsNullRuntimeException();
		if (pStyleViewer == null)
			throw new StyleViewerIsNullException();
		if (pFillingMap == null)
			throw new MapIsNullRutimeException();
		
		if (pLoadingSectorBounds.isZero())
			return;
		
		try
		{
			URL openStreetMapURL = new URL("http://api.openstreetmap.org/api/0.6/map?bbox=35.7155,53.9239,35.7811,53.971");;
			URLConnection openStreetMapConnection = openStreetMapURL.openConnection();
			onlineParser.convert(openStreetMapConnection.getInputStream());
			fillMapWithPoints(onlineParser.getParserNodes(), pFillingMap);
		}
		catch (MalformedURLException ex)
		{
			// connection error
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
	 * @param pFillingMap map, filling with map points, created by osm nodes
	 */
	private void fillMapWithPoints(ArrayList<OsmNode> pNodes, OnlineMap pFillingMap)
	{
		if (pFillingMap == null)
			return;
		if (pNodes == null)
			return;
		if (pNodes.isEmpty())
			return;
		
		for (OsmNode currentNode : pNodes)
		{
			MapPoint newPoint = createMapPointByOsmNode(currentNode);
			if (newPoint != null)
				pFillingMap.addObject(newPoint);
		}
	}

	/**
	 * Creates map point by osm node
	 *
	 * @param pNode osm node
	 * @return map point created by osm node
	 */
	private MapPoint createMapPointByOsmNode(OsmNode pNode)
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
	private DefenitionTags createDefentionTagsByOsmTags(ArrayList<OsmTag> pOsmTags)
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
	private MapTag createMapTagByOsmTag(OsmTag pOsmTag)
	{
		if (pOsmTag == null)
			return null;
		if (pOsmTag.getKey().isEmpty() || pOsmTag.getValue().isEmpty())
			return null;
		
		return new MapTag(pOsmTag.getKey(), pOsmTag.getValue());
	}
}
