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
	 * @return индекс добавленного стиля в массиве
	 */
	public int Add(MapObjectStyle pNewStyle)
	{
		styles.add(pNewStyle);
		return styles.size() - 1;
	}

	/**
	 * Редактировать стиль отображения
	 * @param pEditedIndex индекс редактируемого стиля
	 * @param pNewStyle новые данные
	 */
	public void Edit(int pEditedIndex, MapObjectStyle pNewStyle)
	{
		if ((pEditedIndex >= 0) && (pEditedIndex < styles.size()))
			styles.set(pEditedIndex, pNewStyle);
	}

	/**
	 * Получить кол-во стилей в массиве
	 * @return кол-во стилей в массиве
	 */
	public int getStylesCount()
	{
		return styles.size();
	}
}
