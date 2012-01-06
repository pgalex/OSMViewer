package drawingStyle;

import java.util.ArrayList;

/**
 * Редактор стилей отображения объектов с возможностью добавления/удаления и сохранения изменений
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
	 * Добавить новый объект
	 * @param pNewStyle стиль отображения нового объекта
	 * @return id, который был присвоен стилю добавленного объекта 
	 */
	public int Add(MapObjectStyle pNewStyle)
	{
		MapObjectStyle addingStyle = new MapObjectStyle(GetNextId(), pNewStyle);
		styles.add(addingStyle);
		return addingStyle.getId();
	}
	////////////////////////////  тесты для трех функ. ниже
	/**
	 * Получить кол-во стилей в массиве
	 * @return 
	 */
	public int getStylesCount()
	{
		return styles.size();
	}

	/**
	 * Получить стиль по индексу в массиве
	 * @param pIndex
	 * @return 
	 */
	public MapObjectStyle getStyle(int pIndex)
	{
		if ((pIndex >= 0) && (pIndex < styles.size()))
			return styles.get(pIndex);
		else
			return null;
	}

	/**
	 * Выдать новый уникальный id для объекта карты
	 * @return id
	 */
	protected int GetNextId()
	{
		// как максимальный
		int maxId = -1;
		for (MapObjectStyle tempStyle : styles)
		{
			if (tempStyle.getId() > maxId)
				maxId = tempStyle.getId();
		}
		return maxId + 1;
	}
}
