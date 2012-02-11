package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.w3c.dom.css.RGBColor;

/**
 * Стиль многоугольника (замкнутой линии)
 * @author abc
 */
public class PolygonDrawStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Цвет заполнения
	 */
	public Color fillColor;
	/**
	 * Стиль рисования границы
	 */
	public LineDrawStyle borderDrawStyle;
	/**
	 * Текстура для заполнения
	 */
	public ImageFromFile fillImage;

	/**
	 * Конструктор
	 */
	public PolygonDrawStyle()
	{
		fillColor = Color.GRAY;
		borderDrawStyle = new LineDrawStyle();
		fillImage = new ImageFromFile();
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
			int a = pInput.readInt();
			int r = pInput.readInt();
			int g = pInput.readInt();
			int b = pInput.readInt();
			fillColor = new Color(r, g, b, a);
			
			borderDrawStyle.ReadFromStream(pInput);
			fillImage.ReadFromStream(pInput);
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
			pOutput.writeInt(fillColor.getAlpha());
			pOutput.writeInt(fillColor.getRed());
			pOutput.writeInt(fillColor.getGreen());
			pOutput.writeInt(fillColor.getBlue());
			borderDrawStyle.WriteToStream(pOutput);
			fillImage.WriteToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
