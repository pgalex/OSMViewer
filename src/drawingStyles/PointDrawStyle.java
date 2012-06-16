package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Класс стиля рисования точки. Установка значений полей только из конструктора
 *
 * @author abc
 */
public class PointDrawStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Значок
	 */
	private IOIcon icon;

	/**
	 * Конструктор
	 */
	public PointDrawStyle()
	{
		icon = new IOIcon();
	}

	/**
	 * Конструктор
	 *
	 * @param pIcon значок. Если null, то создается автоматически
	 */
	public PointDrawStyle(IOIcon pIcon)
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
		icon.readFromStream(pInput);
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
		icon.writeToStream(pOutput);
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