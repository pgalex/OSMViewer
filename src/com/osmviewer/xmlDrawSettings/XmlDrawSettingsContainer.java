package com.osmviewer.xmlDrawSettings;

import com.osmviewer.rendering.DrawSettingsFinder;
import com.osmviewer.rendering.RenderableMapDrawSettings;
import com.osmviewer.rendering.drawingIdBasedRenderers.PolygonDrawSettings;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Contains draw settings for each drawing id, by reading them from special xml
 * file
 *
 * @author preobrazhentsev
 */
public class XmlDrawSettingsContainer implements DrawSettingsFinder
{
	/**
	 * Container uses to find draw priority
	 */
	private final XmlDrawPriorityContainer drawPriorityContainer;

	/**
	 * Create empty
	 */
	public XmlDrawSettingsContainer()
	{
		drawPriorityContainer = new XmlDrawPriorityContainer();
	}

	/**
	 * Read draw settings from files
	 *
	 * @param drawPriorityFileName xml file storing draw priority. Must be not
	 * null
	 * @throws IllegalArgumentException drawPriorityFileName is null
	 * @throws IOException error while reading
	 */
	public void readFromFiles(String drawPriorityFileName) throws IllegalArgumentException, IOException
	{
		if (drawPriorityFileName == null)
		{
			throw new IllegalArgumentException("drawPriorityFileName is null");
		}

		drawPriorityContainer.readFromStream(new FileInputStream(drawPriorityFileName));
	}

	/**
	 * Find renderer type that must be used to render map object with given
	 * drawing id
	 *
	 * @param drawingId drawing id to find renderer by. Must be not null
	 * @return renderer typed. Null if not found
	 * @throws IllegalArgumentException drawingId is null
	 */
	@Override
	public String findRendererType(String drawingId) throws IllegalArgumentException
	{
		if (drawingId == null)
		{
			throw new IllegalArgumentException("drawingId is null");
		}

		return "polygon";
	}

	/**
	 * Find draw priority for map object with given drawing id
	 *
	 * @param drawingId drawing id to find draw priority by. Must be not null
	 * @return draw priority value. Null if not found
	 * @throws IllegalArgumentException drawingId is null
	 */
	@Override
	public Integer findDrawPriority(String drawingId) throws IllegalArgumentException
	{
		if (drawingId == null)
		{
			throw new IllegalArgumentException("drawingId is null");
		}

		return drawPriorityContainer.getDrawPriorityOf(drawingId);
	}

	/**
	 * Find polygon draw settings for map object with given drawing id
	 *
	 * @param drawingId drawing id to find draw settinga by. Must be not null
	 * @return polygon draw settings. Null if can not find suitable draw settings
	 * @throws IllegalArgumentException drawingId is null
	 */
	@Override
	public PolygonDrawSettings findPolygonDrawSettings(String drawingId) throws IllegalArgumentException
	{
		return new XmlPolygonDrawSettings();
	}

	/**
	 * Get common map draw settings
	 *
	 * @return map draw settings
	 */
	@Override
	public RenderableMapDrawSettings getMapDrawSettings()
	{
		return new StandartMapDrawSettings();
	}
}
