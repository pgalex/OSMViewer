package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Стиль рисования линий (не замкнутых путей)
 * @author abc
 */
public class LineDrawStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Цвет линии
	 */
	public Color color;
	/**
	 * Тольщина линии
	 */
	public int width;
	/**
	 * Стиль линии (тире, точка тире) - шаблон для рисования линии.
	 * четные индексы - длинна участка на котором линия рисуется; нечетные - длинна пустых участков
	 */
	public float[] pattern;

	/**
	 * Конструктор
	 */
	public LineDrawStyle()
	{
		color = Color.BLACK;
		width = 1;
		pattern = new float[1];
		pattern[0] = 1;//сполшная линия
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
			
			int patternCount = pInput.readInt();
			pattern = new float[patternCount];
			for (int i = 0; i < patternCount; i++)
				pattern[i] = pInput.readFloat();
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
			
			pOutput.writeInt(pattern.length);
			for (int i = 0; i < pattern.length; i++)
				pOutput.writeFloat(pattern[i]);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
