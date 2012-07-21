package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to draw object on specified scale level. Параметры отображения на
 * определенном масштабе
 *
 * @author abc
 */
public class DrawStyleOnScale implements ReadableMapData, WritableMapData
{
	/**
	 * Is need to draw point. Рисовать ли точку на данном масштабе
	 */
	private boolean drawPoint;
	/**
	 * Is need to draw line. Рисовать ли линию на данном масштабе
	 */
	private boolean drawLine;
	/**
	 * Is need to draw polygon. Рисовать ли многоугольник на данном масштабе
	 */
	private boolean drawPolygon;
	/**
	 * point drawing style. стиль точки
	 */
	private PointDrawSettings pointStyle;
	/**
	 * line drawing style. стиль линии
	 */
	private LineDrawSettings lineStyle;
	/**
	 * polygon drawing style. стиль многоугольника
	 */
	private PolygonDrawSettings polygonStyle;
	/**
	 * Map object text(caption) font. Шрифт текста для имени
	 */
	private IOFont textFont;
	/**
	 * Text(caption) color. Цвет текстовой подписи
	 */
	private IOColor textColor;

	/**
	 * Default constructor
	 */
	public DrawStyleOnScale()
	{
		pointStyle = new PointDrawSettings();
		lineStyle = new LineDrawSettings();
		polygonStyle = new PolygonDrawSettings();
		drawPoint = false;
		drawLine = false;
		drawPolygon = false;
		textColor = new IOColor();
		textFont = new IOFont();
	}

	/**
	 * Constructor
	 *
	 * @param pDrawPoint Is need to draw point
	 * @param pDrawLine Is need to draw line
	 * @param pDrawPolygon Is need to draw polygon
	 * @param pPointStyle point drawing style. Auto-initialize if null
	 * @param pLineStyle line drawing style. Auto-initialize if null
	 * @param pPolygonStyle polygon drawing style. Auto-initialize if null
	 * @param pTextColor Text(caption) color. Цвет текстовой подписи.
	 * Auto-initialize if null
	 * @param pTextFont Map object text(caption) font. Шрифт текстововй подписи.
	 * Auto-initialize if null
	 */
	public DrawStyleOnScale(boolean pDrawPoint, boolean pDrawLine, boolean pDrawPolygon,
					PointDrawSettings pPointStyle, LineDrawSettings pLineStyle, PolygonDrawSettings pPolygonStyle,
					IOColor pTextColor, IOFont pTextFont)
	{
		drawPoint = pDrawPoint;
		drawLine = pDrawLine;
		drawPolygon = pDrawPolygon;
		pointStyle = pPointStyle;
		lineStyle = pLineStyle;
		polygonStyle = pPolygonStyle;
		textColor = pTextColor;
		textFont = pTextFont;

		initializeNullFields();
	}

	/**
	 * Read from stream
	 *
	 * @param pInput reading stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			drawPoint = pInput.readBoolean();
			drawLine = pInput.readBoolean();
			drawPolygon = pInput.readBoolean();
			pointStyle.readFromStream(pInput);
			lineStyle.readFromStream(pInput);
			polygonStyle.readFromStream(pInput);

			textColor.readFromStream(pInput);
			textFont.readFromStream(pInput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Write into stream
	 *
	 * @param pOutput output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeBoolean(isDrawPoint());
			pOutput.writeBoolean(isDrawLine());
			pOutput.writeBoolean(isDrawPolygon());
			pointStyle.writeToStream(pOutput);
			lineStyle.writeToStream(pOutput);
			polygonStyle.writeToStream(pOutput);

			textColor.writeToStream(pOutput);
			textFont.writeToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Is need to draw point.
	 *
	 * @return Is need to draw point.
	 */
	public boolean isDrawPoint()
	{
		return drawPoint;
	}

	/**
	 * Is need to draw line
	 *
	 * @return Is need to draw line
	 */
	public boolean isDrawLine()
	{
		return drawLine;
	}

	/**
	 * Is need to draw polygon
	 *
	 * @return Is need to draw polygon
	 */
	public boolean isDrawPolygon()
	{
		return drawPolygon;
	}

	/**
	 * Get point drawing style
	 *
	 * @return point drawing style
	 */
	public PointDrawSettings getPointStyle()
	{
		return pointStyle;
	}

	/**
	 * Get line drawing style
	 *
	 * @return line drawing style
	 */
	public LineDrawSettings getLineStyle()
	{
		return lineStyle;
	}

	/**
	 * Get polygon drawing style
	 *
	 * @return polygon drawing style
	 */
	public PolygonDrawSettings getPolygonStyle()
	{
		return polygonStyle;
	}

	/**
	 * Get text (caption) font
	 *
	 * @return text (caption) font
	 */
	public IOFont getTextFont()
	{
		return textFont;
	}

	/**
	 * Get text (caption) color
	 *
	 * @return text (caption) color
	 */
	public IOColor getTextColor()
	{
		return textColor;
	}

	/**
	 * Auto-initialize null fields
	 */
	private void initializeNullFields()
	{
		if (pointStyle == null)
			pointStyle = new PointDrawSettings();

		if (lineStyle == null)
			lineStyle = new LineDrawSettings();

		if (polygonStyle == null)
			polygonStyle = new PolygonDrawSettings();

		if (textColor == null)
			textColor = new IOColor();

		if (textFont == null)
			textFont = new IOFont();
	}
}