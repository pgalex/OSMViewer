package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Массив стилей объекта на каждом из уровней масштаба
 *
 * @author pgalex
 */
public class ScaledObjectStyleArray implements ReadableMapData, WritableMapData
{
	/**
	 * Текущее кол-во уровней масштаба (12 нижних уровней osm). Не писать функций
	 * позволяющих получить эту константу вне класса
	 */
	private static final int DEFAULT_SCALE_LEVELS_COUNT = 12;
	/**
	 * Стили на каждом из уровней масштаба
	 */
	private ScaledObjectStyle[] scaledStyles;

	/**
	 * Конструкор
	 */
	public ScaledObjectStyleArray()
	{
		scaledStyles = new ScaledObjectStyle[DEFAULT_SCALE_LEVELS_COUNT];
		for (int i = 0; i < DEFAULT_SCALE_LEVELS_COUNT; i++)
			scaledStyles[i] = new ScaledObjectStyle();
	}

	/**
	 * Конструктор. Для тестов
	 *
	 * @param pScaleLevelsCount кол-во уровней масштаба
	 */
	public ScaledObjectStyleArray(int pScaleLevelsCount)
	{
		scaledStyles = new ScaledObjectStyle[pScaleLevelsCount];
		for (int i = 0; i < pScaleLevelsCount; i++)
			scaledStyles[i] = new ScaledObjectStyle();
	}

	/**
	 * Считать из потока
	 *
	 * @param pInput поток для чтения
	 * @throws IOException ошибка чтения
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			int scaledStylesLength = pInput.readInt();
			scaledStyles = new ScaledObjectStyle[scaledStylesLength];
			for (int i = 0; i < scaledStyles.length; i++)
			{
				scaledStyles[i] = new ScaledObjectStyle();
				scaledStyles[i].readFromStream(pInput);
			}
			resetToDefault();
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Записать в поток
	 *
	 * @param pOutput поток для записи
	 * @throws IOException ошибка записи
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeInt(scaledStyles.length);
			for (int i = 0; i < scaledStyles.length; i++)
				scaledStyles[i].writeToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Установить новый стиль на определенном уровне масштаба
	 *
	 * @param pScaleLevel уровень масштаба. Значение за пределами допустимых
	 * приводяться к граничным
	 * @param pNewScaledStyle новый стиль
	 */
	public void setStyleOnScale(int pScaleLevel, ScaledObjectStyle pNewScaledStyle)
	{
		scaledStyles[normalizeScaleLevel(pScaleLevel)] = pNewScaledStyle;
	}

	/**
	 * Получить стиль на определонном уровне масштаба
	 *
	 * @param pScaleLevel уровень масштаба. Значение за пределами допустимых
	 * приводяться к граничным
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
	public int count()
	{
		return scaledStyles.length;
	}

	/**
	 * Является ли текущее кол-во уровней масштаба кол-вом по умолчанию
	 *
	 * @return Является ли текущее кол-во уровней масштаба кол-вом по умолчанию
	 */
	public boolean isDefaultLevelsCount()
	{
		return scaledStyles.length == DEFAULT_SCALE_LEVELS_COUNT;
	}

	/**
	 * Установить кол-во уровней масштаба по умолчанию. Новые стили добавляются
	 * как копия последеного. Лишиние обрезаются
	 */
	private void resetToDefault()
	{
		if (isDefaultLevelsCount())
			return;
		if (scaledStyles.length == 0)
			return;
							
		ScaledObjectStyle[] scaledStylesDefaultCount = new ScaledObjectStyle[DEFAULT_SCALE_LEVELS_COUNT];

		int minLenght = Math.min(scaledStylesDefaultCount.length, scaledStyles.length);
		System.arraycopy(scaledStyles, 0, scaledStylesDefaultCount, 0, minLenght);

		if (scaledStylesDefaultCount.length > minLenght)
		{
			for (int i = minLenght; i < scaledStylesDefaultCount.length; i++)
				scaledStylesDefaultCount[i] = scaledStyles[scaledStyles.length - 1];
		}
		scaledStyles = scaledStylesDefaultCount;
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
