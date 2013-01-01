package rendering;

import drawingStyles.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import map.MapLine;
import map.MapObject;
import map.MapObjectsRenderer;
import map.MapPoint;
import map.MapPolygon;
import rendering.selectng.SelectingBuffer;
import rendering.selectng.SelectingLine;
import rendering.selectng.SelectingPolygon;

/**
 * Objects renderer that drawes object on one canvas, and it's drawingText on
 * other
 *
 * @author pgalex
 */
public class MapObjectsRendererSeparatingText implements MapObjectsRenderer
{
	/**
	 * Color using to draw highlighted objects instead its own color
	 */
	private static final Color HIGHLIGHTED_OBJECT_COLOR = new Color(170, 255, 170);
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
	 * Object of rendering map, to draw as highlighted. Can be null
	 */
	private MapObject objectToDrawAsHighlighted;
	/**
	 * Buffer where will be added selecting objects, created by renderer objects
	 */
	private SelectingBuffer selectingBuffer;

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
	 * @param fillingSelectingBuffer Buffer where will be added selecting objects,
	 * created by renderer objects
	 * @throws IllegalArgumentException targetObjectsCanvas,targetTextCanvas,
	 * styleViewerForRendering,converterForRendering, fillingSelectingBuffer is
	 * null
	 */
	public MapObjectsRendererSeparatingText(Graphics2D targetObjectsCanvas,
					Graphics2D targetTextCanvas, StyleViewer styleViewerForRendering,
					CoordinatesConverter converterForRendering, int scaleLevelForRendering,
					SelectingBuffer fillingSelectingBuffer) throws IllegalArgumentException
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
		if (fillingSelectingBuffer == null)
		{
			throw new IllegalArgumentException();
		}

		objectsCanvas = targetObjectsCanvas;
		textCanvas = new TextCanvas(targetTextCanvas);
		styleViewer = styleViewerForRendering;
		coordinatesConverter = converterForRendering;
		renderingScaleLevel = scaleLevelForRendering;

		objectToDrawAsHighlighted = null;
		selectingBuffer = fillingSelectingBuffer;
	}

	/**
	 * Set object of rendering map to draw as highlighted
	 *
	 * @param highlightedObject object to set as highlighted
	 * @throws IllegalArgumentException highlightedObject is null
	 */
	public void setObjectToDrawAsHighlighted(MapObject highlightedObject) throws IllegalArgumentException
	{
		if (highlightedObject == null)
		{
			throw new IllegalArgumentException();
		}

		objectToDrawAsHighlighted = highlightedObject;
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

		boolean drawAsHightlighted = (pointToRender == objectToDrawAsHighlighted);

		Point2D pointPositionOnCanvas = coordinatesConverter.goegraphicsToCanvas(pointToRender.getPosition());

		BufferedImage pointImage = pointStyle.getIcon();
		if (pointImage != null)
		{
			int imagePositionX = (int) (pointPositionOnCanvas.getX() - pointImage.getWidth() / 2);
			int imagePositionY = (int) (pointPositionOnCanvas.getY() - pointImage.getHeight() / 2);

			if (drawAsHightlighted)
			{
				objectsCanvas.setColor(HIGHLIGHTED_OBJECT_COLOR);
				objectsCanvas.setStroke(new BasicStroke(2));
				objectsCanvas.drawRoundRect(imagePositionX - 2, imagePositionY - 2, pointImage.getWidth() + 4,
								pointImage.getHeight() + 4, 2, 2);
			}

			objectsCanvas.drawImage(pointImage, imagePositionX, imagePositionY, null);

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
				if (drawAsHightlighted)
				{
					TextDrawSettings highlightedTextDrawSettings = new TextDrawSettings();
					highlightedTextDrawSettings.setFont(textDrawSettings.getFont());
					highlightedTextDrawSettings.setColor(HIGHLIGHTED_OBJECT_COLOR);
					textCanvas.drawTextAtPoint(objectStyle.findTextInTags(pointToRender.getDefenitionTags()),
									highlightedTextDrawSettings, pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY());
				}
				else
				{
					textCanvas.drawTextAtPoint(objectStyle.findTextInTags(pointToRender.getDefenitionTags()),
									textDrawSettings, pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY());
				}
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

		boolean drawAsHightlighted = (lineToRender == objectToDrawAsHighlighted);
		if (drawAsHightlighted)
		{
			objectsCanvas.setColor(HIGHLIGHTED_OBJECT_COLOR);
		}
		else
		{
			objectsCanvas.setColor(lineStyle.getColor());
		}

		objectsCanvas.setStroke(lineStyle.getStroke());

		Point2D[] drawingMultiline = createDrawingMultilineByMapLine(lineToRender);
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
							textDrawSettings, drawingMultiline);
		}

		SelectingLine selectingLineByRenderingLine = new SelectingLine(lineToRender,
						drawingMultiline, lineStyle.getWidth());
		selectingBuffer.addSelectingObject(selectingLineByRenderingLine);
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

		boolean drawAsHightlighted = (polygonToRender == objectToDrawAsHighlighted);

		Polygon drawingPolygon = createDrawingPolygonByMapPolygon(polygonToRender);
		if (polygonStyle.isDrawInnerPart())
		{
			if (drawAsHightlighted)
			{
				objectsCanvas.setPaint(HIGHLIGHTED_OBJECT_COLOR);
			}
			else
			{
				objectsCanvas.setPaint(polygonStyle.getPaint());
			}
			objectsCanvas.fillPolygon(drawingPolygon);
		}

		if (polygonStyle.isDrawBorder())
		{
			LineDrawSettings borderStyle = polygonStyle.getBorderDrawSettings();
			if (drawAsHightlighted)
			{
				objectsCanvas.setColor(HIGHLIGHTED_OBJECT_COLOR);
			}
			else
			{
				objectsCanvas.setColor(borderStyle.getColor());
			}
			objectsCanvas.setStroke(borderStyle.getStroke());
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

		SelectingPolygon selectingPolygonByRenderedPolygon = new SelectingPolygon(polygonToRender,
						drawingPolygon);
		selectingBuffer.addSelectingObject(selectingPolygonByRenderedPolygon);
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
