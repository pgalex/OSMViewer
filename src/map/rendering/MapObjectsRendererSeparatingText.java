package map.rendering;

import drawingStyles.*;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import map.MapLine;
import map.MapPoint;
import map.MapPolygon;
import map.exceptions.CanvasIsNullException;
import map.exceptions.CoordinatesConverterIsNullException;
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
	 * Coordinates converter using for rendering
	 */
	private CoordinatesConverter coordinatesConverter;
	/**
	 * Scale level using for rendering
	 */
	private int scaleLevel;

	/**
	 * Constructor
	 *
	 * @param pCanvas Canvas to draw map objects
	 * @param pStyleViewer Style viewer using to find drawing style of object
	 * @param pCoordinatesConverter object that will be using for coordinates
	 * converting while drawing
	 * @param pScaleLevel scale level using for rendering
	 * @throws CanvasIsNullException object canvas is null
	 * @throws StyleViewerIsNullException style viewer is null
	 * @throws CoordinatesConverterIsNullException coordinates converter is null
	 */
	public MapObjectsRendererSeparatingText(Graphics2D pCanvas, StyleViewer pStyleViewer,
					CoordinatesConverter pCoordinatesConverter, int pScaleLevel) throws CanvasIsNullException, StyleViewerIsNullException, CoordinatesConverterIsNullException
	{
		if (pCanvas == null)
		{
			throw new CanvasIsNullException();
		}
		if (pStyleViewer == null)
		{
			throw new StyleViewerIsNullException();
		}
		if (pCoordinatesConverter == null)
		{
			throw new CoordinatesConverterIsNullException();
		}

		canvas = pCanvas;
		styleViewer = pStyleViewer;
		coordinatesConverter = pCoordinatesConverter;
		scaleLevel = pScaleLevel;
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
		{
			return;
		}

		MapObjectDrawStyle objectStyle = styleViewer.findMapObjectDrawStyle(pPoint.getStyleIndex());
		if (objectStyle == null)
		{
			return;
		}

		PointDrawStyle pointStyle = objectStyle.findPointDrawStyle(scaleLevel);
		if (pointStyle == null)
		{
			return;
		}


		Point2D pointPositionOnCanvas = coordinatesConverter.goegraphicsToCanvas(pPoint.getPosition());

		BufferedImage pointImage = pointStyle.getIcon();
		if (pointImage != null)
		{
			canvas.drawImage(pointImage, (int) (pointPositionOnCanvas.getX() - pointImage.getWidth() / 2),
							(int) (pointPositionOnCanvas.getY() - pointImage.getHeight() / 2), null);
		}

		TextDrawStyle textStyle = objectStyle.findTextDrawStyle(scaleLevel);
		String text = objectStyle.findTextInTags(pPoint.getDefenitionTags());
		
		if (!text.isEmpty())
		{
			canvas.setColor(textStyle.getColor());
			canvas.setFont(textStyle.getFont());

			FontMetrics textFontMetrics = canvas.getFontMetrics(textStyle.getFont());
			int textWidth = textFontMetrics.stringWidth(text);

			canvas.drawString(text, (int) pointPositionOnCanvas.getX() - textWidth / 2, (int) pointPositionOnCanvas.getY());
		}

		// получить параметры отрисовки точки
		// получить из параметров значок
		// если значок есть нарисовать значок
		// получить параметры для отображения текста
		// найти в тегах текст который может отображать как подпись
		// отобразить текст
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
