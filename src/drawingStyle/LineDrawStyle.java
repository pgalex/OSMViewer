package drawingStyle;

import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Стиль рисования линий (не замкнутых путей)
 * @author abc
 */
public class LineDrawStyle implements ReadableMapData, WriteableMapData
{
	/**
	 * Цвет линии
	 */
	public Color color;
	/**
	 * Тольщина линии
	 */
	public int width;
	// стиль линии

	/**
	 * Конструктор
	 */
	public LineDrawStyle()
	{
		color = Color.BLACK;
		width = 1;
	}

	/**
	 * Считать из файла
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
			color = new Color(r, g, b, a);

			width = pInput.readInt();
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Записать в файл
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
	 */
	@Override
	public void WriteToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeInt(color.getAlpha());
			pOutput.writeInt(color.getRed());
			pOutput.writeInt(color.getGreen());
			pOutput.writeInt(color.getBlue());
			pOutput.writeInt(width);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
