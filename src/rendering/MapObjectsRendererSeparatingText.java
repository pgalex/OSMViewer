package rendering;

import drawingStyles.*;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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
		
		boolean drawAsHightlighted = (pointToRender == objectToDrawAsHighlighted);
		Point2D pointPositionOnCanvas = coordinatesConverter.goegraphicsToCanvas(pointToRender.getPosition());
		
		BufferedImage pointImage = pointStyle.getIcon();
		if (pointImage != null)
		{
			if (drawAsHightlighted)
			{
				drawHighlightedPointWithImage(pointPositionOnCanvas, pointImage,
								objectStyle.findTextInTags(pointToRender.getDefenitionTags()),
								objectStyle.findTextDrawSettings(renderingScaleLevel));
			}
			else
			{
				drawNormalPointWithImage(pointPositionOnCanvas, pointImage,
								objectStyle.findTextInTags(pointToRender.getDefenitionTags()),
								objectStyle.findTextDrawSettings(renderingScaleLevel));
			}
			
			int imagePositionX = (int) (pointPositionOnCanvas.getX() - pointImage.getWidth() / 2);
			int imagePositionY = (int) (pointPositionOnCanvas.getY() - pointImage.getHeight() / 2);
			SelectingRectangle selectingRectangleByImage = new SelectingRectangle(pointToRender,
							new Rectangle2D.Double(imagePositionX, imagePositionY,
							pointImage.getWidth(), pointImage.getHeight()));
			selectingBuffer.addSelectingObject(selectingRectangleByImage);
		}
		else
		{
			String pointText = objectStyle.findTextInTags(pointToRender.getDefenitionTags());
			TextDrawSettings pointTextDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
			if (drawAsHightlighted)
			{
				drawHighlightedPointWithoutImage(pointPositionOnCanvas, pointText, pointTextDrawSettings);
			}
			else
			{
				drawNormalPointWithoutImage(pointPositionOnCanvas, pointText, pointTextDrawSettings);
			}
			
			Rectangle2D pointTextBounds = textCanvas.computeTextAtPointBounds(pointText,
							pointTextDrawSettings, pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY());
			SelectingRectangle selectingRectangleByTextBounds = new SelectingRectangle(pointToRender,
							pointTextBounds);
			selectingBuffer.addSelectingObject(selectingRectangleByTextBounds);
		}
	}

	/**
	 * Draw point without image as highlighted
	 *
	 * @param pointPosition point position on canvas
	 * @param pointText text of point. If null - not draw text
	 * @param pointTextDrawSettings draw settings of point text. If null - not
	 * draw text
	 * @throws IllegalArgumentException pointPosition is null
	 */
	private void drawHighlightedPointWithoutImage(Point2D pointPosition, String pointText,
					TextDrawSettings pointTextDrawSettings) throws IllegalArgumentException
	{
		if (pointPosition == null)
		{
			throw new IllegalArgumentException();
		}
		
		boolean needToDrawText = (pointTextDrawSettings != null && pointText != null);
		if (needToDrawText)
		{
			TextDrawSettings highlightedTextDrawSettings = new TextDrawSettings();
			highlightedTextDrawSettings.setFont(pointTextDrawSettings.getFont());
			highlightedTextDrawSettings.setColor(HIGHLIGHTED_OBJECT_COLOR);
			textCanvas.drawTextAtPoint(pointText, highlightedTextDrawSettings,
							pointPosition.getX(), pointPosition.getY());
		}
	}

	/**
	 * Draw point without image in normal state
	 *
	 * @param pointPosition point position on canvas
	 * @param pointText text of point. If null - not draw text
	 * @param pointTextDrawSettings draw settings of point text. If null - not
	 * draw text
	 * @throws IllegalArgumentException pointPosition is null
	 */
	private void drawNormalPointWithoutImage(Point2D pointPosition, String pointText,
					TextDrawSettings pointTextDrawSettings) throws IllegalArgumentException
	{
		if (pointPosition == null)
		{
			throw new IllegalArgumentException();
		}
		
		boolean needToDrawText = (pointTextDrawSettings != null && pointText != null);
		if (needToDrawText)
		{
			textCanvas.drawTextAtPoint(pointText, pointTextDrawSettings, pointPosition.getX(),
							pointPosition.getY());
		}
	}

	/**
	 * Draw point with image as highlighted
	 *
	 * @param pointPosition point position on canvas
	 * @param pointImage point image
	 * @param pointText text of point. If null - not draw text
	 * @param pointTextDrawSettings draw settings of point text. If null - not
	 * draw text
	 * @throws IllegalArgumentException pointPosition or pointImage is null
	 */
	private void drawHighlightedPointWithImage(Point2D pointPosition, BufferedImage pointImage,
					String pointText, TextDrawSettings pointTextDrawSettings) throws IllegalArgumentException
	{
		if (pointPosition == null)
		{
			throw new IllegalArgumentException();
		}
		if (pointImage == null)
		{
			throw new IllegalArgumentException();
		}
		
		int imagePositionX = (int) (pointPosition.getX() - pointImage.getWidth() / 2);
		int imagePositionY = (int) (pointPosition.getY() - pointImage.getHeight() / 2);
		
		objectsCanvas.drawImage(pointImage, imagePositionX, imagePositionY, null);
		
		objectsCanvas.setColor(HIGHLIGHTED_OBJECT_COLOR);
		objectsCanvas.setStroke(new BasicStroke(2));
		objectsCanvas.drawRoundRect(imagePositionX - 2, imagePositionY - 2, pointImage.getWidth() + 4,
						pointImage.getHeight() + 4, 2, 2);
		
		boolean needToDrawText = (pointTextDrawSettings != null && pointText != null);
		if (needToDrawText)
		{
			textCanvas.drawTextUnderPoint(pointText, pointTextDrawSettings, pointPosition.getX(),
							pointPosition.getY() + pointImage.getHeight() / 2);
		}
	}

	/**
	 * Draw point with image in normal state
	 *
	 * @param pointPosition point position on canvas
	 * @param pointImage point image
	 * @param pointText text of point. If null - not draw text
	 * @param pointTextDrawSettings draw settings of point text. If null - not
	 * draw text
	 * @throws IllegalArgumentException pointPosition or pointImage is null
	 */
	private void drawNormalPointWithImage(Point2D pointPosition, BufferedImage pointImage,
					String pointText, TextDrawSettings pointTextDrawSettings) throws IllegalArgumentException
	{
		if (pointPosition == null)
		{
			throw new IllegalArgumentException();
		}
		if (pointImage == null)
		{
			throw new IllegalArgumentException();
		}
		
		int imagePositionX = (int) (pointPosition.getX() - pointImage.getWidth() / 2);
		int imagePositionY = (int) (pointPosition.getY() - pointImage.getHeight() / 2);
		
		objectsCanvas.drawImage(pointImage, imagePositionX, imagePositionY, null);
		
		boolean needToDrawText = (pointTextDrawSettings != null && pointText != null);
		if (needToDrawText)
		{
			textCanvas.drawTextUnderPoint(pointText, pointTextDrawSettings, pointPosition.getX(),
							pointPosition.getY() + pointImage.getHeight() / 2);
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
		
		boolean drawAsHightlighted = (lineToRender == objectToDrawAsHighlighted);
		if (drawAsHightlighted)
		{
			drawHighlightedLine(drawingMultiline, lineStyle,
							objectStyle.findTextInTags(lineToRender.getDefenitionTags()),
							objectStyle.findTextDrawSettings(renderingScaleLevel));
		}
		else
		{
			drawNormalLine(drawingMultiline, lineStyle,
							objectStyle.findTextInTags(lineToRender.getDefenitionTags()),
							objectStyle.findTextDrawSettings(renderingScaleLevel));
		}
		
		SelectingLine selectingLineByRenderingLine = new SelectingLine(lineToRender,
						drawingMultiline, lineStyle.getWidth());
		selectingBuffer.addSelectingObject(selectingLineByRenderingLine);
	}

	/**
	 * Draw line as highlighted
	 *
	 * @param linePoints points of line
	 * @param lineDrawSettings draw settings of line. If null - not draw text
	 * @param lineText text of line. If null - not draw text
	 * @param lineTextDrawSettings draw settings of line text
	 * @throws IllegalArgumentException linePoints null or contains null;
	 * lineDrawSettings is null
	 */
	private void drawHighlightedLine(Point2D[] linePoints, LineDrawSettings lineDrawSettings,
					String lineText, TextDrawSettings lineTextDrawSettings) throws IllegalArgumentException
	{
		if (linePoints == null)
		{
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < linePoints.length; i++)
		{
			if (linePoints[i] == null)
			{
				throw new IllegalArgumentException();
			}
		}
		
		objectsCanvas.setColor(HIGHLIGHTED_OBJECT_COLOR);
		objectsCanvas.setStroke(lineDrawSettings.getStroke());
		
		for (int i = 0; i < linePoints.length - 1; i++)
		{
			Point2D firstPoint = linePoints[i];
			Point2D secondPoint = linePoints[i + 1];
			
			objectsCanvas.drawLine((int) firstPoint.getX(), (int) firstPoint.getY(),
							(int) secondPoint.getX(), (int) secondPoint.getY());
		}
		
		boolean needToDrawText = (lineTextDrawSettings != null && lineText != null);
		if (needToDrawText)
		{
			textCanvas.drawTextOnMultiline(lineText, computeHighlightedTextDrawSettings(lineTextDrawSettings),
							linePoints);
		}
	}

	/**
	 * Draw line in normal state
	 *
	 * @param linePoints points of line
	 * @param lineDrawSettings draw settings of line. If null - not draw text
	 * @param lineText text of line. If null - not draw text
	 * @param lineTextDrawSettings draw settings of line text
	 * @throws IllegalArgumentException linePoints null or contains null;
	 * lineDrawSettings is null
	 */
	private void drawNormalLine(Point2D[] linePoints, LineDrawSettings lineDrawSettings,
					String lineText, TextDrawSettings lineTextDrawSettings) throws IllegalArgumentException
	{
		if (linePoints == null)
		{
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < linePoints.length; i++)
		{
			if (linePoints[i] == null)
			{
				throw new IllegalArgumentException();
			}
		}
		
		objectsCanvas.setColor(lineDrawSettings.getColor());
		objectsCanvas.setStroke(lineDrawSettings.getStroke());
		
		for (int i = 0; i < linePoints.length - 1; i++)
		{
			Point2D firstPoint = linePoints[i];
			Point2D secondPoint = linePoints[i + 1];
			
			objectsCanvas.drawLine((int) firstPoint.getX(), (int) firstPoint.getY(),
							(int) secondPoint.getX(), (int) secondPoint.getY());
		}
		
		boolean needToDrawText = (lineTextDrawSettings != null && lineText != null);
		if (needToDrawText)
		{
			textCanvas.drawTextOnMultiline(lineText, lineTextDrawSettings, linePoints);
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
		String polygonText = objectStyle.findTextInTags(polygonToRender.getDefenitionTags());
		TextDrawSettings polygonTextDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
		
		boolean drawAsHightlighted = (polygonToRender == objectToDrawAsHighlighted);
		if (drawAsHightlighted)
		{
			drawHighlightedPolygon(drawingPolygon, polygonStyle, polygonText, polygonTextDrawSettings);
		}
		else
		{
			drawNormalPolygon(drawingPolygon, polygonStyle, polygonText, polygonTextDrawSettings);
		}
		
		SelectingPolygon selectingPolygonByRenderedPolygon = new SelectingPolygon(polygonToRender,
						drawingPolygon);
		selectingBuffer.addSelectingObject(selectingPolygonByRenderedPolygon);
	}

	/**
	 * Draw polygon in normal state (not highlighted .., etc)
	 *
	 * @param drawingPolygon polygon on canvas to draw
	 * @param polygonDrawSettings draw settings of polygon
	 * @param polygonTextDrawSettings draw settings of polygon text, If null - not
	 * draw text
	 * @param polygonText text of polygon. If null - not draw text
	 * @throws IllegalArgumentException drawingPolygon, polygonDrawSettings is
	 * null
	 */
	private void drawNormalPolygon(Polygon drawingPolygon, PolygonDrawSettings polygonDrawSettings,
					String polygonText, TextDrawSettings polygonTextDrawSettings) throws IllegalArgumentException
	{
		if (drawingPolygon == null)
		{
			throw new IllegalArgumentException();
		}
		if (polygonDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}
		
		if (polygonDrawSettings.isDrawInnerPart())
		{
			objectsCanvas.setPaint(polygonDrawSettings.getPaint());
			objectsCanvas.fillPolygon(drawingPolygon);
		}
		if (polygonDrawSettings.isDrawBorder())
		{
			LineDrawSettings borderStyle = polygonDrawSettings.getBorderDrawSettings();
			objectsCanvas.setColor(borderStyle.getColor());
			objectsCanvas.setStroke(borderStyle.getStroke());
			objectsCanvas.drawPolygon(drawingPolygon);
		}
		
		boolean needToDrawText = (polygonTextDrawSettings != null && polygonText != null);
		if (needToDrawText)
		{
			double textPositionX = drawingPolygon.getBounds2D().getCenterX();
			double textPositionY = drawingPolygon.getBounds2D().getCenterY();
			textCanvas.drawTextAtPoint(polygonText, polygonTextDrawSettings, textPositionX, textPositionY);
		}
	}

	/**
	 * Draw highlighted polygon
	 *
	 * @param drawingPolygon polygon on canvas to draw
	 * @param polygonDrawSettings draw settings of polygon
	 * @param polygonTextDrawSettings draw settings of polygon text, If null - not
	 * draw text
	 * @param polygonText text of polygon. If null - not draw text
	 * @throws IllegalArgumentException drawingPolygon, polygonDrawSettings is
	 * null
	 */
	private void drawHighlightedPolygon(Polygon drawingPolygon, PolygonDrawSettings polygonDrawSettings,
					String polygonText, TextDrawSettings polygonTextDrawSettings) throws IllegalArgumentException
	{
		if (drawingPolygon == null)
		{
			throw new IllegalArgumentException();
		}
		if (polygonDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}
		
		if (polygonDrawSettings.isDrawInnerPart())
		{
			objectsCanvas.setPaint(HIGHLIGHTED_OBJECT_COLOR);
			objectsCanvas.fillPolygon(drawingPolygon);
		}
		if (polygonDrawSettings.isDrawBorder())
		{
			LineDrawSettings borderStyle = polygonDrawSettings.getBorderDrawSettings();
			objectsCanvas.setColor(HIGHLIGHTED_OBJECT_COLOR);
			objectsCanvas.setStroke(borderStyle.getStroke());
			objectsCanvas.drawPolygon(drawingPolygon);
		}
		
		boolean needToDrawText = (polygonTextDrawSettings != null && polygonText != null);
		if (needToDrawText)
		{
			double textPositionX = drawingPolygon.getBounds2D().getCenterX();
			double textPositionY = drawingPolygon.getBounds2D().getCenterY();
			textCanvas.drawTextAtPoint(polygonText, computeHighlightedTextDrawSettings(polygonTextDrawSettings),
							textPositionX, textPositionY);
		}
	}

	/**
	 * Compute highlighted text draw settings by source object draw settings
	 *
	 * @param sourceDrawSettings source text draw settings
	 * @return highlighted text draw settings by source draw settings
	 * @throws IllegalArgumentException sourceDrawSettings is null
	 */
	private TextDrawSettings computeHighlightedTextDrawSettings(TextDrawSettings sourceDrawSettings) throws IllegalArgumentException
	{
		if (sourceDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}
		
		TextDrawSettings highlightedTextDrawSettings = new TextDrawSettings();
		highlightedTextDrawSettings.setFont(sourceDrawSettings.getFont());
		highlightedTextDrawSettings.setColor(HIGHLIGHTED_OBJECT_TEXT_COLOR);
		
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
