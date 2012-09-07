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
public class MapObjectDrawSettings implements MapObjectDrawStyle, ReadableMapData, WritableMapData
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
	private DrawSettingsOnScaleArray drawSettingsOnScales;
	/**
	 * Tags that define map object
	 */
	private DefenitionTags defenitionTags;

	/**
	 * Create with default values
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
		drawSettingsOnScales = new DrawSettingsOnScaleArray();
		defenitionTags = new DefenitionTags();
	}

	/**
	 * Create with parameters
	 *
	 * @param canObjectBePoint Can be object with this tags a point ( single node )
	 * @param canObjectBeLine Can be object with this tags a line ( non closed way )
	 * @param canObjectBePolygon Can be object with this tags a polygon ( closed way )
	 * @param textKeys "Key" of tag that "value" should be drawen on map as
	 * text under object on a map
	 * @param objectDrawPriority Drawing priority
	 * @param objectDescription Description of map object
	 * @param settingsOnScales Drawing styles on each scale level
	 * @param objectDefenitionTags Map object defenition tags
	 */
	public MapObjectDrawSettings(boolean canObjectBePoint, boolean canObjectBeLine, boolean canObjectBePolygon,
					TextTagsKeys textKeys, int objectDrawPriority, String objectDescription, DrawSettingsOnScaleArray settingsOnScales,
					DefenitionTags objectDefenitionTags)
	{
		canBePoint = canObjectBePoint;
		canBeLine = canObjectBeLine;
		canBePolygon = canObjectBePolygon;
		textTagKeys = textKeys;
		drawPriority = objectDrawPriority;
		description = objectDescription;
		drawSettingsOnScales = settingsOnScales;
		defenitionTags = objectDefenitionTags;
		initializeNullFields();
	}

	/**
	 * Set default values into null fields
	 */
	private void initializeNullFields()
	{
		if (drawSettingsOnScales == null)
		{
			drawSettingsOnScales = new DrawSettingsOnScaleArray();
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
		return drawSettingsOnScales;
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
	 * @param scaleLevel scale level
	 * @return point draw style on scale level. Null if not found
	 */
	@Override
	public PointDrawStyle findPointDrawStyle(int scaleLevel)
	{
		if (canBePoint)
		{
			return drawSettingsOnScales.findPointDrawStyle(scaleLevel);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Find line style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return line draw style on scale level. Null if not found
	 */
	@Override
	public LineDrawStyle findLineDrawStyle(int scaleLevel)
	{
		if (canBeLine)
		{
			return drawSettingsOnScales.findLineDrawStyle(scaleLevel);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Find polygon style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return polygon draw style on scale level. Null if not found
	 */
	@Override
	public PolygonDrawStyle findPolygonDrawStyle(int scaleLevel)
	{
		if (canBePolygon)
		{
			return drawSettingsOnScales.findPolygonDrawStyle(scaleLevel);
		}
		else
		{
			return null;
		}
	}

	/**
	 * Find text style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return text draw style on scale level. Null if not found
	 */
	@Override
	public TextDrawStyle findTextDrawStyle(int scaleLevel)
	{
		return drawSettingsOnScales.findTextDrawStyle(scaleLevel);
	}

	/**
	 * Find value of tag in tags that means text description of object
	 *
	 * @param tagsWhereFindText tags of object where to find text description
	 * @return text description of object founded in tags. Empty if not found
	 */
	@Override
	public String findTextInTags(DefenitionTags tagsWhereFindText)
	{
		return textTagKeys.findTextInTags(tagsWhereFindText);
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			canBePoint = input.readBoolean();
			canBeLine = input.readBoolean();
			canBePolygon = input.readBoolean();
			textTagKeys.readFromStream(input);
			drawPriority = input.readInt();
			description = input.readUTF();
			drawSettingsOnScales.readFromStream(input);
			defenitionTags.readFromStream(input);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			output.writeBoolean(canBePoint);
			output.writeBoolean(canBeLine);
			output.writeBoolean(canBePolygon);
			textTagKeys.writeToStream(output);
			output.writeInt(drawPriority);
			output.writeUTF(description);
			drawSettingsOnScales.writeToStream(output);
			defenitionTags.writeToStream(output);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
