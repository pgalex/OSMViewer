package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Стиль рисования линий (не замкнутых путей)
 *
 * @author abc
 */
public class LineDrawStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Шаблон сплошной линии
	 */
	public static final float[] SOLID_LINE_PATTERN =
	{
		1
	};
	/**
	 * Цвет линии
	 */
	private IOColor color;
	/**
	 * Тольщина линии
	 */
	private int width;
	/**
	 * Стиль линии (тире, точка тире) - шаблон для рисования линии. четные индексы
	 * - длинна участка на котором линия рисуется; нечетные - длинна пустых
	 * участков
	 */
	private float[] pattern;

	/**
	 * Конструктор
	 */
	public LineDrawStyle()
	{
		color = new IOColor(Color.BLACK);
		width = 1;
		pattern = SOLID_LINE_PATTERN;
	}

	/**
	 * Контсруктор
	 *
	 * @param pColor цвет. При нулевом значении задается автоматически
	 * @param pWidth толщина
	 * @param pPattern шаблон рисования. При нулевом значении задается
	 * автоматически
	 */
	public LineDrawStyle(IOColor pColor, int pWidth, float[] pPattern)
	{
		color = pColor;
		width = pWidth;
		pattern = pPattern;
		InitializeNullFields();
	}

	/**
	 * Считать из файла
	 *
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			color.readFromStream(pInput);

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
	 *
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			getColor().writeToStream(pOutput);
			pOutput.writeInt(getWidth());

			pOutput.writeInt(getPattern().length);
			for (int i = 0; i < getPattern().length; i++)
				pOutput.writeFloat(getPattern()[i]);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Получить цвет
	 *
	 * @return цвет
	 */
	public IOColor getColor()
	{
		return color;
	}

	/**
	 * Получить толщину
	 *
	 * @return толщина
	 */
	public int getWidth()
	{
		return width;
	}

	/**
	 * Получить шаблон рисования
	 *
	 * @return шаблон рисования
	 */
	public float[] getPattern()
	{
		return pattern;
	}

	/**
	 * Инициализировать null поля значениями по умолчанию
	 */
	private void InitializeNullFields()
	{
		if (color == null)
			color = new IOColor();
		if (pattern == null)
			pattern = SOLID_LINE_PATTERN;
		if (pattern.length == 0)
			pattern = SOLID_LINE_PATTERN;
	}
}
