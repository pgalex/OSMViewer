package com.osmviewer.rendering.drawingIdBasedRenderers;

import com.osmviewer.rendering.CoordinatesConverter;
import com.osmviewer.rendering.DrawSettingsFinder;
import com.osmviewer.rendering.DrawingTool;
import com.osmviewer.rendering.RenderableMapObject;
import java.awt.Polygon;
import java.awt.geom.Point2D;

/**
 * Renderer of for polygon-like objects
 *
 * @author preobrazhentsev
 */
public class PolygonRenderer extends DrawingIdBasedRenderer
{

	@Override
	public void renderMapObject(RenderableMapObject mapObjectToRender, DrawSettingsFinder drawSettingsFinder,
					CoordinatesConverter coordinatesConverter, DrawingTool drawingTool) throws IllegalArgumentException
	{
		PolygonDrawSettings drawSettings = drawSettingsFinder.findPolygonDrawSettings(mapObjectToRender.getDrawingId());
		if (drawSettings == null)
		{
			return;
		}

		Polygon drawingPolygon = new Polygon();
		for (int i = 0; i < mapObjectToRender.getPointsCount(); i++)
		{
			Point2D pointOnCanvas = coordinatesConverter.goegraphicsToCanvas(mapObjectToRender.getPoint(i));
			drawingPolygon.addPoint((int) pointOnCanvas.getX(), (int) pointOnCanvas.getY());
		}

		drawingTool.drawColorFilledPolygon(drawingPolygon, drawSettings.getFillColor());
	}

}
