package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Класс стиля рисования точки
 *
 * @author abc
 */
public class PointDrawSettings implements ReadableMapData, WritableMapData
{
	/**
	 * Значок
	 */
	private IOIcon icon;

	/**
	 * Конструктор
	 */
	public PointDrawSettings()
	{
		icon = new IOIcon();
	}

	/**
	 * Конструктор
	 *
	 * @param pIcon значок. Если null, то создается автоматически
	 */
	public PointDrawSettings(IOIcon pIcon)
	{
		icon = pIcon;
		initializeNullFields();
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
			icon.readFromStream(pInput);
		}
		catch (Exception ex)
		{
			throw new IOException();
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
			icon.writeToStream(pOutput);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Получить значок
	 *
	 * @return значок
	 */
	public IOIcon getIcon()
	{
		return icon;
	}

	/**
	 * Инициализировать null поля значениями по умолчанию
	 */
	private void initializeNullFields()
	{
		if (icon == null)
			icon = new IOIcon();
	}
}
