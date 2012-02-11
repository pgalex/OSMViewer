package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Параметры отображения на определенном масштабе
 * @author abc
 */
public class ScaledObjectStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Рисовать ли точку на данном масштабе
	 */
	public boolean drawPoint;
	/**
	 * Рисовать ли линию на данном масштабе
	 */
	public boolean drawLine;
	/**
	 * Рисовать ли многоугольник на данном масштабе
	 */
	public boolean drawPolygon;
	/**
	 * стиль точки
	 */
	public PointDrawStyle pointStyle;
	/**
	 * стиль линии
	 */
	public LineDrawStyle lineStyle;
	/**
	 * стиль многоугольника
	 */
	public PolygonDrawStyle polygonStyle;

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
	 * Считать из потока
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	@Override
	public void ReadFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			drawPoint = pInput.readBoolean();
			drawLine = pInput.readBoolean();
			drawPolygon = pInput.readBoolean();
			pointStyle.ReadFromStream(pInput);
			lineStyle.ReadFromStream(pInput);
			polygonStyle.ReadFromStream(pInput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Записать в поток
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
	 */
	@Override
	public void WriteToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeBoolean(drawPoint);
			pOutput.writeBoolean(drawLine);
			pOutput.writeBoolean(drawPolygon);
			pointStyle.WriteToStream(pOutput);
			lineStyle.WriteToStream(pOutput);
			polygonStyle.WriteToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
