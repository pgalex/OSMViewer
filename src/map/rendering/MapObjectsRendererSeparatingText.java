package map.rendering;

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
	 * Create renderer
	 *
	 * @param targetCanvas Canvas to draw map objects
	 * @param styleViewerForRendering Style viewer using to find drawing style of
	 * object
	 * @param converter object that will be using for coordinates converting while
	 * drawing
	 * @param renderingScaleLevel scale level using for rendering
	 * @throws IllegalArgumentException targetCanvas, styleViewerForRendering or
	 * converter is null
	 */
	public MapObjectsRendererSeparatingText(Graphics2D targetCanvas, StyleViewer styleViewerForRendering,
					CoordinatesConverter converter, int renderingScaleLevel) throws IllegalArgumentException
	{
		if (targetCanvas == null || styleViewerForRendering == null || converter == null)
		{
			throw new IllegalArgumentException();
		}

		canvas = targetCanvas;
		styleViewer = styleViewerForRendering;
		coordinatesConverter = converter;
		scaleLevel = renderingScaleLevel;
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

		PointDrawSettings pointStyle = objectStyle.findPointDrawStyle(scaleLevel);
		if (pointStyle == null)
		{
			return;
		}


		Point2D pointPositionOnCanvas = coordinatesConverter.goegraphicsToCanvas(pointToRender.getPosition());

		BufferedImage pointImage = pointStyle.getIcon();
		if (pointImage != null)
		{
			canvas.drawImage(pointImage, (int) (pointPositionOnCanvas.getX() - pointImage.getWidth() / 2),
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
	 * Draw map object text(name, description etc) on canvas, by text center x and
	 * top bound of text
	 *
	 * @param objectStyle map object draw style
	 * @param objectTags map object tags using to find text
	 * @param textCenterX text center x on canvas
	 * @param textTop text top bound
	 */
	private void drawMapObjectTextUnderPoint(MapObjectDrawSettings objectStyle,
					DefenitionTags objectTags, double textCenterX, double textTop)
	{
		if (objectStyle == null || objectTags == null)
		{
			return;
		}

		TextDrawSettings textStyle = objectStyle.findTextDrawStyle(scaleLevel);
		if (textStyle == null)
		{
			return;
		}

		String drawingText = objectStyle.findTextInTags(objectTags);
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
	 * @param objectStyle map object draw style
	 * @param objectTags map object tags using to find text
	 * @param textCenterX text center x on canvas
	 * @param textCenterY text center y on canvas
	 */
	private void drawMapObjectTextAtPoint(MapObjectDrawSettings objectStyle,
					DefenitionTags objectTags, double textCenterX, double textCenterY)
	{
		if (objectStyle == null || objectTags == null)
		{
			return;
		}

		TextDrawSettings textStyle = objectStyle.findTextDrawStyle(scaleLevel);
		if (textStyle == null)
		{
			return;
		}

		String drawingText = objectStyle.findTextInTags(objectTags);
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
	 * @param lineToRender line on a map
	 */
	@Override
	public void renderLine(MapLine lineToRender)
	{
		if (lineToRender == null)
			return;
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

		PolygonDrawSettings polygonStyle = objectStyle.findPolygonDrawStyle(scaleLevel);
		if (polygonStyle == null)
		{
			return;
		}

		Polygon drawingPolygon = createDrawingPolygonByMapPolygon(polygonToRender);

		// inner part
		canvas.setPaint(polygonStyle.getPaint());
		canvas.fillPolygon(drawingPolygon);

		// border
		LineDrawSettings borderStyle = polygonStyle.getBorderDrawStyle();
		canvas.setStroke(borderStyle.getStroke());
		canvas.setColor(borderStyle.getColor());
		canvas.drawPolygon(drawingPolygon);

		Point2D textPosition = coordinatesConverter.goegraphicsToCanvas(polygonToRender.getCenterPoint());

		drawMapObjectTextAtPoint(objectStyle, polygonToRender.getDefenitionTags(),
						textPosition.getX(), textPosition.getY());
	}

	/**
	 * Create polygon for drawing on canvas by converting MapPolygon points
	 *
	 * @param mapPolygon polygon on map
	 * @return polygon on canvas
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
