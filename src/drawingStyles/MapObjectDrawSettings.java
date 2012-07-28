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
public class MapObjectDrawSettings implements MapObjectDrawStyle, ReadableMapData, WritableMapData, Comparable<MapObjectDrawSettings>
{
	/**
	 * Can be object with this tags a point (single node)
	 */
	private boolean canBePoint;
	/**
	 * Can be object with this tags a line (non closed way)
	 */
	private boolean canBeLine;
	/**
	 * Can be object with this tags a polygon (closed way)
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
	private DrawSettingsOnScaleArray scaledStyles;
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
		scaledStyles = new DrawSettingsOnScaleArray();
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
					TextTagsKeys pTextTagKeys, int pDrawPriority, String pDescription, DrawSettingsOnScaleArray pScaledStyles,
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
	 * Set default values into null fields
	 */
	private void initializeNullFields()
	{
		if (scaledStyles == null)
		{
			scaledStyles = new DrawSettingsOnScaleArray();
		}
		if (defenitionTags == null)
		{
			defenitionTags = new DefenitionTags();
		}
		if (textTagKeys == null)
		{
			textTagKeys = new TextTagsKeys();
		}
	}

	/**
	 * Can object be a point (one node) on a map
	 *
	 * @return Can object be a point on a map
	 */
	@Override
	public boolean canBePoint()
	{
		return canBePoint;
	}

	/**
	 * Can object be a line(non-closed way) on a map
	 *
	 * @return Can object be a line(non-closed way) on a map
	 */
	@Override
	public boolean canBeLine()
	{
		return canBeLine;
	}

	/**
	 * Can object be a polygon(closed way) on a map
	 *
	 * @return Can object be a polygon(closed way) on a map
	 */
	@Override
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
	 * Get draw priority
	 *
	 * @return draw priority
	 */
	@Override
	public int getDrawPriority()
	{
		return drawPriority;
	}

	/**
	 * Get object description
	 *
	 * @return object description
	 */
	@Override
	public String getDescription()
	{
		return description;
	}

	/**
	 * Get scaled drawing styles
	 *
	 * @return scaled drawing styles
	 */
	public DrawSettingsOnScaleArray getScaledStyles()
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
	 * Find point draw style on scale level
	 *
	 * @param pScaleLevel scale level
	 * @return point draw style on scale level
	 */
	@Override
	public PointDrawStyle findPointDrawStyle(int pScaleLevel)
	{
		return scaledStyles.findPointDrawStyle(pScaleLevel);
	}

	/**
	 * Find line style on scale level
	 *
	 * @param pScaleLevel scale level
	 * @return line draw style on scale level
	 */
	@Override
	public LineDrawStyle findLineDrawStyle(int pScaleLevel)
	{
		return scaledStyles.findLineDrawStyle(pScaleLevel);
	}

	/**
	 * Find polygon style on scale level
	 *
	 * @param pScaleLevel scale level
	 * @return polygon draw style on scale level
	 */
	@Override
	public PolygonDrawStyle findPolygonDrawStyle(int pScaleLevel)
	{
		return scaledStyles.findPolygonDrawStyle(pScaleLevel);
	}

	/**
	 * Find text style on scale level
	 *
	 * @param pScaleLevel scale level
	 * @return text draw style on scale level
	 */
	@Override
	public TextDrawStyle findTextDrawStyle(int pScaleLevel)
	{
		return scaledStyles.findTextDrawStyle(pScaleLevel);
	}

	/**
	 * Find value of tag that means text description of object
	 *
	 * @param pTags tags of object
	 * @return text description of object founded in tag. Empty if not found
	 */
	@Override
	public String findTextInTags(DefenitionTags pTags)
	{
		return textTagKeys.findTextInTags(pTags);
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
		{
			return 1;
		}
		if (defenitionTags.size() > pComparedStyle.getDefenitionTags().size())
		{
			return -1;
		}
		if (defenitionTags.size() < pComparedStyle.getDefenitionTags().size())
		{
			return 1;
		}
		return 0;
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
}
