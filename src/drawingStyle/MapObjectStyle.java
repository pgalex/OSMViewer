package drawingStyle;

import java.util.ArrayList;
import map.MapTag;
import map.ProgramSettings;

/**
 * Стиль отображения объекта
 * @author abc
 */
public class MapObjectStyle
{
	/**
	 * Стили на каждом из уровней масштаба
	 */
	public ScaledObjectStyle[] scaledStyles;
	/**
	 * Теги, опеределяющие тип объекта
	 */
	public ArrayList<MapTag> defenitionTags;
	/**
	 * Уникальный идентификатор типа объекта (чтобы не искать по тегам)
	 */
	private int id;
	/**
	 * Может ли быть точкой
	 */
	public boolean canBePoint;
	/**
	 * Может ли быть линией
	 */
	public boolean canBeLine;
	/**
	 * Может ли быть многоугольником (замкнутая линия)
	 */
	public boolean canBePolygon;
	/**
	 * Ключ тега, значение которого будет выводиться на экран как текстовая подпись
	 */
	public String textTagKey;

	/**
	 * Конструктор для стиля, где id не важен
	 */
	public MapObjectStyle()
	{
		scaledStyles = new ScaledObjectStyle[ProgramSettings.SCALE_LEVELS_COUNT];
		defenitionTags = new ArrayList<MapTag>();
		id = -1;
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
	}

	/**
	 * Конструктор стиля с id
	 * @param pId Уникальный идентификатор, который нельзя будет изменить после создания объекта
	 */
	public MapObjectStyle(int pId)
	{
		scaledStyles = new ScaledObjectStyle[ProgramSettings.SCALE_LEVELS_COUNT];
		defenitionTags = new ArrayList<MapTag>();
		id = pId;
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
	}

	/**
	 * Конструктор по копии, но с новым id
	 * @param pId новый id
	 * @param pSource исходный стиль
	 */
	public MapObjectStyle(int pId, MapObjectStyle pSource)
	{
		id = pId;
		scaledStyles = pSource.scaledStyles;
		defenitionTags = pSource.defenitionTags;
		canBePoint = pSource.canBePoint;
		canBeLine = pSource.canBeLine;
		canBePolygon = pSource.canBePolygon;
	}

	/**
	 * @return the id
	 */
	public int getId()
	{
		return id;
	}
}
