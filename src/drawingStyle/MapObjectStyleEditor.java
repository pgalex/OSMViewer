package drawingStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import settings.ProgramSettings;

/**
 * Редактор стилей отображения объектов с возможностью добавления/удаления и сохранения изменений.
 * Уникальный id стиля определяется его индексом в массиве, чтобы бытро определять нужный стиль.
 * Id стиля будет использоваться для распознавания объектов вместо тегов при работе с большими картами.
 * Все новые элементы идут последними - за счет этого старые отображаются корректно.
 * Удаление пока не предусмотрено
 * @author abc
 */
public class MapObjectStyleEditor
{
	/**
	 * Стили всех объектов
	 */
	private ArrayList<MapObjectStyle> styles;

	/**
	 * Конструктор
	 */
	public MapObjectStyleEditor()
	{
		styles = new ArrayList<MapObjectStyle>();
	}

	/**
	 * Добавить новый стиль отображения объекта
	 * @param pNewStyle новый стиль отображения
	 * @return индекс добавленного стиля в массиве (id)
	 */
	public int AddStyle(MapObjectStyle pNewStyle)
	{
		styles.add(pNewStyle);
		return styles.size() - 1;
	}

	/**
	 * Редактировать стиль отображения
	 * @param pEditedIndex индекс редактируемого стиля (id)
	 * @param pNewStyle новые данные
	 */
	public void EditStyle(int pEditedIndex, MapObjectStyle pNewStyle)
	{
		if ((pEditedIndex >= 0) && (pEditedIndex < styles.size()))
			styles.set(pEditedIndex, pNewStyle);
	}

	/**
	 * Получить стиль отображения
	 * @param pIndex индекс стиля (id)
	 * @return стиль отображения
	 */
	public MapObjectStyle GetStyle(int pIndex)
	{
		if ((pIndex >= 0) && (pIndex < styles.size()))
			return styles.get(pIndex);
		else
			return null;
	}

	/**
	 * Получить кол-во стилей в массиве
	 * @return кол-во стилей в массиве
	 */
	public int getStylesCount()
	{
		return styles.size();
	}

	/**
	 * Очистить список стилей
	 */
	public void Clear()
	{
		styles.clear();
	}

	/**
	 * Прочитать стили из файла
	 * @param pFileName имя файла
	 * @throws IOException чтение не удалось
	 */
	public void LoadFromFile(String pFileName) throws IOException
	{
		if (pFileName.isEmpty())
			throw new IOException();
		
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(pFileName));
			
			// если вдруг в файле кол-во масштабов не равно текущему в контанте
			int scaleLevesCount = input.readInt();
			if (scaleLevesCount != ProgramSettings.SCALE_LEVELS_COUNT)
				throw new IOException();
			
			styles.clear();
			int styleCount = input.readInt();
			for (int i = 0; i < styleCount; i++)
			{
				MapObjectStyle tempStyle = new MapObjectStyle();
				tempStyle.ReadFromStream(input);
				styles.add(tempStyle);
			}
			
			input.close();
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Сохранить стили в файл
	 * @param pFileName имя файла
	 * @throws IOException запись не удалась
	 */
	public void SaveToFile(String pFileName) throws IOException
	{
		if (pFileName.isEmpty())
			throw new IOException();

		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(pFileName));
			output.writeInt(ProgramSettings.SCALE_LEVELS_COUNT);
			output.writeInt(styles.size());
			for (int i = 0; i < styles.size(); i++)
				styles.get(i).WriteToStream(output);

			output.close();
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}
