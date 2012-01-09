package drawingStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
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
	 * Приоритет при рисовании
	 */
	public int drawPriority;
	/**
	 * Стили на каждом из уровней масштаба
	 */
	public ScaledObjectStyle[] scaledStyles;
	/**
	 * Теги, опеределяющие тип объекта
	 */
	public ArrayList<MapTag> defenitionTags;

	/**
	 * Конструктор
	 */
	public MapObjectStyle()
	{
		scaledStyles = new ScaledObjectStyle[ProgramSettings.SCALE_LEVELS_COUNT];
		defenitionTags = new ArrayList<MapTag>();
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
		drawPriority = -1;
	}

	/**
	 * Сравнить теги без учета их порядка
	 * @param pTags теги для сравнения
	 * @return совпадают ли теги
	 */
	public boolean CompareDefenitionTags(ArrayList<MapTag> pTags)
	{
		//заведомо несопадающие теги
		if (pTags == null)
			return false;
		if (pTags.size() != defenitionTags.size())
			return false;

		//сравнение списков
		for (MapTag parameterTempTag : pTags)
		{
			boolean tempCompareResult = false;
			for (MapTag thisTempTag : defenitionTags)
			{
				//нашелся ли тег
				if (parameterTempTag.compareTo(thisTempTag))
				{
					tempCompareResult = true;
					break;
				}
			}
			if (tempCompareResult == false)
				return false;
		}
		return true;
	}
}
