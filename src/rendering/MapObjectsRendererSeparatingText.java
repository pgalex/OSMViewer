package rendering;

import drawingStyles.*;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import map.MapLine;
import map.MapObjectsRenderer;
import map.MapPoint;
import map.MapPolygon;
import map.MapPosition;

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
	private Graphics2D objectsCanvas;
	/**
	 * Canvas for text drawing
	 */
	private TextCanvas textCanvas;
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
	 * @param targetTextCanvas canvas where draw text
	 * @param styleViewerForRendering Style viewer using to find how to draw
	 * objects
	 * @param converterForRendering object that will be using for coordinates
	 * converting while drawing
	 * @param scaleLevelForRendering scale level using for rendering
	 * @throws IllegalArgumentException targetObjectsCanvas,
	 * styleViewerForRendering or converterForRendering is null
	 */
	public MapObjectsRendererSeparatingText(Graphics2D targetObjectsCanvas,
					Graphics2D targetTextCanvas, StyleViewer styleViewerForRendering,
					CoordinatesConverter converterForRendering, int scaleLevelForRendering) throws IllegalArgumentException
	{
		if (targetObjectsCanvas == null)
		{
			throw new IllegalArgumentException();
		}
		if (targetTextCanvas == null)
		{
			throw new IllegalArgumentException();
		}
		if (styleViewerForRendering == null)
		{
			throw new IllegalArgumentException();
		}
		if (converterForRendering == null)
		{
			throw new IllegalArgumentException();
		}


		objectsCanvas = targetObjectsCanvas;
		textCanvas = new TextCanvas(targetTextCanvas);
		styleViewer = styleViewerForRendering;
		coordinatesConverter = converterForRendering;
		renderingScaleLevel = scaleLevelForRendering;
	}

	/**
	 * Render point
	 *
	 * @param pointToRender point for rendering
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

			TextDrawSettings textDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
			if (textDrawSettings != null)
			{
				textCanvas.drawTextUnderPoint(objectStyle.findTextInTags(pointToRender.getDefenitionTags()),
								textDrawSettings,
								pointPositionOnCanvas.getX(),
								pointPositionOnCanvas.getY() + pointImage.getHeight() / 2);
			}
		}
		else
		{
			TextDrawSettings textDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
			if (textDrawSettings != null)
			{
				textCanvas.drawTextAtPoint(objectStyle.findTextInTags(pointToRender.getDefenitionTags()),
								textDrawSettings,
								pointPositionOnCanvas.getX(),
								pointPositionOnCanvas.getY());
			}
		}


	}

	/**
	 * Render line
	 *
	 * @param lineToRender line for rendering
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
	 * @param polygonToRender polygon for rendering
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

		TextDrawSettings textDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
		if (textDrawSettings != null)
		{
			textCanvas.drawTextAtPoint(objectStyle.findTextInTags(polygonToRender.getDefenitionTags()),
							textDrawSettings,
							textPosition.getX(),
							textPosition.getY());
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
