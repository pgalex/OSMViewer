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
	private boolean drawPoint;
	/**
	 * Is need to draw line
	 */
	private boolean drawLine;
	/**
	 * Is need to draw polygon
	 */
	private boolean drawPolygon;
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
	 * Default constructor
	 */
	public DrawSettingsOnScale()
	{
		pointDrawSettings = new PointDrawSettings();
		lineDrawSettings = new LineDrawSettings();
		polygonDrawSettings = new PolygonDrawSettings();
		textDrawSettings = new TextDrawSettings();
		drawPoint = false;
		drawLine = false;
		drawPolygon = false;
	}

	/**
	 * Constructor
	 *
	 * @param pDrawPoint Is need to draw point
	 * @param pDrawLine Is need to draw line
	 * @param pDrawPolygon Is need to draw polygon
	 * @param pPointStyle point drawing settings. Setting to default if null
	 * @param pLineStyle line drawing settings. Setting to default if null
	 * @param pPolygonStyle polygon drawing settings. Setting to default if null
	 * @param pTextDrawSettings text drawing settings. Setting to default if null
	 */
	public DrawSettingsOnScale(boolean pDrawPoint, boolean pDrawLine, boolean pDrawPolygon,
					PointDrawSettings pPointStyle, LineDrawSettings pLineStyle, PolygonDrawSettings pPolygonStyle,
					TextDrawSettings pTextDrawSettings)
	{
		drawPoint = pDrawPoint;
		drawLine = pDrawLine;
		drawPolygon = pDrawPolygon;
		pointDrawSettings = pPointStyle;
		lineDrawSettings = pLineStyle;
		polygonDrawSettings = pPolygonStyle;
		textDrawSettings = pTextDrawSettings;

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
	 * @param pInput reading stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			drawPoint = pInput.readBoolean();
			drawLine = pInput.readBoolean();
			drawPolygon = pInput.readBoolean();
			pointDrawSettings.readFromStream(pInput);
			lineDrawSettings.readFromStream(pInput);
			polygonDrawSettings.readFromStream(pInput);
			textDrawSettings.readFromStream(pInput);
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
			pOutput.writeBoolean(isDrawPoint());
			pOutput.writeBoolean(isDrawLine());
			pOutput.writeBoolean(isDrawPolygon());
			pointDrawSettings.writeToStream(pOutput);
			lineDrawSettings.writeToStream(pOutput);
			polygonDrawSettings.writeToStream(pOutput);
			textDrawSettings.writeToStream(pOutput);
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
		return drawPoint;
	}

	/**
	 * Is need to draw line
	 *
	 * @return Is need to draw line
	 */
	public boolean isDrawLine()
	{
		return drawLine;
	}

	/**
	 * Is need to draw polygon
	 *
	 * @return Is need to draw polygon
	 */
	public boolean isDrawPolygon()
	{
		return drawPolygon;
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
