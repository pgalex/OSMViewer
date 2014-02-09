package com.osmviewer.rendering;

import com.osmviewer.map.MapObject;
import com.osmviewer.rendering.drawingIdBasedRenderers.DrawingIdBasedRenderer;
import com.osmviewer.rendering.drawingIdBasedRenderers.RenderersFactory;
import com.osmviewer.rendering.selectng.SelectingBuffer;

/**
 * Renderer of map objects that uses drawing id to find how to draw map object
 *
 * @author pgalex
 */
public class SeparatingByDrawingIdMapObjectsRenderer implements MapObjectsRenderer
{
	/**
	 * Drawing tool to render map map objects with
	 */
	private DrawingTool drawingTool;
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
	 * Finds draw settings for each type of map object
	 */
	private DrawSettingsFinder drawSettingsFinder;

	/**
	 * Create map objects renderer
	 *
	 * @param drawingTool drawing tool to render map map objects with. Must be not null
	 * @param coordinatesConverter object that will be using for coordinates
	 * converting while drawing
	 * @param renderingScaleLevel scale level using for rendering
	 * @param selectingBuffer buffer where will be added selecting objects,
	 * created by drawen objects
	 * @param drawSettingsFinder finder of draw settings. Must be not null
	 * @throws IllegalArgumentException drawingTool is null, targetTextCanvas is null,
	 * renderingCoordinatesConverter is null or fillingSelectingBuffer is null;
	 * drawSettingsFinder is null
	 */
	public SeparatingByDrawingIdMapObjectsRenderer(DrawingTool drawingTool, CoordinatesConverter coordinatesConverter,
					int renderingScaleLevel, SelectingBuffer selectingBuffer, DrawSettingsFinder drawSettingsFinder) throws IllegalArgumentException
	{
		if (drawingTool == null)
		{
			throw new IllegalArgumentException("drawingTool is null");
		}
		if (coordinatesConverter == null)
		{
			throw new IllegalArgumentException("coordinatesConverter is null");
		}
		if (selectingBuffer == null)
		{
			throw new IllegalArgumentException("fillingSelectingBuffer is null");
		}
		if (drawSettingsFinder == null)
		{
			throw new IllegalArgumentException("drawSettingsFinder is null");
		}

		this.drawingTool = drawingTool;
		this.coordinatesConverter = coordinatesConverter;
		this.renderingScaleLevel = renderingScaleLevel;
		this.selectingBuffer = selectingBuffer;
		this.drawSettingsFinder = drawSettingsFinder;
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

		String rendererType = drawSettingsFinder.findRendererType(mapObjectToRender.getDrawingId());
		if (rendererType == null)
		{
			return;
		}

		DrawingIdBasedRenderer renderer = RenderersFactory.createRenderer(rendererType);
		if (renderer != null)
		{
			renderer.renderMapObject(mapObjectToRender, drawSettingsFinder, coordinatesConverter, drawingTool);
		}
	}
}
