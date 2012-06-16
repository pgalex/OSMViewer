package map;

import drawingStyles.StyleViewer;
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
	private Graphics2D canvas;
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
	 * @param pCanvas Canvas to draw map objects
	 * @param pStyleViewer Style viewer using to find drawing style of object
	 * @param pScaleLevel start scale level using for drawing
	 * @throws CanvasIsNullException object canvas is null
	 * @throws StyleViewerIsNullException style viewer is null
	 */
	public MapObjectsRendererSeparatingText(Graphics2D pCanvas, StyleViewer pStyleViewer,
					int pScaleLevel) throws CanvasIsNullException, StyleViewerIsNullException
	{
		if (pCanvas == null)
			throw new CanvasIsNullException();
		if (pStyleViewer == null)
			throw new StyleViewerIsNullException();

		canvas = pCanvas;
		styleViewer = pStyleViewer;
		currentScaleLevel = pScaleLevel;
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
		// получение настрооек отрисвки (PointDrawStyle, текста и т.п.) сделать как метод 
		// MapObjectStyle, который будет получен по индексу
		canvas.drawRect((int) Math.round(pPoint.getPosition().getLatitude()),
						(int) Math.round(pPoint.getPosition().getLongitude()), 4, 4);
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
