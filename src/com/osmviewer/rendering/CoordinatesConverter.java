package com.osmviewer.rendering;

import java.awt.geom.Point2D;
import com.osmviewer.mapDefenitionUtilities.MapPosition;

/**
 * Object that can be using to convert coordinates by its view parameters
 *
 * @author pgalex
 */
public interface CoordinatesConverter
{
	/**
	 * Convert point on a map to point on drawing canvas
	 *
	 * @param positionOnMap position of point on a map in geographics coordinates
	 * @return position of point on drawing canvas in pixels
	 * @throws IllegalArgumentException positionOnMap is null
	 */
	public Point2D goegraphicsToCanvas(MapPosition positionOnMap) throws IllegalArgumentException;

	/**
	 * Convert point on drawing canvas to point on a map
	 *
	 * @param positionOnCanvas position of point on drawing canvas in pixels
	 * @return position of point on map in geographics coordinates
	 * @throws IllegalArgumentException positionOnCanvas is null
	 */
	public MapPosition canvasToGeographics(Point2D positionOnCanvas) throws IllegalArgumentException;
}
