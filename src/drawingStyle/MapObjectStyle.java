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
import settings.ProgramSettings;

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
	 * Шрифт текста для имени
	 */
	public Font textFont;
	/**
	 * Цвет текстовой подписи
	 */
	public Color textColor;
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
		for (int i = 0; i < ProgramSettings.SCALE_LEVELS_COUNT; i++)
			scaledStyles[i] = new ScaledObjectStyle();
		defenitionTags = new ArrayList<MapTag>();
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
		drawPriority = -1;
		textTagKey = "";
		description = "";
		textColor = Color.BLACK;
		textFont = ProgramSettings.DEFAULT_FONT;
	}

	/**
	 * Сравнить теги без учета их порядка
	 *
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

	/**
	 * Считать из потока
	 *
	 * @param pInput поток чтения
	 * @throws IOException чтение не удалось
	 */
	@Override
	public void ReadFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			canBePoint = pInput.readBoolean();
			canBeLine = pInput.readBoolean();
			canBePolygon = pInput.readBoolean();
			textTagKey = pInput.readUTF();
			drawPriority = pInput.readInt();
			description = pInput.readUTF();

			int textColorA = pInput.readInt();
			int textColorR = pInput.readInt();
			int textColorG = pInput.readInt();
			int textColorB = pInput.readInt();

			textColor = new Color(textColorR, textColorG, textColorB, textColorA);

			String fontFamily = pInput.readUTF();
			int fontStyle = pInput.readInt();
			int fontSize = pInput.readInt();
			textFont = new Font(fontFamily, fontStyle, fontSize);

			for (int i = 0; i < ProgramSettings.SCALE_LEVELS_COUNT; i++)
				scaledStyles[i].ReadFromStream(pInput);

			int tagsCount = pInput.readInt();
			for (int i = 0; i < tagsCount; i++)
			{
				MapTag tempTag = new MapTag();
				tempTag.ReadFromStream(pInput);
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
	public void WriteToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			pOutput.writeBoolean(canBePoint);
			pOutput.writeBoolean(canBeLine);
			pOutput.writeBoolean(canBePolygon);
			pOutput.writeUTF(textTagKey);
			pOutput.writeInt(drawPriority);
			pOutput.writeUTF(description);

			pOutput.writeInt(textColor.getAlpha());
			pOutput.writeInt(textColor.getRed());
			pOutput.writeInt(textColor.getGreen());
			pOutput.writeInt(textColor.getBlue());

			pOutput.writeUTF(textFont.getFamily());
			pOutput.writeInt(textFont.getStyle());
			pOutput.writeInt(textFont.getSize());

			for (int i = 0; i < ProgramSettings.SCALE_LEVELS_COUNT; i++)
				scaledStyles[i].WriteToStream(pOutput);

			pOutput.writeInt(defenitionTags.size());
			for (MapTag tempTag : defenitionTags)
				tempTag.WriteToStream(pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
