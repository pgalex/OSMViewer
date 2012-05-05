package map;

import drawingStyle.*;
import java.awt.Graphics2D;
import map.exceptions.CanvasIsNullException;
import map.exceptions.StyleViewerIsNullException;

/**
 * Objects renderer that drawes object on one canvas, and it's text on other
 *
 * @author pgalex
 */
public class MapObjectsRendererSeparatingText implements MapObjectsRenderer
{
	/**
	 * Canvas to draw map objects
	 */
	private Graphics2D objectsCanvas;
	/**
	 * Style viewer using to find drawing style of object
	 */
	private StyleViewer styleViewer;
	/**
	 * Scale level using for drawing
	 */
	private int currentScaleLevel;

	/**
	 * Constructor
	 *
	 * @param pObjectsCanvas Canvas to draw map objects
	 * @param pStyleViewer Style viewer using to find drawing style of object
	 * @param pStartScaleLevel start scale level using for drawing
	 * @throws CanvasIsNullException object canvas is null
	 * @throws StyleViewerIsNullException style viewer is null
	 */
	public MapObjectsRendererSeparatingText(Graphics2D pObjectsCanvas, StyleViewer pStyleViewer,
					int pStartScaleLevel) throws CanvasIsNullException, StyleViewerIsNullException
	{
		if (pObjectsCanvas == null)
			throw new CanvasIsNullException();
		if (pStyleViewer == null)
			throw new StyleViewerIsNullException();

		objectsCanvas = pObjectsCanvas;
		styleViewer = pStyleViewer;
		currentScaleLevel = pStartScaleLevel;
	}

	/**
	 * Render point
	 *
	 * @param pPoint point on a map
	 */
	@Override
	public void renderPoint(MapPoint pPoint)
	{
		if (pPoint == null)
			return;
	}

	/**
	 * Render line
	 *
	 * @param pLine line on a map
	 */
	@Override
	public void renderLine(MapLine pLine)
	{
		if (pLine == null)
			return;
		/*if (pLine.getStyleIndex() == null)
			return;

		MapObjectStyle drawingStyle = styleViewer.getMapObjectStyle(pLine.getStyleIndex());
		if (drawingStyle == null)
			return;
		if (!drawingStyle.isCanBeLine())
			return;

		ScaledObjectStyleCollection scaledStyles = drawingStyle.getScaledStyles();
		if (scaledStyles == null)
			return;

		ScaledObjectStyle styleOnCurrentScale = scaledStyles.getStyleOnScale(currentScaleLevel);
		if (styleOnCurrentScale == null)
			return;
		if (!styleOnCurrentScale.isDrawLine())
			return;

		LineDrawStyle lineStyle = styleOnCurrentScale.getLineStyle();
		if (lineStyle == null)
			return;

		MapPosition[] linePoints = pLine.getPoints();
		if (linePoints == null)
			return;

		for (int i = 0; i < linePoints.length - 1; i++)
		{
			objectsCanvas.setColor(lineStyle.getColor().getColor());
			objectsCanvas.drawLine((int) linePoints[i].getLatitude(), (int) linePoints[i].getLongitude(),
							(int) linePoints[i + 1].getLatitude(), (int) linePoints[i + 1].getLongitude());
		}*/

	}

	/**
	 * Render polygon
	 *
	 * @param pPolygon polygon on a map
	 */
	@Override
	public void renderPolygon(MapPolygon pPolygon)
	{
		if (pPolygon == null)
			return;
	}
}
