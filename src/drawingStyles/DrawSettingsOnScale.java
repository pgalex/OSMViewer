package drawingStyles;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to draw object on specified scale level.
 *
 * @author abc
 */
public class DrawSettingsOnScale
{
	/**
	 * Is need to draw point
	 */
	private boolean needDrawPoint;
	/**
	 * Is need to draw line
	 */
	private boolean needDrawLine;
	/**
	 * Is need to draw polygon
	 */
	private boolean needDrawPolygon;
	/**
	 * Is need to draw text
	 */
	private boolean needDrawText;
	/**
	 * point drawing settings
	 */
	private PointDrawSettings pointDrawSettings;
	/**
	 * line drawing settings
	 */
	private LineDrawSettings lineDrawSettings;
	/**
	 * polygon drawing settings
	 */
	private PolygonDrawSettings polygonDrawSettings;
	/**
	 * text drawing settings
	 */
	private TextDrawSettings textDrawSettings;

	/**
	 * Create with default values
	 */
	public DrawSettingsOnScale()
	{
		pointDrawSettings = new PointDrawSettings();
		lineDrawSettings = new LineDrawSettings();
		polygonDrawSettings = new PolygonDrawSettings();
		textDrawSettings = new TextDrawSettings();
		needDrawPoint = false;
		needDrawLine = false;
		needDrawPolygon = false;
		needDrawText = false;
	}

	/**
	 * Is need to draw point.
	 *
	 * @return Is need to draw point.
	 */
	public boolean isDrawPoint()
	{
		return needDrawPoint;
	}

	/**
	 * Set need to draw point
	 */
	public void setDrawPoint()
	{
		needDrawPoint = true;
	}

	/**
	 * Set dont need to draw point
	 */
	public void setNotDrawPoint()
	{
		needDrawPoint = false;
	}

	/**
	 * Is need to draw line
	 *
	 * @return Is need to draw line
	 */
	public boolean isDrawLine()
	{
		return needDrawLine;
	}

	/**
	 * Set need to draw line
	 */
	public void setDrawLine()
	{
		needDrawLine = true;
	}

	/**
	 * Set dont need to draw line
	 */
	public void setNotDrawLine()
	{
		needDrawLine = false;
	}

	/**
	 * Is need to draw polygon
	 *
	 * @return Is need to draw polygon
	 */
	public boolean isDrawPolygon()
	{
		return needDrawPolygon;
	}

	/**
	 * Set need to draw polygon
	 */
	public void setDrawPolygon()
	{
		needDrawPolygon = true;
	}

	/**
	 * Set dont need to draw polygon
	 */
	public void setNotDrawPolygon()
	{
		needDrawPolygon = false;
	}

	/**
	 * Is need to draw text
	 *
	 * @return is need to draw text
	 */
	public boolean isDrawText()
	{
		return needDrawText;
	}

	/**
	 * Set need to draw text
	 */
	public void setDrawText()
	{
		needDrawText = true;
	}

	/**
	 * Set dont need to draw text
	 */
	public void setNotDrawText()
	{
		needDrawText = false;
	}

	/**
	 * Get point drawing settings
	 *
	 * @return point drawing style
	 */
	public PointDrawSettings getPointDrawSettings()
	{
		return pointDrawSettings;
	}

	/**
	 * Get line drawing settings
	 *
	 * @return line drawing settings
	 */
	public LineDrawSettings getLineDrawSettings()
	{
		return lineDrawSettings;
	}

	/**
	 * Get polygon drawing settings
	 *
	 * @return polygon drawing settings
	 */
	public PolygonDrawSettings getPolygonDrawSettings()
	{
		return polygonDrawSettings;
	}

	/**
	 * Get text drawing settings
	 *
	 * @return text drawing settings
	 */
	public TextDrawSettings getTextDrawSettings()
	{
		return textDrawSettings;
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
			needDrawPoint = input.readBoolean();
			needDrawLine = input.readBoolean();
			needDrawPolygon = input.readBoolean();
			needDrawText = input.readBoolean();
			pointDrawSettings.readFromStream(input);
			lineDrawSettings.readFromStream(input);
			polygonDrawSettings.readFromStream(input);
			textDrawSettings.readFromStream(input);
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
			output.writeBoolean(needDrawPoint);
			output.writeBoolean(needDrawLine);
			output.writeBoolean(needDrawPolygon);
			output.writeBoolean(needDrawText);
			pointDrawSettings.writeToStream(output);
			lineDrawSettings.writeToStream(output);
			polygonDrawSettings.writeToStream(output);
			textDrawSettings.writeToStream(output);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}
}
