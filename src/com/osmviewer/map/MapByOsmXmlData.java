package com.osmviewer.map;

import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.osmXml.OsmWay;
import com.osmviewer.osmXml.OsmXmlParser;
import com.osmviewer.osmXml.exceptions.ParsingOsmErrorException;
import com.osmviewer.osmXmlParsing.SAXOsmXmlParser;
import com.osmviewer.rendering.RenderableMap;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import com.osmviewer.rendering.RenderableMapObjectsVisitor;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Map, creating by .osm xml data, loading all parsed objects
 *
 * @author pgalex
 */
public class MapByOsmXmlData implements RenderableMap
{
	/**
	 * Objects on a map
	 */
	private ArrayList<MapObject> objects;

	/**
	 * Create empty map
	 */
	public MapByOsmXmlData()
	{
		objects = new ArrayList<MapObject>();
	}

	/**
	 * Load map from input stream of osm xml data
	 *
	 * @param input input stream of osm xml data
	 * @param drawSettingsFinder finder of draw settings
	 * @throws IllegalArgumentException input or drawSettingsFinderis null
	 * @throws ParsingOsmErrorException error while parsing
	 */
	public void loadFromStream(InputStream input, RenderableMapObjectsDrawSettingsFinder drawSettingsFinder) throws IllegalArgumentException, ParsingOsmErrorException
	{
		if (input == null)
		{
			throw new IllegalArgumentException("input is null");
		}
		if (drawSettingsFinder == null)
		{
			throw new IllegalArgumentException("drawSettingsFinder is null");
		}

		objects = new ArrayList<MapObject>();

		OsmParsingResultsCollector parsingResultsCollector = new OsmParsingResultsCollector();
		OsmXmlParser osmParser = new SAXOsmXmlParser();
		osmParser.parse(input, parsingResultsCollector);

		ArrayList<OsmNode> parsedNodes = parsingResultsCollector.getTakedNodes();
		for (OsmNode osmNode : parsedNodes)
		{
			MapPoint pointByNode = OsmMapObjectsConverter.createMapPointByOsmNode(osmNode);
			if (pointByNode == null)
			{
				continue;
			}

			RenderableMapObjectDrawSettings pointDrawSettings = drawSettingsFinder.findMapObjectDrawSettings(pointByNode.getDefenitionTags());
			if (pointDrawSettings != null)
			{
				pointByNode.setDrawSettings(pointDrawSettings);
				objects.add(pointByNode);
			}
		}

		ArrayList<OsmWay> parsedWays = parsingResultsCollector.getTakedWays();
		for (OsmWay osmWay : parsedWays)
		{
			MapObject mapObjectByWay = OsmMapObjectsConverter.createMapObjectByWay(osmWay, parsedNodes);
			if (mapObjectByWay == null)
			{
				continue;
			}

			RenderableMapObjectDrawSettings objectByWayDrawSettings = drawSettingsFinder.findMapObjectDrawSettings(mapObjectByWay.getDefenitionTags());
			if (objectByWayDrawSettings != null)
			{
				mapObjectByWay.setDrawSettings(objectByWayDrawSettings);
				objects.add(mapObjectByWay);
			}
		}
	}

	/**
	 * Render all map objects, visible in area, with renderer. Object should be
	 * given to objectsVisitor by its draw priority
	 *
	 * @param objectsVisitor objects renderer
	 * @param area area to determine which object need give to objectsVisitor
	 * @param objectsDrawPriorityComparator comparator for sorting rendering
	 * objects by its draw priority
	 * @throws IllegalArgumentException objectsVisitor, area or
	 * objectsDrawPriorityComparator is null
	 */
	@Override
	public void renderObjectsByDrawPriority(RenderableMapObjectsVisitor objectsVisitor, MapBounds area,
					RenderableMapObjectsDrawPriorityComparator objectsDrawPriorityComparator) throws IllegalArgumentException
	{
		if (objectsVisitor == null)
		{
			throw new IllegalArgumentException("objectsVisitor is null");
		}
		if (area == null)
		{
			throw new IllegalArgumentException("area is null");
		}
		if (objectsDrawPriorityComparator == null)
		{
			throw new IllegalArgumentException("objectsDrawPriorityComparator is null");
		}

		// nothing to visit
		if (area.isZero())
		{
			return;
		}

		Collections.sort(objects, objectsDrawPriorityComparator);

		for (int i = 0; i < objects.size(); i++)
		{
			MapObject renderingObject = objects.get(i);
			if (renderingObject.isVisibleInArea(area))
			{
				renderingObject.acceptRenderingVisitor(objectsVisitor);
			}
		}
	}
}
