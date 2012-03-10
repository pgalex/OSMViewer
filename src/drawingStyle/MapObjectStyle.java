package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
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
	 * Может ли быть точкой
	 */
	private boolean canBePoint;
	/**
	 * Может ли быть линией
	 */
	private boolean canBeLine;
	/**
	 * Может ли быть многоугольником (замкнутая линия)
	 */
	private boolean canBePolygon;
	/**
	 * Ключ тега, значение которого будет выводиться на экран как текстовая
	 * подпись
	 */
	private String textTagKey;
	/**
	 * Приоритет при рисовании
	 */
	private int drawPriority;
	/**
	 * Описание объекта
	 */
	private String description;
	/**
	 * Стиль на каждом уровне масштаба
	 */
	public ScaledObjectStyleArray scaledStyles;
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
		scaledStyles = new ScaledObjectStyleArray();
		defenitionTags = new ArrayList<MapTag>();
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
		drawPriority = 1;
		textTagKey = "";
		description = "";
	}

	/**
	 * Конструктор
	 *
	 * @param pCanBePoint Может ли быть точкой
	 * @param pCanBeLine Может ли быть линией
	 * @param pCanBePolygon Может ли быть многоугольником
	 * @param pTextTagKey Ключ тега, значение которого будет выводиться на экран
	 * как текстовая подпись
	 * @param pDrawPriority Приоритет при рисовании
	 * @param pDescription Описание объекта
	 */
	public MapObjectStyle(boolean pCanBePoint, boolean pCanBeLine, boolean pCanBePolygon,
					String pTextTagKey, int pDrawPriority, String pDescription)
	{
		canBePoint = pCanBePoint;
		canBeLine = pCanBeLine;
		canBePolygon = pCanBePolygon;
		textTagKey = pTextTagKey;
		drawPriority = pDrawPriority;
		description = pDescription;
		
		scaledStyles = new ScaledObjectStyleArray();
		defenitionTags = new ArrayList<MapTag>();
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

			scaledStyles.readFromStream(pInput);

			int tagsCount = pInput.readInt();
			for (int i = 0; i < tagsCount; i++)
			{
				MapTag tempTag = new MapTag();
				tempTag.readFromStream(pInput);
				defenitionTags.add(tempTag);
			}
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
			pOutput.writeBoolean(isCanBePoint());
			pOutput.writeBoolean(isCanBeLine());
			pOutput.writeBoolean(isCanBePolygon());
			pOutput.writeUTF(getTextTagKey());
			pOutput.writeInt(getDrawPriority());
			pOutput.writeUTF(getDescription());

			scaledStyles.writeToStream(pOutput);

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
	 * Может ли быть точкой
	 *
	 * @return the canBePoint
	 */
	public boolean isCanBePoint()
	{
		return canBePoint;
	}

	/**
	 * Может ли быть линией
	 *
	 * @return Может ли быть линией
	 */
	public boolean isCanBeLine()
	{
		return canBeLine;
	}

	/**
	 * Может ли быть многоугольником (замкнутая линия)
	 *
	 * @return Может ли быть многоугольником
	 */
	public boolean isCanBePolygon()
	{
		return canBePolygon;
	}

	/**
	 * Получить ключ тега, значение которого будет выводиться на экран как
	 * текстовая подпись
	 *
	 * @return Ключ тега
	 */
	public String getTextTagKey()
	{
		return textTagKey;
	}

	/**
	 * Получить приоритет при рисовании
	 *
	 * @return приоритет при рисовании
	 */
	public int getDrawPriority()
	{
		return drawPriority;
	}

	/**
	 * Получить описание объекта
	 *
	 * @return описание объекта
	 */
	public String getDescription()
	{
		return description;
	}
}
