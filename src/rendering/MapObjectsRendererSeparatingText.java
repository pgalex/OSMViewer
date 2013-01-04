package rendering;

import drawingStyles.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import map.MapLine;
import map.MapObject;
import map.MapObjectsRenderer;
import map.MapPoint;
import map.MapPolygon;
import rendering.selectng.SelectingBuffer;
import rendering.selectng.SelectingLine;
import rendering.selectng.SelectingPolygon;
import rendering.selectng.SelectingRectangle;

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
	 * Text color of object drawen as highlighted
	 */
	private static final Color HIGHLIGHTED_OBJECT_TEXT_COLOR = new Color(0, 0, 0);
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
	 * styleViewerForRendering, converterForRendering, fillingSelectingBuffer is
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

		Point2D pointPositionOnCanvas = coordinatesConverter.goegraphicsToCanvas(pointToRender.getPosition());

		BufferedImage pointImage = pointStyle.getIcon();
		if (pointImage != null)
		{
			int imagePositionX = (int) (pointPositionOnCanvas.getX() - pointImage.getWidth() / 2);
			int imagePositionY = (int) (pointPositionOnCanvas.getY() - pointImage.getHeight() / 2);

			if (isNeedToDrawPointBoundingRectangle(pointToRender))
			{
				drawBoundingRectangleAroundPoint(pointPositionOnCanvas, pointImage.getWidth(),
								pointImage.getHeight());
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

			SelectingRectangle selectingRectangleByImage = new SelectingRectangle(pointToRender,
							objectStyle.getDrawPriority(),
							new Rectangle2D.Double(imagePositionX, imagePositionY,
							pointImage.getWidth(), pointImage.getHeight()));
			selectingBuffer.addSelectingObject(selectingRectangleByImage);

		}
		else
		{
			TextDrawSettings textDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
			if (textDrawSettings != null)
			{
				String pointText = objectStyle.findTextInTags(pointToRender.getDefenitionTags());
				textCanvas.drawTextAtPoint(pointText,
								findPointTextDrawSettings(pointToRender, textDrawSettings),
								pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY());

				Rectangle2D pointTextBounds = textCanvas.computeTextAtPointBounds(pointText,
								textDrawSettings, pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY());
				SelectingRectangle selectingRectangleByTextBounds = new SelectingRectangle(pointToRender,
								objectStyle.getDrawPriority(),
								pointTextBounds);
				selectingBuffer.addSelectingObject(selectingRectangleByTextBounds);
			}
		}
	}

	/**
	 * Find point text draw settings by source draw settings
	 *
	 * @param point point draw settings of which text need to find
	 * @param sourceDrawSettings source text draw settings
	 * @return point text draw settings
	 * @throws IllegalArgumentException point or sourceDrawSettings is null
	 */
	private TextDrawSettings findPointTextDrawSettings(MapPoint point,
					TextDrawSettings sourceDrawSettings) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourceDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsHightlighted = (point == objectToDrawAsHighlighted);
		if (drawAsHightlighted)
		{
			return recreateTextDrawSettingsWithColor(sourceDrawSettings, HIGHLIGHTED_OBJECT_COLOR);
		}
		else
		{
			return sourceDrawSettings;
		}
	}

	/**
	 * Draw rectangle around point with image, using image size to find bounding
	 * rectangle size
	 *
	 * @param pointPositionOnCanvas point positiong on canvas
	 * @param pointImageWidth point image width
	 * @param pointImageHeight point image height
	 * @throws IllegalArgumentException pointPositionOnCanvas is null
	 */
	private void drawBoundingRectangleAroundPoint(Point2D pointPositionOnCanvas, int pointImageWidth,
					int pointImageHeight) throws IllegalArgumentException
	{
		objectsCanvas.setColor(HIGHLIGHTED_OBJECT_COLOR);
		objectsCanvas.setStroke(new BasicStroke(2));
		objectsCanvas.drawRoundRect((int) pointPositionOnCanvas.getX() - (pointImageWidth / 2) - 2,
						(int) pointPositionOnCanvas.getY() - (pointImageHeight / 2) - 2,
						pointImageWidth + 2 + 2,
						pointImageHeight + 2 + 2,
						2, 2);
	}

	/**
	 * Find, is need to draw bounding rectangle around point
	 *
	 * @param point rendering point
	 * @return is need to draw bounding rectangle
	 * @throws IllegalArgumentException point is null
	 */
	private boolean isNeedToDrawPointBoundingRectangle(MapPoint point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsHightlighted = (point == objectToDrawAsHighlighted);
		return drawAsHightlighted;
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

		objectsCanvas.setColor(findLineColor(lineToRender, lineStyle));
		objectsCanvas.setStroke(lineStyle.getStroke());

		for (int i = 0; i < drawingMultiline.length - 1; i++)
		{
			Point2D firstPoint = drawingMultiline[i];
			Point2D secondPoint = drawingMultiline[i + 1];

			objectsCanvas.drawLine((int) firstPoint.getX(), (int) firstPoint.getY(),
							(int) secondPoint.getX(), (int) secondPoint.getY());
		}

		TextDrawSettings lineTextDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
		boolean needToDrawText = (lineTextDrawSettings != null);
		if (needToDrawText)
		{
			String lineText = objectStyle.findTextInTags(lineToRender.getDefenitionTags());
			textCanvas.drawTextOnMultiline(lineText,
							findLineTextDrawSettingsColor(lineToRender, lineTextDrawSettings), drawingMultiline);
		}

		SelectingLine selectingLineByRenderingLine = new SelectingLine(lineToRender,
						objectStyle.getDrawPriority(),
						drawingMultiline, lineStyle.getWidth());
		selectingBuffer.addSelectingObject(selectingLineByRenderingLine);
	}

	/**
	 * Find color to draw line
	 *
	 * @param line line, which color need to find
	 * @param sourceDrawSettings source draw settings of line, using for finding
	 * @return line color
	 * @throws IllegalArgumentException line or sourceDrawSettings is null
	 */
	private Color findLineColor(MapLine line,
					LineDrawSettings sourceDrawSettings) throws IllegalArgumentException
	{
		if (line == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourceDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsHighlighted = (line == objectToDrawAsHighlighted);
		if (drawAsHighlighted)
		{
			return HIGHLIGHTED_OBJECT_COLOR;
		}
		else
		{
			return sourceDrawSettings.getColor();
		}
	}

	/**
	 * Find draw settings of line text
	 *
	 * @param line line, draw settings of which text need to find
	 * @param sourceTextDrawSettings source draw settings of line text, using for
	 * finding
	 * @return line text draw settings
	 * @throws IllegalArgumentException line or sourceTextDrawSettings is null
	 */
	private TextDrawSettings findLineTextDrawSettingsColor(MapLine line,
					TextDrawSettings sourceTextDrawSettings) throws IllegalArgumentException
	{
		if (line == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourceTextDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsHighlighted = (line == objectToDrawAsHighlighted);
		if (drawAsHighlighted)
		{
			return recreateTextDrawSettingsWithColor(sourceTextDrawSettings, HIGHLIGHTED_OBJECT_TEXT_COLOR);
		}
		else
		{
			return sourceTextDrawSettings;
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
			objectsCanvas.setPaint(findPolygonInnerPaint(polygonToRender, polygonStyle));
			objectsCanvas.fillPolygon(drawingPolygon);
		}
		if (polygonStyle.isDrawBorder())
		{
			LineDrawSettings borderStyle = polygonStyle.getBorderDrawSettings();
			objectsCanvas.setColor(findPolygonBorderColor(polygonToRender, borderStyle));
			objectsCanvas.setStroke(borderStyle.getStroke());
			objectsCanvas.drawPolygon(drawingPolygon);
		}

		TextDrawSettings polygonTextDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
		boolean needToDrawText = (polygonTextDrawSettings != null);
		if (needToDrawText)
		{
			double textPositionX = drawingPolygon.getBounds2D().getCenterX();
			double textPositionY = drawingPolygon.getBounds2D().getCenterY();
			String polygonText = objectStyle.findTextInTags(polygonToRender.getDefenitionTags());
			textCanvas.drawTextAtPoint(polygonText,
							findPolygonTextDrawSettings(polygonToRender, polygonTextDrawSettings),
							textPositionX, textPositionY);
		}

		SelectingPolygon selectingPolygonByRenderedPolygon = new SelectingPolygon(polygonToRender,
						objectStyle.getDrawPriority(),
						drawingPolygon);
		selectingBuffer.addSelectingObject(selectingPolygonByRenderedPolygon);
	}

	/**
	 * Find color to draw polygon border part
	 *
	 * @param polygon polygon, color of which border need to find
	 * @param sourceBorderDrawSettings draw settings of polygon border, using as
	 * source for finding
	 * @return color of polygon border
	 * @throws IllegalArgumentException polygon or sourceBorderDrawSettings is
	 * null
	 */
	private Color findPolygonBorderColor(MapPolygon polygon,
					LineDrawSettings sourceBorderDrawSettings) throws IllegalArgumentException
	{
		if (polygon == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourceBorderDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsHighlighted = (polygon == objectToDrawAsHighlighted);
		if (drawAsHighlighted)
		{
			return HIGHLIGHTED_OBJECT_COLOR;
		}
		else
		{
			return sourceBorderDrawSettings.getColor();
		}
	}

	/**
	 * Find paint to draw inner part of polygon
	 *
	 * @param polygon polygon, paint of which border need to find
	 * @param sourceDrawSettings source draw settings of polygon, using for
	 * finding
	 * @return paint for drawing inner part of polygon
	 * @throws IllegalArgumentException polygon or sourceDrawSettings is null
	 */
	private Paint findPolygonInnerPaint(MapPolygon polygon,
					PolygonDrawSettings sourceDrawSettings) throws IllegalArgumentException
	{
		if (polygon == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourceDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsHighlighted = (polygon == objectToDrawAsHighlighted);
		if (drawAsHighlighted)
		{
			return HIGHLIGHTED_OBJECT_COLOR;
		}
		else
		{
			return sourceDrawSettings.getPaint();
		}
	}

	/**
	 * Find text draw settings to draw polygon text
	 *
	 * @param polygon polygon, text color of which border need to find
	 * @param sourceTextDrawSettings source draw settings of polygon text, using
	 * for finding
	 * @return draw settings of polygon text
	 * @throws IllegalArgumentException polygon or sourceTextDrawSettings is null
	 */
	private TextDrawSettings findPolygonTextDrawSettings(MapPolygon polygon,
					TextDrawSettings sourceTextDrawSettings) throws IllegalArgumentException
	{
		if (polygon == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourceTextDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsHighlighted = (polygon == objectToDrawAsHighlighted);
		if (drawAsHighlighted)
		{
			return recreateTextDrawSettingsWithColor(sourceTextDrawSettings, HIGHLIGHTED_OBJECT_TEXT_COLOR);
		}
		else
		{
			return sourceTextDrawSettings;
		}
	}

	/**
	 * Create text draw settings by source draw settings with another color
	 *
	 * @param sourceDrawSettings source text draw settings
	 * @param newColor new text color
	 * @return highlighted text draw settings by source draw settings
	 * @throws IllegalArgumentException sourceDrawSettings is null
	 */
	private TextDrawSettings recreateTextDrawSettingsWithColor(TextDrawSettings sourceDrawSettings,
					Color newColor) throws IllegalArgumentException
	{
		if (sourceDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		TextDrawSettings highlightedTextDrawSettings = new TextDrawSettings();
		highlightedTextDrawSettings.setFont(sourceDrawSettings.getFont());
		highlightedTextDrawSettings.setColor(newColor);

		return highlightedTextDrawSettings;
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
