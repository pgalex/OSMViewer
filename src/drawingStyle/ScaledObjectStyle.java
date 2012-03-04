package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Параметры отображения на определенном масштабе
 *
 * @author abc
 */
public class ScaledObjectStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Рисовать ли точку на данном масштабе
	 */
	private boolean drawPoint;
	/**
	 * Рисовать ли линию на данном масштабе
	 */
	private boolean drawLine;
	/**
	 * Рисовать ли многоугольник на данном масштабе
	 */
	private boolean drawPolygon;
	/**
	 * стиль точки
	 */
	private PointDrawStyle pointStyle;
	/**
	 * стиль линии
	 */
	private LineDrawStyle lineStyle;
	/**
	 * стиль многоугольника
	 */
	private PolygonDrawStyle polygonStyle;

	/**
	 * Конструктор
	 */
	public ScaledObjectStyle()
	{
		pointStyle = new PointDrawStyle();
		lineStyle = new LineDrawStyle();
		polygonStyle = new PolygonDrawStyle();
		drawPoint = false;
		drawLine = false;
		drawPolygon = false;
	}

	/**
	 * Конструктор
	 *
	 * @param pDrawPoint Рисовать ли точку на данном масштабе
	 * @param pDrawLine Рисовать ли линию на данном масштабе
	 * @param pDrawPolygon Рисовать ли многоугольник на данном масштабе
	 * @param pPointStyle стиль точки. При нулевом значении задается автоматически
	 * @param pLineStyle стиль линии. При нулевом значении задается автоматически
	 * @param pPolygonStyle стиль многоугольника. При нулевом значении задается
	 * автоматически
	 */
	public ScaledObjectStyle(boolean pDrawPoint, boolean pDrawLine, boolean pDrawPolygon,
					PointDrawStyle pPointStyle, LineDrawStyle pLineStyle, PolygonDrawStyle pPolygonStyle)
	{
		drawPoint = pDrawPoint;
		drawLine = pDrawLine;
		drawPolygon = pDrawPolygon;

		pointStyle = pPointStyle;
		if (pointStyle == null)
			pointStyle = new PointDrawStyle();

		lineStyle = pLineStyle;
		if (lineStyle == null)
			lineStyle = new LineDrawStyle();

		polygonStyle = pPolygonStyle;
		if (polygonStyle == null)
			polygonStyle = new PolygonDrawStyle();
	}

	/**
	 * Считать из потока
	 *
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			drawPoint = pInput.readBoolean();
			drawLine = pInput.readBoolean();
			drawPolygon = pInput.readBoolean();
			getPointStyle().readFromStream(pInput);
			getLineStyle().readFromStream(pInput);
			getPolygonStyle().readFromStream(pInput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Записать в поток
	 *
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeBoolean(isDrawPoint());
			pOutput.writeBoolean(isDrawLine());
			pOutput.writeBoolean(isDrawPolygon());
			getPointStyle().writeToStream(pOutput);
			getLineStyle().writeToStream(pOutput);
			getPolygonStyle().writeToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Рисовать ли точку на данном масштабе
	 *
	 * @return Рисовать ли точку на данном масштабе
	 */
	public boolean isDrawPoint()
	{
		return drawPoint;
	}

	/**
	 * Рисовать ли линию на данном масштабе
	 *
	 * @return Рисовать ли линию на данном масштабе
	 */
	public boolean isDrawLine()
	{
		return drawLine;
	}

	/**
	 * Рисовать ли многоугольник на данном масштабе
	 *
	 * @return Рисовать ли многоугольник на данном масштабе
	 */
	public boolean isDrawPolygon()
	{
		return drawPolygon;
	}

	/**
	 * Получить стиль точки
	 *
	 * @return стиль точки
	 */
	public PointDrawStyle getPointStyle()
	{
		return pointStyle;
	}

	/**
	 * Получить стиль линии
	 *
	 * @return стиль линии
	 */
	public LineDrawStyle getLineStyle()
	{
		return lineStyle;
	}

	/**
	 * Получить стиль многоугольника
	 *
	 * @return стиль многоугольника
	 */
	public PolygonDrawStyle getPolygonStyle()
	{
		return polygonStyle;
	}
}
