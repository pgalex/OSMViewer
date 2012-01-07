package drawingStyle;

import java.util.ArrayList;

/**
 * Редактор стилей отображения объектов с возможностью добавления/удаления и сохранения изменений
 * Уникальный id стиля определяется его индексом в массиве, чтобы бытро определять нужный стиль
 * Все новые элементы идут последними - за счет этого старые отображаются корректно
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

	///////// тесты
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
}
