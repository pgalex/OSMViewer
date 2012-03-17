package drawingStyle;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import map.DefenitionTags;

/**
 * How to draw any object on a map
 *
 * @author abc
 */
public class MapObjectStyle implements ReadableMapData, WritableMapData
{
	/**
	 * Can be object with this tags a point ( single node ). Using when finding
	 * object style by tags
	 */
	private boolean canBePoint;
	/**
	 * Can be object with this tags a line ( non closed way ). Using when finding
	 * object style by tags
	 */
	private boolean canBeLine;
	/**
	 * Can be object with this tags a polygon ( closed way ). Using when finding
	 * object style by tags
	 */
	private boolean canBePolygon;
	/**
	 * "Key" of tag that "value" should be drawen on map as text under object
	 *
	 * (Ключ тега, значение которого будет выводиться на экран как текстовая
	 * подпись)
	 */
	private String textTagKey;
	/**
	 * Drawing priority (front back)
	 */
	private int drawPriority;
	/**
	 * Description of map object
	 */
	private String description;
	/**
	 * How to draw object on each scale level
	 */
	private ScaledObjectStyleCollection scaledStyles;
	/**
	 * Tags that define map object
	 */
	private DefenitionTags defenitionTags;

	/**
	 * Default constructor
	 *
	 */
	public MapObjectStyle()
	{
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
		drawPriority = 1;
		textTagKey = "";
		description = "";
		scaledStyles = new ScaledObjectStyleArray();
		defenitionTags = new DefenitionTags();
	}

	/**
	 * Constructor
	 *
	 * @param pCanBePoint Can be object with this tags a point ( single node )
	 * @param pCanBeLine Can be object with this tags a line ( non closed way )
	 * @param pCanBePolygon Can be object with this tags a polygon ( closed way )
	 * @param pTextTagKey "Key" of tag that "value" should be drawen on map as
	 * text under object on a map
	 * @param pDrawPriority Drawing priority
	 * @param pDescription Description of map object
	 * @param pScaledStyles Drawing styles on each scale level
	 * @param pDefenitionTags Map object defenition tags
	 */
	public MapObjectStyle(boolean pCanBePoint, boolean pCanBeLine, boolean pCanBePolygon,
					String pTextTagKey, int pDrawPriority, String pDescription, ScaledObjectStyleCollection pScaledStyles,
					DefenitionTags pDefenitionTags)
	{
		canBePoint = pCanBePoint;
		canBeLine = pCanBeLine;
		canBePolygon = pCanBePolygon;
		textTagKey = pTextTagKey;
		drawPriority = pDrawPriority;
		description = pDescription;
		scaledStyles = pScaledStyles;
		defenitionTags = pDefenitionTags;
		InitializeNullFields();
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
			defenitionTags.readFromStream(pInput);
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
			defenitionTags.writeToStream(pOutput);
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

	/**
	 * Get scaled drawing styles
	 *
	 * @return scaled drawing styles
	 */
	public ScaledObjectStyleCollection getScaledStyles()
	{
		return scaledStyles;
	}

	/**
	 * Get map object defenition tags
	 *
	 * @return map object defenition tags
	 */
	public DefenitionTags getDefenitionTags()
	{
		return defenitionTags;
	}

	/**
	 * Set default values into null fields
	 */
	private void InitializeNullFields()
	{
		if (scaledStyles == null)
			scaledStyles = new ScaledObjectStyleArray();
		if (defenitionTags == null)
			defenitionTags = new DefenitionTags();
	}
}
