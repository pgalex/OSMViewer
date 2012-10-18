package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to draw object on specified scale level.
 *
 * @author abc
 */
public class DrawSettingsOnScale implements ReadableMapData, WritableMapData
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
		needDrawPoint = true;
		needDrawLine = true;
		needDrawPolygon = true;
	}

	/**
	 * Create with parameters
	 *
	 * @param drawPoint Is need to draw point
	 * @param drawLine Is need to draw line
	 * @param drawPolygon Is need to draw polygon
	 * @param pointSettings point drawing settings. Reseting to default if null
	 * @param lineSettings line drawing settings. Reseting to default if null
	 * @param polygonSettings polygon drawing settings. Reseting to default if
	 * null
	 * @param textSettings text drawing settings. Reseting to default if null
	 */
	public DrawSettingsOnScale(boolean drawPoint, boolean drawLine, boolean drawPolygon,
					PointDrawSettings pointSettings, LineDrawSettings lineSettings, PolygonDrawSettings polygonSettings,
					TextDrawSettings textSettings)
	{
		needDrawPoint = drawPoint;
		needDrawLine = drawLine;
		needDrawPolygon = drawPolygon;
		pointDrawSettings = pointSettings;
		lineDrawSettings = lineSettings;
		polygonDrawSettings = polygonSettings;
		textDrawSettings = textSettings;

		initializeNullFields();
	}

	/**
	 * Auto-initialize null fields
	 */
	private void initializeNullFields()
	{
		if (pointDrawSettings == null)
		{
			pointDrawSettings = new PointDrawSettings();
		}

		if (lineDrawSettings == null)
		{
			lineDrawSettings = new LineDrawSettings();
		}

		if (polygonDrawSettings == null)
		{
			polygonDrawSettings = new PolygonDrawSettings();
		}
		if (textDrawSettings == null)
		{
			textDrawSettings = new TextDrawSettings();
		}
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
			needDrawPoint = input.readBoolean();
			needDrawLine = input.readBoolean();
			needDrawPolygon = input.readBoolean();
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
	@Override
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			output.writeBoolean(isDrawPoint());
			output.writeBoolean(isDrawLine());
			output.writeBoolean(isDrawPolygon());
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
}
