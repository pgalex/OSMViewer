package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Класс стиля рисования точки
 * @author abc
 */
public class PointDrawStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Значок
	 */
	public ImageFromFile icon;

	/**
	 * Конструктор
	 */
	public PointDrawStyle()
	{
		icon = new ImageFromFile();
	}

	/**
	 * Считать из файла
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	@Override
	public void ReadFromStream(DataInputStream pInput) throws IOException
	{
		icon.ReadFromStream(pInput);
	}

	/**
	 * Записать в файл
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
	 */
	@Override
	public void WriteToStream(DataOutputStream pOutput) throws IOException
	{
		icon.WriteToStream(pOutput);
	}
}
