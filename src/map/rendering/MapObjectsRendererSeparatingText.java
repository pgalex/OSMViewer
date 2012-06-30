package map.rendering;

import drawingStyles.DefenitionTags;
import drawingStyles.StyleViewer;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import map.MapLine;
import map.MapPoint;
import map.MapPolygon;
import map.MapTag;
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
	private Graphics2D canvas;
	/**
	 * Style viewer using to find drawing style of object
	 */
	private StyleViewer styleViewer;
	private CoordinatesConverter coordinatesConverter;

	/**
	 * Constructor
	 *
	 * @param pCanvas Canvas to draw map objects
	 * @param pStyleViewer Style viewer using to find drawing style of object
	 * @param pCoordinatesConverter object that will be using for coordinates
	 * converting while drawing
	 * @throws CanvasIsNullException object canvas is null
	 * @throws StyleViewerIsNullException style viewer is null
	 */
	public MapObjectsRendererSeparatingText(Graphics2D pCanvas, StyleViewer pStyleViewer,
					CoordinatesConverter pCoordinatesConverter) throws CanvasIsNullException, StyleViewerIsNullException
	{
		if (pCanvas == null)
			throw new CanvasIsNullException();
		if (pStyleViewer == null)
			throw new StyleViewerIsNullException();

		canvas = pCanvas;
		styleViewer = pStyleViewer;
		coordinatesConverter = pCoordinatesConverter;
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
		if (pPoint.getStyleIndex() == null)
			return;
		Point2D positionOnDrawingCanvas = coordinatesConverter.goegraphicsToCanvas(pPoint.getPosition());

		canvas.setColor(Color.RED);
		canvas.drawRect((int) positionOnDrawingCanvas.getX(), (int) positionOnDrawingCanvas.getY(),
						2, 2);

		DefenitionTags tags = pPoint.getDefenitionTags();
		for (int i = 0; i < tags.size(); i++)
		{
			MapTag tag = tags.get(i);
			if (tag.getKey().equals("name"))
				canvas.drawString(tag.getValue(), (int) positionOnDrawingCanvas.getX(), (int) positionOnDrawingCanvas.getY());
		}
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
