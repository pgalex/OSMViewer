package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to draw any object on a map
 *
 * @author abc
 */
public class MapObjectStyle implements ReadableMapData, WritableMapData, Comparable<MapObjectStyle>
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
	 * "Keys" of tags that "value" should be drawen on map as text under object
	 *
	 * (Ключи тегов, значение которых должно выводиться на экран как текстовая
	 * подпись)
	 */
	private TextTagsKeys textTagKeys;
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
		textTagKeys = new TextTagsKeys();
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
	 * @param pTextTagKeys "Key" of tag that "value" should be drawen on map as
	 * text under object on a map
	 * @param pDrawPriority Drawing priority
	 * @param pDescription Description of map object
	 * @param pScaledStyles Drawing styles on each scale level
	 * @param pDefenitionTags Map object defenition tags
	 */
	public MapObjectStyle(boolean pCanBePoint, boolean pCanBeLine, boolean pCanBePolygon,
					TextTagsKeys pTextTagKeys, int pDrawPriority, String pDescription, ScaledObjectStyleCollection pScaledStyles,
					DefenitionTags pDefenitionTags)
	{
		canBePoint = pCanBePoint;
		canBeLine = pCanBeLine;
		canBePolygon = pCanBePolygon;
		textTagKeys = pTextTagKeys;
		drawPriority = pDrawPriority;
		description = pDescription;
		scaledStyles = pScaledStyles;
		defenitionTags = pDefenitionTags;
		initializeNullFields();
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
			textTagKeys.readFromStream(pInput);
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
			textTagKeys.writeToStream(pOutput);
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
	 * "Keys" of tags that "value" should be drawen on map as text under object
	 *
	 * @return tags keys
	 */
	public TextTagsKeys getTextTagKeys()
	{
		return textTagKeys;
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
	private void initializeNullFields()
	{
		if (scaledStyles == null)
			scaledStyles = new ScaledObjectStyleArray();
		if (defenitionTags == null)
			defenitionTags = new DefenitionTags();
		if (textTagKeys == null)
			textTagKeys = new TextTagsKeys();
	}

	/**
	 * Compare to. using for sorting
	 *
	 * @param pComparedStyle styled for comparing
	 * @return this object 0 - equal, -1 - more tags count, 1 - less tags count
	 */
	@Override
	public int compareTo(MapObjectStyle pComparedStyle)
	{
		if (pComparedStyle == null)
			return 1;
		if (defenitionTags.size() > pComparedStyle.getDefenitionTags().size())
			return -1;
		if (defenitionTags.size() < pComparedStyle.getDefenitionTags().size())
			return 1;
		return 0;
	}
}
