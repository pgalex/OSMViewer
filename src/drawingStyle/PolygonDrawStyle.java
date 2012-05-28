package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Стиль многоугольника (замкнутой линии)
 *
 * @author abc
 */
public class PolygonDrawStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Цвет заполнения
	 */
	private IOColor fillColor;
	/**
	 * Стиль рисования границы
	 */
	private LineDrawStyle borderDrawStyle;
	/**
	 * Текстура для заполнения
	 */
	private IOIcon fillImage;

	/**
	 * Конструктор
	 */
	public PolygonDrawStyle()
	{
		fillColor = new IOColor();
		borderDrawStyle = new LineDrawStyle();
		fillImage = new IOIcon();
	}

	/**
	 * Конструктор
	 *
	 * @param pFillColor Цвет заполнения. При нулевом значении задается
	 * автоматически
	 * @param pBorderDrawStyle Стиль рисования границы. При нулевом значении
	 * задается автоматически
	 * @param pFillImage Текстура для заполнения. При нулевом значении задается
	 * автоматически
	 */
	public PolygonDrawStyle(IOColor pFillColor, LineDrawStyle pBorderDrawStyle, IOIcon pFillImage)
	{
		fillColor = pFillColor;
		borderDrawStyle = pBorderDrawStyle;
		fillImage = pFillImage;

		initializeNullFields();
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
			fillColor.readFromStream(pInput);
			borderDrawStyle.readFromStream(pInput);
			fillImage.readFromStream(pInput);
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
			fillColor.writeToStream(pOutput);
			borderDrawStyle.writeToStream(pOutput);
			fillImage.writeToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Получить цвет заполнения
	 *
	 * @return цвет заполнения
	 */
	public IOColor getFillColor()
	{
		return fillColor;
	}

	/**
	 * Получить стиль границы
	 *
	 * @return стиль границы
	 */
	public LineDrawStyle getBorderDrawStyle()
	{
		return borderDrawStyle;
	}

	/**
	 * Получить текстуру заполнения
	 *
	 * @return текстура заполнения
	 */
	public IOIcon getFillImage()
	{
		return fillImage;
	}

	/**
	 * Инициализировать null поля значениями по умолчанию
	 */
	private void initializeNullFields()
	{
		if (fillColor == null)
			fillColor = new IOColor();

		if (borderDrawStyle == null)
			borderDrawStyle = new LineDrawStyle();

		if (fillImage == null)
			fillImage = new IOIcon();
	}
}
