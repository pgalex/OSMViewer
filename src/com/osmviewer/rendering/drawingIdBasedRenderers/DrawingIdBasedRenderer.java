package com.osmviewer.rendering.drawingIdBasedRenderers;

import com.osmviewer.rendering.CoordinatesConverter;
import com.osmviewer.rendering.DrawSettingsFinder;
import com.osmviewer.rendering.DrawingTool;
import com.osmviewer.rendering.RenderableMapObject;

/**
 * Renderer for some kind of map object
 *
 * @author preobrazhentsev
 */
public abstract class DrawingIdBasedRenderer
{
	public abstract void renderMapObject(RenderableMapObject mapObjectToRender, DrawSettingsFinder drawSettingsFinder,
					CoordinatesConverter coordinatesConverter, DrawingTool drawingTool) throws IllegalArgumentException;
}
