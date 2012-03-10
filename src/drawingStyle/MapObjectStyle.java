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
		drawPriority = -1;
		textTagKey = "";
		description = "";

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
			pOutput.writeBoolean(canBePoint);
			pOutput.writeBoolean(canBeLine);
			pOutput.writeBoolean(canBePolygon);
			pOutput.writeUTF(textTagKey);
			pOutput.writeInt(drawPriority);
			pOutput.writeUTF(description);

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
}
