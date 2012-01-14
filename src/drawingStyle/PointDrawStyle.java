package drawingStyle;

import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Класс стиля рисования точки
 * @author abc
 */
public class PointDrawStyle implements ReadableMapData, WriteableMapData
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
