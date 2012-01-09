package drawingStyle;

import java.awt.Image;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Класс стиля рисования точки
 * @author abc
 */
public class PointDrawStyle implements ReadableMapData, WriteableMapData
{
	/**
	 * Значок
	 */
	public Image icon;
	/**
	 * Имя файла значка
	 */
	public String iconFileName;

	/**
	 * Конструктор
	 */
	public PointDrawStyle()
	{
		icon = null;
		iconFileName = "";
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
			iconFileName = pInput.readUTF();
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
			pOutput.writeUTF(iconFileName);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
