package com.osmviewer.rendering;

import com.osmviewer.map.MapObject;
import com.osmviewer.rendering.selectng.SelectingBuffer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;

/**
 * Renderer of map objects that uses drawing id to find how to draw map object
 *
 * @author pgalex
 */
public class DrawingIdBasedMapObjectsRenderer implements MapObjectsRenderer
{
	/**
	 * Canvas to draw map objects
	 */
	private Graphics2D objectsCanvas;
	/**
	 * Coordinates converter to target canvas coordinates
	 */
	private CoordinatesConverter coordinatesConverter;
	/**
	 * Scale level using for rendering
	 */
	private int renderingScaleLevel;
	/**
	 * Buffer where will be added selecting objects, created by drawen objects
	 */
	private SelectingBuffer selectingBuffer;

	/**
	 * Create map objects renderer
	 *
	 * @param targetObjectsCanvas canvas to draw map objects
	 * @param renderingCoordinatesConverter object that will be using for
	 * coordinates converting while drawing
	 * @param rederingScaleLevel scale level using for rendering
	 * @param fillingSelectingBuffer buffer where will be added selecting objects,
	 * created by drawen objects
	 * @throws IllegalArgumentException targetObjectsCanvas, targetTextCanvas,
	 * renderingCoordinatesConverter, fillingSelectingBuffer is null
	 */
	public DrawingIdBasedMapObjectsRenderer(Graphics2D targetObjectsCanvas,
					CoordinatesConverter renderingCoordinatesConverter,
					int rederingScaleLevel, SelectingBuffer fillingSelectingBuffer) throws IllegalArgumentException
	{
		if (targetObjectsCanvas == null)
		{
			throw new IllegalArgumentException("targetObjectsCanvas is null");
		}
		if (renderingCoordinatesConverter == null)
		{
			throw new IllegalArgumentException("renderingCoordinatesConverter is null");
		}
		if (fillingSelectingBuffer == null)
		{
			throw new IllegalArgumentException("fillingSelectingBuffer is null");
		}

		objectsCanvas = targetObjectsCanvas;
		coordinatesConverter = renderingCoordinatesConverter;
		renderingScaleLevel = rederingScaleLevel;

		selectingBuffer = fillingSelectingBuffer;
	}

	/**
	 * Render given map object
	 *
	 * @param mapObjectToRender rendering map object. Must be not null
	 * @throws IllegalArgumentException mapObjectToRender is null
	 */
	@Override
	public void renderMapObject(MapObject mapObjectToRender) throws IllegalArgumentException
	{
		if (mapObjectToRender == null)
		{
			throw new IllegalArgumentException("mapObjectToRender is null");
		}

		Polygon drawingPolygon = new Polygon();

		for (int i = 0; i < mapObjectToRender.getPointsCount(); i++)
		{
			Point2D pointOnCanvas = coordinatesConverter.goegraphicsToCanvas(mapObjectToRender.getPoint(i));
			drawingPolygon.addPoint((int) pointOnCanvas.getX(), (int) pointOnCanvas.getY());
		}

		objectsCanvas.setPaint(Color.GREEN);
		objectsCanvas.fillPolygon(drawingPolygon);
	}
}
