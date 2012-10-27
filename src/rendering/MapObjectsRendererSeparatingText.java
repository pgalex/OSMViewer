package rendering;

import map.MapObjectsRenderer;
import drawingStyles.*;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import map.MapLine;
import map.MapPoint;
import map.MapPolygon;
import map.MapPosition;

/**
 * Objects renderer that drawes object on one objectsCanvas, and it's drawingText on
 * other
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
	 * Coordinates converter using for rendering
	 */
	private CoordinatesConverter coordinatesConverter;
	/**
	 * Scale level using for rendering
	 */
	private int renderingScaleLevel;

	/**
	 * Create renderer
	 *
	 * @param targetObjectsCanvas Canvas to draw map objects
	 * @param styleViewerForRendering Style viewer using to find drawing style of
	 * object
	 * @param converter object that will be using for coordinates converting while
	 * drawing
	 * @param renderingScaleLevel scale level using for rendering
	 * @throws IllegalArgumentException targetObjectsCanvas, styleViewerForRendering or
	 * converter is null
	 */
	public MapObjectsRendererSeparatingText(Graphics2D targetObjectsCanvas, StyleViewer styleViewerForRendering,
					CoordinatesConverter converter, int renderingScaleLevel) throws IllegalArgumentException
	{
		if (targetObjectsCanvas == null || styleViewerForRendering == null || converter == null)
		{
			throw new IllegalArgumentException();
		}

		objectsCanvas = targetObjectsCanvas;
		styleViewer = styleViewerForRendering;
		coordinatesConverter = converter;
		this.renderingScaleLevel = renderingScaleLevel;
	}

	/**
	 * Render point
	 *
	 * @param pointToRender point on a map
	 */
	@Override
	public void renderPoint(MapPoint pointToRender)
	{
		if (pointToRender == null)
		{
			return;
		}

		MapObjectDrawSettings objectStyle = styleViewer.getMapObjectDrawSettings(pointToRender.getStyleIndex());
		if (objectStyle == null)
		{
			return;
		}

		PointDrawSettings pointStyle = objectStyle.findPointDrawSettings(renderingScaleLevel);
		if (pointStyle == null)
		{
			return;
		}


		Point2D pointPositionOnCanvas = coordinatesConverter.goegraphicsToCanvas(pointToRender.getPosition());

		BufferedImage pointImage = pointStyle.getIcon();
		if (pointImage != null)
		{
			objectsCanvas.drawImage(pointImage, (int) (pointPositionOnCanvas.getX() - pointImage.getWidth() / 2),
							(int) (pointPositionOnCanvas.getY() - pointImage.getHeight() / 2), null);

			drawMapObjectTextUnderPoint(objectStyle, pointToRender.getDefenitionTags(),
							pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY() + pointImage.getHeight() / 2);
		}
		else
		{
			drawMapObjectTextAtPoint(objectStyle, pointToRender.getDefenitionTags(),
							pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY());
		}


	}

	/**
	 * Render line
	 *
	 * @param lineToRender line on a map
	 */
	@Override
	public void renderLine(MapLine lineToRender)
	{
		if (lineToRender == null)
		{
			return;
		}
		MapObjectDrawSettings objectStyle = styleViewer.getMapObjectDrawSettings(lineToRender.getStyleIndex());
		if (objectStyle == null)
		{
			return;
		}

		LineDrawSettings lineStyle = objectStyle.findLineDrawSettings(renderingScaleLevel);
		if (lineStyle == null)
		{
			return;
		}
	
		objectsCanvas.setStroke(lineStyle.getStroke());
		objectsCanvas.setColor(lineStyle.getColor());

		for (int i = 0; i < lineToRender.getPoints().length - 1; i++)
		{
			Point2D firstPoint = coordinatesConverter.goegraphicsToCanvas(lineToRender.getPoints()[i]);
			Point2D secondPoint = coordinatesConverter.goegraphicsToCanvas(lineToRender.getPoints()[i + 1]);

			objectsCanvas.drawLine((int) firstPoint.getX(), (int) firstPoint.getY(),
							(int) secondPoint.getX(), (int) secondPoint.getY());
		}

		// text ...
	}

	/**
	 * Render polygon
	 *
	 * @param polygonToRender polygon on a map
	 */
	@Override
	public void renderPolygon(MapPolygon polygonToRender)
	{
		if (polygonToRender == null)
		{
			return;
		}

		MapObjectDrawSettings objectStyle = styleViewer.getMapObjectDrawSettings(polygonToRender.getStyleIndex());
		if (objectStyle == null)
		{
			return;
		}

		PolygonDrawSettings polygonStyle = objectStyle.findPolygonDrawSettings(renderingScaleLevel);
		if (polygonStyle == null)
		{
			return;
		}

		Polygon drawingPolygon = createDrawingPolygonByMapPolygon(polygonToRender);

		// inner part
		objectsCanvas.setPaint(polygonStyle.getPaint());
		objectsCanvas.fillPolygon(drawingPolygon);

		// border
		LineDrawSettings borderStyle = polygonStyle.getBorderDrawSettings();
		objectsCanvas.setStroke(borderStyle.getStroke());
		objectsCanvas.setColor(borderStyle.getColor());
		objectsCanvas.drawPolygon(drawingPolygon);

		Point2D textPosition = coordinatesConverter.goegraphicsToCanvas(polygonToRender.getCenterPoint());

		drawMapObjectTextAtPoint(objectStyle, polygonToRender.getDefenitionTags(),
						textPosition.getX(), textPosition.getY());
	}

	/**
	 * Draw map object text(name, description etc) on objectsCanvas, by text center x and
	 * top bound of text
	 *
	 * @param objectStyle map object draw style
	 * @param objectTags map object tags using to find text
	 * @param textCenterX text center x on objectsCanvas
	 * @param textTop text top bound
	 */
	private void drawMapObjectTextUnderPoint(MapObjectDrawSettings objectStyle,
					DefenitionTags objectTags, double textCenterX, double textTop)
	{
		if (objectStyle == null || objectTags == null)
		{
			return;
		}

		TextDrawSettings textStyle = objectStyle.findTextDrawSettings(renderingScaleLevel);
		if (textStyle == null)
		{
			return;
		}

		String drawingText = objectStyle.findTextInTags(objectTags);
		if (!drawingText.isEmpty())
		{
			objectsCanvas.setColor(textStyle.getColor());
			objectsCanvas.setFont(textStyle.getFont());

			FontMetrics textFontMetrics = objectsCanvas.getFontMetrics(textStyle.getFont());
			int textWidth = textFontMetrics.stringWidth(drawingText);
			int textHeight = textFontMetrics.getHeight();

			objectsCanvas.drawString(drawingText, (int) textCenterX - textWidth / 2, (int) textTop + textHeight / 2);
		}
	}

	/**
	 * Draw map object text(name, description etc) on objectsCanvas, by point sets center
	 * of text
	 *
	 * @param objectStyle map object draw style
	 * @param objectTags map object tags using to find text
	 * @param textCenterX text center x on objectsCanvas
	 * @param textCenterY text center y on objectsCanvas
	 */
	private void drawMapObjectTextAtPoint(MapObjectDrawSettings objectStyle,
					DefenitionTags objectTags, double textCenterX, double textCenterY)
	{
		if (objectStyle == null || objectTags == null)
		{
			return;
		}

		TextDrawSettings textStyle = objectStyle.findTextDrawSettings(renderingScaleLevel);
		if (textStyle == null)
		{
			return;
		}

		String drawingText = objectStyle.findTextInTags(objectTags);
		if (!drawingText.isEmpty())
		{
			objectsCanvas.setColor(textStyle.getColor());
			objectsCanvas.setFont(textStyle.getFont());

			FontMetrics textFontMetrics = objectsCanvas.getFontMetrics(textStyle.getFont());
			int textWidth = textFontMetrics.stringWidth(drawingText);

			objectsCanvas.drawString(drawingText, (int) textCenterX - textWidth / 2, (int) textCenterY);
		}
	}

	/**
	 * Create polygon for drawing on objectsCanvas by converting MapPolygon points
	 *
	 * @param mapPolygon polygon on map
	 * @return polygon on objectsCanvas
	 */
	private Polygon createDrawingPolygonByMapPolygon(MapPolygon mapPolygon)
	{
		if (mapPolygon == null)
		{
			return new Polygon();
		}

		Polygon drawingPolygon = new Polygon();

		MapPosition[] mapPolygonPoints = mapPolygon.getPoints();
		for (int i = 0; i < mapPolygonPoints.length; i++)
		{
			Point2D pointOnCanvas = coordinatesConverter.goegraphicsToCanvas(mapPolygonPoints[i]);
			drawingPolygon.addPoint((int) pointOnCanvas.getX(), (int) pointOnCanvas.getY());
		}

		return drawingPolygon;
	}
}