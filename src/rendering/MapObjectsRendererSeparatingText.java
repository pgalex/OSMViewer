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
	 * @throws IllegalArgumentException pointToRender is null
	 */
	@Override
	public void renderPoint(MapPoint pointToRender) throws IllegalArgumentException
	{
		if (pointToRender == null)
		{
			throw new IllegalArgumentException();
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
	 * @throws IllegalArgumentException lineToRender is null
	 */
	@Override
	public void renderLine(MapLine lineToRender) throws IllegalArgumentException
	{
		if (lineToRender == null)
		{
			throw new IllegalArgumentException();
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

		Point2D[] drawingMultiline = createDrawingMultilineByMapLine(lineToRender);

		objectsCanvas.setStroke(lineStyle.getStroke());
		objectsCanvas.setColor(lineStyle.getColor());
		for (int i = 0; i < drawingMultiline.length - 1; i++)
		{
			Point2D firstPoint = drawingMultiline[i];
			Point2D secondPoint = drawingMultiline[i + 1];

			objectsCanvas.drawLine((int) firstPoint.getX(), (int) firstPoint.getY(),
							(int) secondPoint.getX(), (int) secondPoint.getY());
		}

		TextDrawSettings textDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
		if (textDrawSettings != null)
		{
			textCanvas.drawTextOnMultiline(objectStyle.findTextInTags(lineToRender.getDefenitionTags()), 
							textDrawSettings, 
							drawingMultiline);
		}
	}

	/**
	 * Create multiline for drawing by converting each point of map line to canvas
	 * coordinates
	 *
	 * @param mapLine line on map
	 * @return multiline by mapLine, defined by points array
	 * @throws IllegalArgumentException mapLine is null
	 */
	private Point2D[] createDrawingMultilineByMapLine(MapLine mapLine) throws IllegalArgumentException
	{
		if (mapLine == null)
		{
			throw new IllegalArgumentException();
		}

		Point2D[] drawingMultiline = new Point2D[mapLine.getPointsCount()];

		for (int i = 0; i < mapLine.getPointsCount(); i++)
		{
			drawingMultiline[i] = coordinatesConverter.goegraphicsToCanvas(mapLine.getPoint(i));
		}

		return drawingMultiline;
	}

	/**
	 * Render polygon
	 *
	 * @param polygonToRender polygon for rendering
	 * @throws IllegalArgumentException polygonToRender is null
	 */
	@Override
	public void renderPolygon(MapPolygon polygonToRender) throws IllegalArgumentException
	{
		if (polygonToRender == null)
		{
			throw new IllegalArgumentException();
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

		if (polygonStyle.isDrawInnerPart())
		{
			objectsCanvas.setPaint(polygonStyle.getPaint());
			objectsCanvas.fillPolygon(drawingPolygon);
		}

		if (polygonStyle.isDrawBorder())
		{
			LineDrawSettings borderStyle = polygonStyle.getBorderDrawSettings();
			objectsCanvas.setStroke(borderStyle.getStroke());
			objectsCanvas.setColor(borderStyle.getColor());
			objectsCanvas.drawPolygon(drawingPolygon);
		}


		TextDrawSettings textDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
		if (textDrawSettings != null)
		{
			double textPositionX = drawingPolygon.getBounds2D().getCenterX();
			double textPositionY = drawingPolygon.getBounds2D().getCenterY();
			textCanvas.drawTextAtPoint(objectStyle.findTextInTags(polygonToRender.getDefenitionTags()),
							textDrawSettings,
							textPositionX,
							textPositionY);
		}
	}

	/**
	 * Create polygon for drawing by converting MapPolygon points
	 *
	 * @param mapPolygon polygon on map
	 * @return polygon on objectsCanvas
	 * @throws IllegalArgumentException mapPolygon is null
	 */
	private Polygon createDrawingPolygonByMapPolygon(MapPolygon mapPolygon) throws IllegalArgumentException
	{
		if (mapPolygon == null)
		{
			throw new IllegalArgumentException();
		}

		Polygon drawingPolygon = new Polygon();

		for (int i = 0; i < mapPolygon.getPointsCount(); i++)
		{
			Point2D pointOnCanvas = coordinatesConverter.goegraphicsToCanvas(mapPolygon.getPoint(i));
			drawingPolygon.addPoint((int) pointOnCanvas.getX(), (int) pointOnCanvas.getY());
		}

		return drawingPolygon;
	}
}
