package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.awt.Color;
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
	private ImageFromFile fillImage;

	/**
	 * Конструктор
	 */
	public PolygonDrawStyle()
	{
		fillColor = new IOColor(Color.GRAY);
		borderDrawStyle = new LineDrawStyle();
		fillImage = new ImageFromFile();
	}

	/**
	 * Конструктор
	 *
	 * @param pFillColor Цвет заполнения. При нулевом значении задается автоматически
	 * @param pBorderDrawStyle Стиль рисования границы. При нулевом значении задается автоматически
	 * @param pFillImage Текстура для заполнения. При нулевом значении задается автоматически
	 */
	public PolygonDrawStyle(IOColor pFillColor, LineDrawStyle pBorderDrawStyle, ImageFromFile pFillImage)
	{
		fillColor = pFillColor;
		borderDrawStyle = pBorderDrawStyle;
		fillImage = pFillImage;
		InitializeNullFields();
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
			fillColor = IOColor.readFromStream(pInput);
			getBorderDrawStyle().readFromStream(pInput);
			getFillImage().readFromStream(pInput);
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
			getFillColor().writeToStream(pOutput);
			getBorderDrawStyle().writeToStream(pOutput);
			getFillImage().writeToStream(pOutput);
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
	public ImageFromFile getFillImage()
	{
		return fillImage;
	}
	
	/**
	 * Инициализировать null поля значениями по умолчанию
	 */
	private void InitializeNullFields()
	{
		if (fillColor == null)
			fillColor = new IOColor();

		if (borderDrawStyle == null)
			borderDrawStyle = new LineDrawStyle();

		if (fillImage == null)
			fillImage = new ImageFromFile();
	}
}
