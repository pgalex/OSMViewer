package map.rendering;

import drawingStyles.*;
import java.awt.BasicStroke;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import map.MapLine;
import map.MapPoint;
import map.MapPolygon;
import map.MapPosition;
import map.exceptions.CanvasIsNullException;
import map.exceptions.CoordinatesConverterIsNullException;
import map.exceptions.StyleViewerIsNullException;

/**
 * Objects renderer that drawes object on one canvas, and it's drawingText on
 * other
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

			drawMapObjectTextUnderPoint(objectStyle, pPoint.getDefenitionTags(),
							pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY() + pointImage.getHeight() / 2);
		}
		else
		{
			drawMapObjectTextAtPoint(objectStyle, pPoint.getDefenitionTags(),
							pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY());
		}


	}

	/**
	 * Draw map object text(name, description etc) on canvas, by text center x and
	 * top bound of text
	 *
	 * @param pMapObjectStyle map object draw style
	 * @param pMapObjectTags map object tags using to find text
	 * @param textCenterX text center x on canvas
	 * @param textTop text top bound
	 */
	private void drawMapObjectTextUnderPoint(MapObjectDrawStyle pMapObjectStyle,
					DefenitionTags pMapObjectTags, double textCenterX, double textTop)
	{
		if (pMapObjectStyle == null || pMapObjectTags == null)
		{
			return;
		}

		TextDrawStyle textStyle = pMapObjectStyle.findTextDrawStyle(scaleLevel);
		if (textStyle == null)
		{
			return;
		}

		String drawingText = pMapObjectStyle.findTextInTags(pMapObjectTags);
		if (!drawingText.isEmpty())
		{
			canvas.setColor(textStyle.getColor());
			canvas.setFont(textStyle.getFont());

			FontMetrics textFontMetrics = canvas.getFontMetrics(textStyle.getFont());
			int textWidth = textFontMetrics.stringWidth(drawingText);
			int textHeight = textFontMetrics.getHeight();

			canvas.drawString(drawingText, (int) textCenterX - textWidth / 2, (int) textTop + textHeight / 2);
		}
	}

	/**
	 * Draw map object text(name, description etc) on canvas, by point sets center
	 * of text
	 *
	 * @param pMapObjectStyle map object draw style
	 * @param pMapObjectTags map object tags using to find text
	 * @param textCenterX text center x on canvas
	 * @param textCenterY text center y on canvas
	 */
	private void drawMapObjectTextAtPoint(MapObjectDrawStyle pMapObjectStyle,
					DefenitionTags pMapObjectTags, double textCenterX, double textCenterY)
	{
		if (pMapObjectStyle == null || pMapObjectTags == null)
		{
			return;
		}

		TextDrawStyle textStyle = pMapObjectStyle.findTextDrawStyle(scaleLevel);
		if (textStyle == null)
		{
			return;
		}

		String drawingText = pMapObjectStyle.findTextInTags(pMapObjectTags);
		if (!drawingText.isEmpty())
		{
			canvas.setColor(textStyle.getColor());
			canvas.setFont(textStyle.getFont());

			FontMetrics textFontMetrics = canvas.getFontMetrics(textStyle.getFont());
			int textWidth = textFontMetrics.stringWidth(drawingText);

			canvas.drawString(drawingText, (int) textCenterX - textWidth / 2, (int) textCenterY);
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
		{
			return;
		}

		/*MapObjectDrawStyle objectStyle = styleViewer.findMapObjectDrawStyle(pPolygon.getStyleIndex());
		if (objectStyle == null)
		{
			return;
		}

		PolygonDrawStyle polygonStyle = objectStyle.findPolygonDrawStyle(scaleLevel);
		if (polygonStyle == null)
		{
			return;
		}
		
		LineDrawStyle borderStyle = polygonStyle.getBorderDrawStyle();
		
		canvas.setStroke(new BasicStroke(borderStyle.getWidth(), BasicStroke.CAP_ROUND, 
						BasicStroke.JOIN_ROUND, 1.0f, borderStyle.getPattern(), 0.0f));
		canvas.setColor(borderStyle.getColor());
		
		canvas.fillPolygon(createDrawingPolygonByMapPolygon(pPolygon));*/

	}

	/**
	 * Create polygon for drawing on canvas by converting MapPolygon points
	 *
	 * @param pPolygon polygon on map
	 * @return polygon on canvas
	 */
	private Polygon createDrawingPolygonByMapPolygon(MapPolygon pPolygon)
	{
		if (pPolygon == null)
		{
			return new Polygon();
		}

		Polygon drawingPolygon = new Polygon();

		MapPosition[] mapPolygonPoints = pPolygon.getPoints();
		for (int i = 0; i < mapPolygonPoints.length; i++)
		{
			Point2D pointOnCanvas = coordinatesConverter.goegraphicsToCanvas(mapPolygonPoints[i]);
			drawingPolygon.addPoint((int) pointOnCanvas.getX(), (int) pointOnCanvas.getY());
		}
		
		return drawingPolygon;
	}
}
