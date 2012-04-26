package drawingStyle;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Collection of ScaledObjectStyle
 *
 * @author pgalex
 */
public class ScaledObjectStyleArray implements ScaledObjectStyleCollection
{
	/**
	 * Currect default scale levels count. This scale levels are linked with OSM
	 * scale level ( from 18 to 6 ). Contant should be used only inside this
	 * class. Текущее кол-во уровней масштаба (12 нижних уровней osm). Не писать
	 * функций позволяющих получить эту константу вне класса.
	 */
	private static final int DEFAULT_SCALE_LEVELS_COUNT = 12;
	/**
	 * Drawing style on each scale level. Стили на каждом из уровней масштаба
	 */
	private ScaledObjectStyle[] scaledStyles;

	/**
	 * Defaul contructor
	 */
	public ScaledObjectStyleArray()
	{
		scaledStyles = new ScaledObjectStyle[DEFAULT_SCALE_LEVELS_COUNT];
		for (int i = 0; i < DEFAULT_SCALE_LEVELS_COUNT; i++)
			scaledStyles[i] = new ScaledObjectStyle();
	}

	/**
	 * Constructor with specified scale levels count. Only for tests. Конструктор.
	 * Для тестов
	 *
	 * @param pScaleLevelsCount scale levels count
	 */
	public ScaledObjectStyleArray(int pScaleLevelsCount)
	{
		scaledStyles = new ScaledObjectStyle[pScaleLevelsCount];
		for (int i = 0; i < pScaleLevelsCount; i++)
			scaledStyles[i] = new ScaledObjectStyle();
	}

	/**
	 * Read from stream
	 *
	 * @param pInput reading stream
	 * @throws IOException reading error
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
	 * Write into stream
	 *
	 * @param pOutput output stream
	 * @throws IOException writing error
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
	 * Set style on specifiec scale level. If level is out of range value will not
	 * be set
	 *
	 * @param pScaleLevel scale level
	 * @param pNewScaledStyle new style on scale level
	 * @throws ArrayIndexOutOfBoundsException scale level is out of range
	 * @throws NullPointerException new scaled style is null 
	 */
	public void setStyleOnScale(int pScaleLevel, ScaledObjectStyle pNewScaledStyle) throws ArrayIndexOutOfBoundsException, NullPointerException
	{
		if (pScaleLevel < 0 || pScaleLevel >= scaledStyles.length)
			throw new ArrayIndexOutOfBoundsException();
		if (pNewScaledStyle == null)
			throw new NullPointerException();

		scaledStyles[pScaleLevel] = pNewScaledStyle;
	}

	/**
	 * Get style on specifiec scale level. If level is out of range returns
	 * nearest correct value
	 *
	 * @param pScaleLevel scale level
	 * @return style on specifiec scale level
	 */
	@Override
	public ScaledObjectStyle getStyleOnScale(int pScaleLevel)
	{
		return scaledStyles[normalizeScaleLevel(pScaleLevel)];
	}

	/**
	 * Get scale levels count
	 *
	 * @return currect scale levels count
	 */
	@Override
	public int count()
	{
		return scaledStyles.length;
	}

	/**
	 * Is currect scale levels count has default values. Является ли текущее
	 * кол-во уровней масштаба кол-вом по умолчанию
	 *
	 * @return Is currect scale levels count has default values. Является ли
	 * текущее кол-во уровней масштаба кол-вом по умолчанию
	 */
	public boolean isDefaultLevelsCount()
	{
		return scaledStyles.length == DEFAULT_SCALE_LEVELS_COUNT;
	}

	/**
	 * Reset scaled styles array to default length. If default length is bigger
	 * last style copies to the tail of array. If less - out of bounds values will
	 * be deleted. Установить кол-во уровней масштаба по умолчанию. Новые стили
	 * добавляются как копия последеного. Лишиние обрезаются
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
	 * Normalize scale level if it out of array bounds Нормализовать маштаб с
	 * учетом текущего кол-ва уровней в стиле
	 *
	 * @param pScaleLevel scale level
	 * @return scale level in array bounds
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
