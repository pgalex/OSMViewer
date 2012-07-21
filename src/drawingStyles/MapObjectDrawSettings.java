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
public class MapObjectDrawSettings implements ReadableMapData, WritableMapData, Comparable<MapObjectDrawSettings>
{
	/**
	 * Can be object with this tags a point ( single node )
	 */
	private boolean canBePoint;
	/**
	 * Can be object with this tags a line ( non closed way )
	 */
	private boolean canBeLine;
	/**
	 * Can be object with this tags a polygon ( closed way )
	 */
	private boolean canBePolygon;
	/**
	 * "Keys" of tags that "value" can be drawen on map as text under object
	 *
	 */
	private TextTagsKeys textTagKeys;
	/**
	 * Drawing priority
	 */
	private int drawPriority;
	/**
	 * Description of map object
	 */
	private String description;
	/**
	 * How to draw object on each scale level
	 */
	private DrawStyleOnScaleArray scaledStyles;
	/**
	 * Tags that define map object
	 */
	private DefenitionTags defenitionTags;

	/**
	 * Default constructor
	 *
	 */
	public MapObjectDrawSettings()
	{
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
		drawPriority = 1;
		textTagKeys = new TextTagsKeys();
		description = "";
		scaledStyles = new DrawStyleOnScaleArray();
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
	public MapObjectDrawSettings(boolean pCanBePoint, boolean pCanBeLine, boolean pCanBePolygon,
					TextTagsKeys pTextTagKeys, int pDrawPriority, String pDescription, DrawStyleOnScaleArray pScaledStyles,
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
	 * Can object be a point (one node) on a map
	 *
	 * @return Can object be a point on a map
	 */
	public boolean canBePoint()
	{
		return canBePoint;
	}

	/**
	 * Can object be a line(non-closed way) on a map
	 *
	 * @return Can object be a line(non-closed way) on a map
	 */
	public boolean canBeLine()
	{
		return canBeLine;
	}

	/**
	 * Can object be a polygon(closed way) on a map
	 *
	 * @return Can object be a polygon(closed way) on a map
	 */
	public boolean canBePolygon()
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
	public DrawStyleOnScaleArray getScaledStyles()
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
			scaledStyles = new DrawStyleOnScaleArray();
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
	public int compareTo(MapObjectDrawSettings pComparedStyle)
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