package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import map.MapTag;

/**
 * Стиль отображения объекта
 *
 * @author abc
 */
public class MapObjectStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Шрифт текстовой подписи по умолчанию
	 */
	private static final Font DEFAULT_FONT = new Font("Arial", 0, 14);
	/**
	 * Текущее кол-во уровней масштаба (12 нижних уровней osm). Не писать функций
	 * позволяющих получить эту константу вне класса
	 */
	private static final int DEFAULT_SCALE_LEVELS_COUNT = 12;
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
	 * Ключ тега, значение которого будет выводиться на экран как текстовая
	 * подпись
	 */
	public String textTagKey;
	/**
	 * Приоритет при рисовании
	 */
	public int drawPriority;
	/**
	 * Описание объекта
	 */
	public String description;
	/**
	 * Шрифт текста для имени
	 */
	public Font textFont;
	/**
	 * Цвет текстовой подписи
	 */
	public IOColor textColor;
	/**
	 * Стили на каждом из уровней масштаба
	 */
	private ScaledObjectStyle[] scaledStyles;
	/**
	 * Теги, опеределяющие тип объекта
	 */
	public ArrayList<MapTag> defenitionTags;

	/**
	 * Конструктор
	 *
	 */
	public MapObjectStyle()
	{
		scaledStyles = new ScaledObjectStyle[DEFAULT_SCALE_LEVELS_COUNT];
		for (int i = 0; i < DEFAULT_SCALE_LEVELS_COUNT; i++)
			scaledStyles[i] = new ScaledObjectStyle();
		defenitionTags = new ArrayList<MapTag>();
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
		drawPriority = -1;
		textTagKey = "";
		description = "";
		textColor = new IOColor(Color.BLACK);
		textFont = DEFAULT_FONT;
	}

	/**
	 * Констуктор. Используется в тестах
	 *
	 * @param pScaleLevelsCount кол-во уровней масштаба
	 */
	public MapObjectStyle(int pScaleLevelsCount)
	{
		scaledStyles = new ScaledObjectStyle[pScaleLevelsCount];
		for (int i = 0; i < pScaleLevelsCount; i++)
			scaledStyles[i] = new ScaledObjectStyle();
		defenitionTags = new ArrayList<MapTag>();
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
		drawPriority = -1;
		textTagKey = "";
		description = "";
		textColor = new IOColor(Color.BLACK);
		textFont = DEFAULT_FONT;
	}

	/**
	 * Сравнить теги без учета их порядка. Каждый тег из defenitionTags должен
	 * входить в pTags
	 *
	 * @param pTags теги для сравнения
	 * @return совпадают ли теги
	 */
	public boolean compareDefenitionTags(ArrayList<MapTag> pTags)
	{
		//заведомо несопадающие теги
		if (pTags == null)
			return false;
		if (defenitionTags.size() > pTags.size())
			return false;
		// пустые списки считаются равны
		if (defenitionTags.isEmpty() && pTags.isEmpty())
			return true;

		if (defenitionTags.isEmpty() && !pTags.isEmpty())
			return false;

		for (MapTag defenitionTempTag : defenitionTags)
		{
			boolean currentCompareResult = false;
			for (MapTag parameterTempTag : pTags)
			{
				if (defenitionTempTag.compareTo(parameterTempTag))
				{
					currentCompareResult = true;
					break;
				}
			}
			if (!currentCompareResult)
				return false;
		}
		return true;
	}

	/**
	 * Считать из потока
	 *
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			canBePoint = pInput.readBoolean();
			canBeLine = pInput.readBoolean();
			canBePolygon = pInput.readBoolean();
			textTagKey = pInput.readUTF();
			drawPriority = pInput.readInt();
			description = pInput.readUTF();

			textColor = IOColor.readFromStream(pInput);

			String fontFamily = pInput.readUTF();
			int fontStyle = pInput.readInt();
			int fontSize = pInput.readInt();
			textFont = new Font(fontFamily, fontStyle, fontSize);

			int scaledStylesLength = pInput.readInt();
			scaledStyles = new ScaledObjectStyle[scaledStylesLength];
			for (int i = 0; i < scaledStyles.length; i++)
			{
				scaledStyles[i] = new ScaledObjectStyle();
				scaledStyles[i].readFromStream(pInput);
			}

			int tagsCount = pInput.readInt();
			for (int i = 0; i < tagsCount; i++)
			{
				MapTag tempTag = new MapTag();
				tempTag.readFromStream(pInput);
				defenitionTags.add(tempTag);
			}

			resetScaleLevelsCountToDefault();
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Записать в поток
	 *
	 * @param pOutput поток вывода
	 * @throws IOException запись не удалась
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeBoolean(canBePoint);
			pOutput.writeBoolean(canBeLine);
			pOutput.writeBoolean(canBePolygon);
			pOutput.writeUTF(textTagKey);
			pOutput.writeInt(drawPriority);
			pOutput.writeUTF(description);

			textColor.writeToStream(pOutput);

			pOutput.writeUTF(textFont.getFamily());
			pOutput.writeInt(textFont.getStyle());
			pOutput.writeInt(textFont.getSize());

			pOutput.writeInt(scaledStyles.length);

			for (int i = 0; i < scaledStyles.length; i++)
				scaledStyles[i].writeToStream(pOutput);

			pOutput.writeInt(defenitionTags.size());
			for (MapTag tempTag : defenitionTags)
				tempTag.writeToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Установить новый стиль на определенном уровне масштаба
	 *
	 * @param pScaleLevel уровень масштаба
	 * @param pNewScaledStyle новый стиль
	 */
	public void setStyleOnScale(int pScaleLevel, ScaledObjectStyle pNewScaledStyle)
	{
		scaledStyles[normalizeScaleLevel(pScaleLevel)] = pNewScaledStyle;
	}

	/**
	 * Получить стиль на определонном уровне масштаба
	 *
	 * @param pScaleLevel уровень масштаба
	 * @return стиль
	 */
	public ScaledObjectStyle getStyleOnScale(int pScaleLevel)
	{
		return scaledStyles[normalizeScaleLevel(pScaleLevel)];
	}

	/**
	 * Получить текущее кол-во уровней масштаба в стиле
	 *
	 * @return текущее кол-во уровней масштаба
	 */
	public int getCurrentScaleLevelsCount()
	{
		return scaledStyles.length;
	}

	/**
	 * Является ли текущее кол-во уровней масштаба кол-вом по умолчанию
	 *
	 * @return Является ли текущее кол-во уровней масштаба кол-вом по умолчанию
	 */
	public boolean isDefaultScaleLevelsCount()
	{
		return scaledStyles.length == DEFAULT_SCALE_LEVELS_COUNT;
	}

	/**
	 * Установить кол-во уровней масштаба по умолчанию. Новые стили добавляются
	 * как копия последеного. Лишиние обрезаются
	 */
	private void resetScaleLevelsCountToDefault()
	{
		if (isDefaultScaleLevelsCount())
			return;
		if (scaledStyles.length == 0)
			return;

		ScaledObjectStyle[] tempScaledStyles = new ScaledObjectStyle[DEFAULT_SCALE_LEVELS_COUNT];

		int minLenght = Math.min(tempScaledStyles.length, scaledStyles.length);
		System.arraycopy(scaledStyles, 0, tempScaledStyles, 0, minLenght);

		if (tempScaledStyles.length > minLenght)
		{
			for (int i = minLenght; i < tempScaledStyles.length; i++)
				tempScaledStyles[i] = scaledStyles[scaledStyles.length - 1];
		}
		scaledStyles = tempScaledStyles;
	}

	/**
	 * Нормализовать маштаб с учетом текущего кол-ва уровней в стиле
	 *
	 * @param pScaleLevel масштаб для нормализации
	 * @return масштаб в пределах от 0 до текущего максимального уровня
	 */
	private int normalizeScaleLevel(int pScaleLevel)
	{
		int normalizedScaleLevel = pScaleLevel;
		if (normalizedScaleLevel < 0)
			normalizedScaleLevel = 0;
		if (normalizedScaleLevel >= scaledStyles.length)
			normalizedScaleLevel = scaledStyles.length - 1;
		return normalizedScaleLevel;
	}
}
