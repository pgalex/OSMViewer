package rendering;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import rendering.selectng.SelectingBuffer;
import rendering.selectng.SelectingLine;
import rendering.selectng.SelectingPolygon;
import rendering.selectng.SelectingRectangle;

/**
 * Renderer of map objects that drawes object on one canvas, and it's text on
 * other
 *
 * @author pgalex
 */
public class MapObjectsRendererSeparatingText implements RenderableMapObjectsVisitor
{
	/**
	 * Color using to draw highlighted object instead its own color
	 */
	private static final Color HIGHLIGHTED_OBJECT_COLOR = new Color(200, 255, 200);
	/**
	 * Text color of object drawen as highlighted
	 */
	private static final Color HIGHLIGHTED_OBJECT_TEXT_COLOR = new Color(0, 0, 0);
	/**
	 * Color using to draw selected object instead its own color
	 */
	private static final Color SELECTED_OBJECT_COLOR = new Color(0, 255, 0);
	/**
	 * Text color of object drawen as selected
	 */
	private static final Color SELECTED_OBJECT_TEXT_COLOR = new Color(0, 0, 0);
	/**
	 * Canvas to draw map objects
	 */
	private Graphics2D objectsCanvas;
	/**
	 * Canvas to draw text of map objects
	 */
	private TextCanvas textCanvas;
	/**
	 * Coordinates converter to target canvas coordinates
	 */
	private CoordinatesConverter coordinatesConverter;
	/**
	 * Scale level using for rendering
	 */
	private int renderingScaleLevel;
	/**
	 * Object of rendering map, to draw as highlighted. Can be null
	 */
	private RenderableMapObject objectToDrawAsHighlighted;
	/**
	 * Object of rendering map, to draw as selected. Can be null
	 */
	private RenderableMapObject objectToDrawAsSelected;
	/**
	 * Buffer where will be added selecting objects, created by drawen objects
	 */
	private SelectingBuffer selectingBuffer;

	/**
	 * Create map objects renderer
	 *
	 * @param targetObjectsCanvas canvas to draw map objects
	 * @param targetTextCanvas canvas to draw text of map objects
	 * @param renderingCoordinatesConverter object that will be using for
	 * coordinates converting while drawing
	 * @param rederingScaleLevel scale level using for rendering
	 * @param fillingSelectingBuffer buffer where will be added selecting objects,
	 * created by drawen objects
	 * @throws IllegalArgumentException targetObjectsCanvas, targetTextCanvas,
	 * renderingCoordinatesConverter, fillingSelectingBuffer is null
	 */
	public MapObjectsRendererSeparatingText(Graphics2D targetObjectsCanvas,
					Graphics2D targetTextCanvas, CoordinatesConverter renderingCoordinatesConverter,
					int rederingScaleLevel, SelectingBuffer fillingSelectingBuffer) throws IllegalArgumentException
	{
		if (targetObjectsCanvas == null)
		{
			throw new IllegalArgumentException();
		}
		if (targetTextCanvas == null)
		{
			throw new IllegalArgumentException();
		}
		if (renderingCoordinatesConverter == null)
		{
			throw new IllegalArgumentException();
		}
		if (fillingSelectingBuffer == null)
		{
			throw new IllegalArgumentException();
		}

		objectsCanvas = targetObjectsCanvas;
		textCanvas = new TextCanvas(targetTextCanvas);
		coordinatesConverter = renderingCoordinatesConverter;
		renderingScaleLevel = rederingScaleLevel;

		objectToDrawAsHighlighted = null;
		objectToDrawAsSelected = null;
		selectingBuffer = fillingSelectingBuffer;
	}

	/**
	 * Set object of rendering map to draw as highlighted
	 *
	 * @param highlightedObject object which need to draw as highlighted
	 * @throws IllegalArgumentException highlightedObject is null
	 */
	public void setObjectToDrawAsHighlighted(RenderableMapObject highlightedObject) throws IllegalArgumentException
	{
		if (highlightedObject == null)
		{
			throw new IllegalArgumentException();
		}

		objectToDrawAsHighlighted = highlightedObject;
	}

	/**
	 * Set object of rendering map to draw as selected
	 *
	 * @param selectedObject object which need to draw as selected
	 * @throws IllegalArgumentException selectedObject is null
	 */
	public void setObjectToDrawAsSelected(RenderableMapObject selectedObject) throws IllegalArgumentException
	{
		if (selectedObject == null)
		{
			throw new IllegalArgumentException();
		}

		objectToDrawAsSelected = selectedObject;
	}

	/**
	 * Visit renderable point-like map object
	 *
	 * @param renderablePoint visiting renderable point
	 * @throws IllegalArgumentException renderablePoint is null
	 */
	@Override
	public void visitPoint(RenderableMapPoint renderablePoint) throws IllegalArgumentException
	{
		if (renderablePoint == null)
		{
			throw new IllegalArgumentException();
		}

		RenderableMapObjectDrawSettings objectStyle = renderablePoint.getDrawSettings();
		if (objectStyle == null)
		{
			return;
		}
		RenderableMapPointDrawSettings pointStyle = objectStyle.findPointDrawSettings(renderingScaleLevel);
		if (pointStyle == null)
		{
			return;
		}

		Point2D pointPositionOnCanvas = coordinatesConverter.goegraphicsToCanvas(renderablePoint.getPosition());

		BufferedImage pointImage = pointStyle.getIcon();
		if (pointImage != null)
		{
			int imagePositionX = (int) (pointPositionOnCanvas.getX() - pointImage.getWidth() / 2);
			int imagePositionY = (int) (pointPositionOnCanvas.getY() - pointImage.getHeight() / 2);

			if (isNeedToDrawPointBoundingRectangle(renderablePoint))
			{
				Color boundingRectangleColor = determinePointBoundingRectangleColor(renderablePoint);
				if (boundingRectangleColor != null)
				{
					drawBoundingRectangleAroundPoint(pointPositionOnCanvas, pointImage.getWidth(),
									pointImage.getHeight(), boundingRectangleColor);
				}
			}

			objectsCanvas.drawImage(pointImage, imagePositionX, imagePositionY, null);

			TextDrawSettings textDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
			if (textDrawSettings != null)
			{
				textCanvas.drawTextUnderPoint(objectStyle.findTextInTags(renderablePoint.getDefenitionTags()),
								textDrawSettings,
								pointPositionOnCanvas.getX(),
								pointPositionOnCanvas.getY() + pointImage.getHeight() / 2);
			}

			SelectingRectangle selectingRectangleByImage = new SelectingRectangle(renderablePoint,
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
				String pointText = objectStyle.findTextInTags(renderablePoint.getDefenitionTags());
				textCanvas.drawTextAtPoint(pointText,
								determinePointTextDrawSettings(renderablePoint, textDrawSettings),
								pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY());

				Rectangle2D pointTextBounds = textCanvas.computeTextAtPointBounds(pointText,
								textDrawSettings, pointPositionOnCanvas.getX(), pointPositionOnCanvas.getY());
				SelectingRectangle selectingRectangleByTextBounds = new SelectingRectangle(renderablePoint,
								objectStyle.getDrawPriority(),
								pointTextBounds);
				selectingBuffer.addSelectingObject(selectingRectangleByTextBounds);
			}
		}
	}

	/**
	 * Determine point text draw settings by it source text draw settings
	 *
	 * @param point point draw settings of which text need to find
	 * @param sourcePointTextDrawSettings source text draw settings
	 * @return point text draw settings
	 * @throws IllegalArgumentException point or sourcePointTextDrawSettings is
	 * null
	 */
	private TextDrawSettings determinePointTextDrawSettings(RenderableMapPoint point,
					TextDrawSettings sourcePointTextDrawSettings) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourcePointTextDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsSelected = (point == objectToDrawAsSelected);
		if (drawAsSelected)
		{
			return sourcePointTextDrawSettings.createCopyWithColor(SELECTED_OBJECT_COLOR);
		}
		else
		{
			boolean drawAsHightlighted = (point == objectToDrawAsHighlighted);
			if (drawAsHightlighted)
			{
				return sourcePointTextDrawSettings.createCopyWithColor(HIGHLIGHTED_OBJECT_COLOR);
			}
			else
			{
				return sourcePointTextDrawSettings;
			}
		}
	}

	/**
	 * Draw rectangle around point, using bounding rectangle size
	 *
	 * @param pointPositionOnCanvas point positiong on canvas
	 * @param boundingRectangleWidth point image width
	 * @param boundingRectangleHeight point image height
	 * @param boundingRectangleColor color of bounding rectangle
	 * @throws IllegalArgumentException pointPositionOnCanvas or
	 * boundingRectangleColor is null
	 */
	private void drawBoundingRectangleAroundPoint(Point2D pointPositionOnCanvas, int boundingRectangleWidth,
					int boundingRectangleHeight, Color boundingRectangleColor) throws IllegalArgumentException
	{
		if (pointPositionOnCanvas == null)
		{
			throw new IllegalArgumentException();
		}
		if (boundingRectangleColor == null)
		{
			throw new IllegalArgumentException();
		}
		objectsCanvas.setColor(boundingRectangleColor);
		objectsCanvas.setStroke(new BasicStroke(2));
		objectsCanvas.drawRoundRect((int) pointPositionOnCanvas.getX() - (boundingRectangleWidth / 2) - 2,
						(int) pointPositionOnCanvas.getY() - (boundingRectangleHeight / 2) - 2,
						boundingRectangleWidth + 2 + 2,
						boundingRectangleHeight + 2 + 2,
						2, 2);
	}

	/**
	 * Determine color for drawing bounding rectangle around point
	 *
	 * @param point point which bounding rectangle color need to determine
	 * @return color for drawing bounding rectangle. If null - can not find
	 * specified color
	 * @throws IllegalArgumentException point is null
	 */
	private Color determinePointBoundingRectangleColor(RenderableMapObject point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsSelected = (point == objectToDrawAsSelected);
		if (drawAsSelected)
		{
			return SELECTED_OBJECT_COLOR;
		}
		else
		{
			boolean drawAsHightlighted = (point == objectToDrawAsHighlighted);
			if (drawAsHightlighted)
			{
				return HIGHLIGHTED_OBJECT_COLOR;
			}
			else
			{
				return null;
			}
		}
	}

	/**
	 * Find, is need to draw bounding rectangle around point
	 *
	 * @param point rendering point
	 * @return is need to draw bounding rectangle around given point
	 * @throws IllegalArgumentException point is null
	 */
	private boolean isNeedToDrawPointBoundingRectangle(RenderableMapPoint point) throws IllegalArgumentException
	{
		if (point == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsHightlighted = (point == objectToDrawAsHighlighted);
		boolean drawAsSelected = (point == objectToDrawAsSelected);
		return drawAsHightlighted || drawAsSelected;
	}

	/**
	 * Visit renderable line-like map object
	 *
	 * @param renderableLine visiting renderable line
	 * @throws IllegalArgumentException renderableLine is null
	 */
	@Override
	public void visitLine(RenderableMapLine renderableLine) throws IllegalArgumentException
	{
		if (renderableLine == null)
		{
			throw new IllegalArgumentException();
		}

		RenderableMapObjectDrawSettings objectStyle = renderableLine.getDrawSettings();
		if (objectStyle == null)
		{
			return;
		}
		RenderableMapLineDrawSettings lineStyle = objectStyle.findLineDrawSettings(renderingScaleLevel);
		if (lineStyle == null)
		{
			return;
		}

		Point2D[] drawingMultiline = createDrawingMultilineByMapLine(renderableLine);

		objectsCanvas.setColor(determineLineColor(renderableLine, lineStyle));
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
			String lineText = objectStyle.findTextInTags(renderableLine.getDefenitionTags());
			textCanvas.drawTextOnMultiline(lineText,
							determineLineTextDrawSettingsColor(renderableLine, lineTextDrawSettings), drawingMultiline);
		}

		SelectingLine selectingLineByRenderingLine = new SelectingLine(renderableLine,
						objectStyle.getDrawPriority(),
						drawingMultiline, lineStyle.getWidth());
		selectingBuffer.addSelectingObject(selectingLineByRenderingLine);
	}

	/**
	 * Determine color to draw line
	 *
	 * @param line line, which color need to determine
	 * @param sourceLineDrawSettings source draw settings of line
	 * @return line color
	 * @throws IllegalArgumentException line or sourceLineDrawSettings is null
	 */
	private Color determineLineColor(RenderableMapLine line,
					RenderableMapLineDrawSettings sourceLineDrawSettings) throws IllegalArgumentException
	{
		if (line == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourceLineDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsSelected = (line == objectToDrawAsSelected);
		if (drawAsSelected)
		{
			return SELECTED_OBJECT_COLOR;
		}
		else
		{
			boolean drawAsHighlighted = (line == objectToDrawAsHighlighted);
			if (drawAsHighlighted)
			{
				return HIGHLIGHTED_OBJECT_COLOR;
			}
			else
			{
				return sourceLineDrawSettings.getColor();
			}
		}
	}

	/**
	 * Determine draw settings of line text
	 *
	 * @param line line, draw settings of which text need to determine
	 * @param sourceLineTextDrawSettings source draw settings of line text, using
	 * for finding
	 * @return line text draw settings
	 * @throws IllegalArgumentException line or sourceLineTextDrawSettings is null
	 */
	private TextDrawSettings determineLineTextDrawSettingsColor(RenderableMapLine line,
					TextDrawSettings sourceLineTextDrawSettings) throws IllegalArgumentException
	{
		if (line == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourceLineTextDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsSelected = (line == objectToDrawAsSelected);
		if (drawAsSelected)
		{
			return sourceLineTextDrawSettings.createCopyWithColor(SELECTED_OBJECT_TEXT_COLOR);
		}
		else
		{
			boolean drawAsHighlighted = (line == objectToDrawAsHighlighted);
			if (drawAsHighlighted)
			{
				return sourceLineTextDrawSettings.createCopyWithColor(HIGHLIGHTED_OBJECT_TEXT_COLOR);
			}
			else
			{
				return sourceLineTextDrawSettings;
			}
		}
	}

	/**
	 * Create multiline for drawing by converting each point of map line to canvas
	 * coordinates
	 *
	 * @param renderableLine line which points need to convert
	 * @return multiline, defined by points array, created by renderableLine
	 * @throws IllegalArgumentException renderableLine is null
	 */
	private Point2D[] createDrawingMultilineByMapLine(RenderableMapLine renderableLine) throws IllegalArgumentException
	{
		if (renderableLine == null)
		{
			throw new IllegalArgumentException();
		}

		Point2D[] drawingMultiline = new Point2D[renderableLine.getPointsCount()];

		for (int i = 0; i < renderableLine.getPointsCount(); i++)
		{
			drawingMultiline[i] = coordinatesConverter.goegraphicsToCanvas(renderableLine.getPoint(i));
		}

		return drawingMultiline;
	}

	/**
	 * Visit renderable polygon-like map object
	 *
	 * @param renderablePolygon visiting renderable polygon
	 * @throws IllegalArgumentException renderablePolygon is null
	 */
	@Override
	public void visitPolygon(RenderableMapPolygon renderablePolygon) throws IllegalArgumentException
	{
		if (renderablePolygon == null)
		{
			throw new IllegalArgumentException();
		}

		RenderableMapObjectDrawSettings objectStyle = renderablePolygon.getDrawSettings();
		if (objectStyle == null)
		{
			return;
		}
		RenderableMapPolygonDrawSettings polygonStyle = objectStyle.findPolygonDrawSettings(renderingScaleLevel);
		if (polygonStyle == null)
		{
			return;
		}

		Polygon drawingPolygon = createDrawingPolygonByRenderablePolygonPoints(renderablePolygon);
		if (polygonStyle.isDrawInnerPart())
		{
			objectsCanvas.setPaint(determinePolygonInnerPaint(renderablePolygon, polygonStyle));
			objectsCanvas.fillPolygon(drawingPolygon);
		}
		if (polygonStyle.isDrawBorder())
		{
			RenderableMapLineDrawSettings borderStyle = polygonStyle.findBorderDrawSettings();
			objectsCanvas.setColor(determinePolygonBorderColor(renderablePolygon, borderStyle));
			objectsCanvas.setStroke(borderStyle.getStroke());
			objectsCanvas.drawPolygon(drawingPolygon);
		}

		TextDrawSettings polygonTextDrawSettings = objectStyle.findTextDrawSettings(renderingScaleLevel);
		boolean needToDrawText = (polygonTextDrawSettings != null);
		if (needToDrawText)
		{
			double textPositionX = drawingPolygon.getBounds2D().getCenterX();
			double textPositionY = drawingPolygon.getBounds2D().getCenterY();
			String polygonText = objectStyle.findTextInTags(renderablePolygon.getDefenitionTags());
			textCanvas.drawTextAtPoint(polygonText,
							findPolygonTextDrawSettings(renderablePolygon, polygonTextDrawSettings),
							textPositionX, textPositionY);
		}

		SelectingPolygon selectingPolygonByRenderedPolygon = new SelectingPolygon(renderablePolygon,
						objectStyle.getDrawPriority(),
						drawingPolygon);
		selectingBuffer.addSelectingObject(selectingPolygonByRenderedPolygon);
	}

	/**
	 * Determine color to draw polygon border
	 *
	 * @param polygon polygon, color of which border need to determine
	 * @param sourceBorderDrawSettings draw settings of polygon border, using as
	 * source for finding
	 * @return color of polygon border
	 * @throws IllegalArgumentException polygon or sourceBorderDrawSettings is
	 * null
	 */
	private Color determinePolygonBorderColor(RenderableMapPolygon polygon,
					RenderableMapLineDrawSettings sourceBorderDrawSettings) throws IllegalArgumentException
	{
		if (polygon == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourceBorderDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsSelected = (polygon == objectToDrawAsSelected);
		if (drawAsSelected)
		{
			return SELECTED_OBJECT_COLOR;
		}
		else
		{
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
	}

	/**
	 * Determine paint to draw inner part of polygon
	 *
	 * @param polygon polygon, paint of which inner part paint need to determine
	 * @param sourcePolygonDrawSettings source draw settings of polygon, using for
	 * finding
	 * @return paint for drawing inner part of polygon
	 * @throws IllegalArgumentException polygon or sourcePolygonDrawSettings is
	 * null
	 */
	private Paint determinePolygonInnerPaint(RenderableMapPolygon polygon,
					RenderableMapPolygonDrawSettings sourcePolygonDrawSettings) throws IllegalArgumentException
	{
		if (polygon == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourcePolygonDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsSelected = (polygon == objectToDrawAsSelected);
		if (drawAsSelected)
		{
			return SELECTED_OBJECT_COLOR;
		}
		else
		{
			boolean drawAsHighlighted = (polygon == objectToDrawAsHighlighted);
			if (drawAsHighlighted)
			{
				return HIGHLIGHTED_OBJECT_COLOR;
			}
			else
			{
				return sourcePolygonDrawSettings.getPaint();
			}
		}
	}

	/**
	 * Determine draw settings of polygon text
	 *
	 * @param polygon polygon, which text draw settings need to determine
	 * @param sourcePolygonTextDrawSettings source draw settings of polygon text,
	 * using for finding
	 * @return draw settings of polygon text
	 * @throws IllegalArgumentException polygon or sourcePolygonTextDrawSettings
	 * is null
	 */
	private TextDrawSettings findPolygonTextDrawSettings(RenderableMapPolygon polygon,
					TextDrawSettings sourcePolygonTextDrawSettings) throws IllegalArgumentException
	{
		if (polygon == null)
		{
			throw new IllegalArgumentException();
		}
		if (sourcePolygonTextDrawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		boolean drawAsSelected = (polygon == objectToDrawAsSelected);
		if (drawAsSelected)
		{
			return sourcePolygonTextDrawSettings.createCopyWithColor(SELECTED_OBJECT_TEXT_COLOR);
		}
		else
		{
			boolean drawAsHighlighted = (polygon == objectToDrawAsHighlighted);
			if (drawAsHighlighted)
			{
				return sourcePolygonTextDrawSettings.createCopyWithColor(HIGHLIGHTED_OBJECT_TEXT_COLOR);
			}
			else
			{
				return sourcePolygonTextDrawSettings;
			}
		}
	}

	/**
	 * Create polygon for drawing by converting renderable polygon points to
	 * target canvas coordinates
	 *
	 * @param polygon polygon, which points will using to create drawing polygon
	 * @return drawing polygon, created by renderable polygon, in target canvas
	 * coordinates
	 * @throws IllegalArgumentException polygon is null
	 */
	private Polygon createDrawingPolygonByRenderablePolygonPoints(RenderableMapPolygon polygon) throws IllegalArgumentException
	{
		if (polygon == null)
		{
			throw new IllegalArgumentException();
		}

		Polygon drawingPolygon = new Polygon();

		for (int i = 0; i < polygon.getPointsCount(); i++)
		{
			Point2D pointOnCanvas = coordinatesConverter.goegraphicsToCanvas(polygon.getPoint(i));
			drawingPolygon.addPoint((int) pointOnCanvas.getX(), (int) pointOnCanvas.getY());
		}

		return drawingPolygon;
	}
}
