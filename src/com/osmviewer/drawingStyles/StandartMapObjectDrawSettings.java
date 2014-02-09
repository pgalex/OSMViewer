package com.osmviewer.drawingStyles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;

/**
 * How to draw any object on a map
 *
 * @author abc
 */
public class StandartMapObjectDrawSettings
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
	 * "Keys" of tags that "value" can be using as a text description of object
	 */
	private TextTagsKeys textTagKeys;
	/**
	 * Drawing priority if map object - point
	 */
	private int pointDrawPriority;
	/**
	 * Drawing priority if map object - line
	 */
	private int lineDrawPriority;
	/**
	 * Drawing priority if object - polygon
	 */
	private int polygonDrawPriority;
	/**
	 * Description of map object
	 */
	private String description;
	/**
	 * Simple name of object (like "tertiary road", etc)
	 */
	private String name;
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
	public StandartMapObjectDrawSettings()
	{
		canBePoint = false;
		canBeLine = false;
		canBePolygon = false;
		pointDrawPriority = 0;
		lineDrawPriority = 0;
		polygonDrawPriority = 0;
		textTagKeys = new TextTagsKeys();
		description = "";
		name = "";
		drawSettingsOnScales = new DrawSettingsOnScaleArray();
		defenitionTags = new DefenitionTags();
	}

	/**
	 * Can object be a point (one node) on a map
	 *
	 * @return Can object be a point on a map
	 */
	public boolean isCanBePoint()
	{
		return canBePoint;
	}

	/**
	 * Can object be a line(non-closed way) on a map
	 *
	 * @return Can object be a line(non-closed way) on a map
	 */
	public boolean isCanBeLine()
	{
		return canBeLine;
	}

	/**
	 * Can object be a polygon(closed way) on a map
	 *
	 * @return Can object be a polygon(closed way) on a map
	 */
	public boolean isCanBePolygon()
	{
		return canBePolygon;
	}

	/**
	 * Set that object can be a point
	 */
	public void setCanBePoint()
	{
		canBePoint = true;
	}

	/**
	 * Set that object can not be a point
	 */
	public void setCanNotBePoint()
	{
		canBePoint = false;
	}

	/**
	 * Set that object can be a line
	 */
	public void setCanBeLine()
	{
		canBeLine = true;
	}

	/**
	 * Set that object can not be a line
	 */
	public void setCanNotBeLine()
	{
		canBeLine = false;
	}

	/**
	 * Set that object can be a polygon
	 */
	public void setCanBePolygon()
	{
		canBePolygon = true;
	}

	/**
	 * Set that object can not be a polygon
	 */
	public void setCanNotBePolygon()
	{
		canBePolygon = false;
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
	public int getPointDrawPriority()
	{
		return pointDrawPriority;
	}

	/**
	 * Set new point draw priority
	 *
	 * @param pointDrawPriorityToSet new point draw priority
	 */
	public void setPointDrawPriority(int pointDrawPriorityToSet)
	{
		pointDrawPriority = pointDrawPriorityToSet;
	}

	/**
	 * Get draw priority if map object - line
	 *
	 * @return line draw priority
	 */
	public int getLineDrawPriority()
	{
		return lineDrawPriority;
	}

	/**
	 * Set new line draw priority
	 *
	 * @param lineDrawPriorityToSet new line draw priority
	 */
	public void setLineDrawPriority(int lineDrawPriorityToSet)
	{
		lineDrawPriority = lineDrawPriorityToSet;
	}

	/**
	 * Get draw priority if map object - polygon
	 *
	 * @return polygon draw priority
	 */
	public int getPolygonDrawPriority()
	{
		return polygonDrawPriority;
	}

	/**
	 * Set new polygon draw priority
	 *
	 * @param polygonDrawPriorityToSet new line draw priority
	 */
	public void setPolygonDrawPriority(int polygonDrawPriorityToSet)
	{
		polygonDrawPriority = polygonDrawPriorityToSet;
	}

	/**
	 * Get object description
	 *
	 * @return object description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * Set new description
	 *
	 * @param descriptionToSet new description. Must be not null
	 * @throws IllegalArgumentException descriptionToSet is null
	 */
	public void setDescription(String descriptionToSet) throws IllegalArgumentException
	{
		if (descriptionToSet == null)
		{
			throw new IllegalArgumentException("descriptionToSet is null");
		}

		description = descriptionToSet;
	}

	/**
	 * Get draw settings on scales levels styles
	 *
	 * @return scaled drawing styles
	 */
	public DrawSettingsOnScaleArray getDrawSettingsOnScales()
	{
		return drawSettingsOnScales;
	}

	/**
	 * Set new draw settings on scales
	 *
	 * @param settingsOnScalesToSet new draw settings on scales
	 * @throws IllegalArgumentException settingsOnScalesToSet is null
	 */
	public void setDrawSettingsOnScales(DrawSettingsOnScaleArray settingsOnScalesToSet) throws IllegalArgumentException
	{
		if (settingsOnScalesToSet == null)
		{
			throw new IllegalArgumentException("settingsOnScalesToSet is null");
		}

		drawSettingsOnScales = settingsOnScalesToSet;
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
	 * Set new object defentition tags
	 *
	 * @param tagsToSet new tags
	 * @throws IllegalArgumentException tagsToSet is null
	 */
	public void setDefenitionTags(DefenitionTags tagsToSet) throws IllegalArgumentException
	{
		if (tagsToSet == null)
		{
			throw new IllegalArgumentException("tagsToSet is null");
		}

		defenitionTags = tagsToSet;
	}

	/**
	 * Get simple name of object
	 *
	 * @return simple name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Set new simple name of object (like "tertiary road", etc)
	 *
	 * @param nameToSet new name
	 * @throws IllegalArgumentException nameToSet is null
	 */
	public void setName(String nameToSet) throws IllegalArgumentException
	{
		if (nameToSet == null)
		{
			throw new IllegalArgumentException("nameToSet is null");
		}

		name = nameToSet;
	}

	/**
	 * Find point draw style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return point draw style on scale level. Null if not found
	 */
	/*public RenderableMapPointDrawSettings findPointDrawSettings(int scaleLevel)
	{
		if (canBePoint)
		{
			return drawSettingsOnScales.findPointDrawSettings(scaleLevel);
		}
		else
		{
			return null;
		}
	}*/

	/**
	 * Find line style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return line draw style on scale level. Null if not found
	 */
	/*public RenderableMapLineDrawSettings findLineDrawSettings(int scaleLevel)
	{
		if (canBeLine)
		{
			return drawSettingsOnScales.findLineDrawSettings(scaleLevel);
		}
		else
		{
			return null;
		}
	}*/

	/**
	 * Find polygon style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return polygon draw style on scale level. Null if not found
	 */
	/*@Override
	public RenderableMapPolygonDrawSettings findPolygonDrawSettings(int scaleLevel)
	{
		if (canBePolygon)
		{
			return drawSettingsOnScales.findPolygonDrawSettings(scaleLevel);
		}
		else
		{
			return null;
		}
	}*/

	/**
	 * Find text style on scale level
	 *
	 * @param scaleLevel scale level
	 * @return text draw style on scale level. Null if not found
	 */
	/*@Override
	public TextDrawSettings findTextDrawSettings(int scaleLevel)
	{
		return drawSettingsOnScales.findTextDrawSettings(scaleLevel);
	}*/

	/**
	 * Find value of tag in tags that means text description of object
	 *
	 * @param tagsWhereFindText tags of object where to find text description
	 * @return text description of object founded in tags. Empty if not found
	 */
	/*verride
	public String findTextInTags(DefenitionTags tagsWhereFindText)
	{
		return textTagKeys.findTextInTags(tagsWhereFindText);
	}*/

	/**
	 * Get string representation
	 *
	 * @return string representation
	 */
	@Override
	public String toString()
	{
		return name;
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			canBePoint = input.readBoolean();
			canBeLine = input.readBoolean();
			canBePolygon = input.readBoolean();
			textTagKeys.readFromStream(input);
			pointDrawPriority = input.readInt();
			lineDrawPriority = input.readInt();
			polygonDrawPriority = input.readInt();
			description = input.readUTF();
			drawSettingsOnScales.readFromStream(input);
			defenitionTags.readFromStream(input);
			name = input.readUTF();
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
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			output.writeBoolean(canBePoint);
			output.writeBoolean(canBeLine);
			output.writeBoolean(canBePolygon);
			textTagKeys.writeToStream(output);
			output.writeInt(pointDrawPriority);
			output.writeInt(lineDrawPriority);
			output.writeInt(polygonDrawPriority);
			output.writeUTF(description);
			drawSettingsOnScales.writeToStream(output);
			defenitionTags.writeToStream(output);
			output.writeUTF(name);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
