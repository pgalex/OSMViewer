package com.osmviewer.xmlDrawSettings;

import com.osmviewer.rendering.DrawSettingsFinder;
import com.osmviewer.rendering.RenderableMapDrawSettings;
import com.osmviewer.rendering.drawingIdBasedRenderers.PolygonDrawSettings;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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
	 * Map of renderer type for each drawing id
	 */
	private final Map<String, String> rendererTypes;

	/**
	 * Create empty
	 */
	public XmlDrawSettingsContainer()
	{
		drawPriorityContainer = new XmlDrawPriorityContainer();
		rendererTypes = new HashMap<>();
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

		return rendererTypes.get(drawingId);
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
		if (drawingId == null)
		{
			throw new IllegalArgumentException("drawingId is null");
		}

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

	/**
	 * Read draw settings from files
	 *
	 * @param drawPriorityFileName xml file storing draw priority. Must be not
	 * null
	 * @param mapObjectsFileName name of xml file storing map objects draw
	 * settings and other common description. Must be not null
	 * @throws IllegalArgumentException drawPriorityFileName is null
	 * @throws IOException error while reading
	 */
	public void readFromFiles(String drawPriorityFileName, String mapObjectsFileName) throws IllegalArgumentException, IOException
	{
		if (drawPriorityFileName == null)
		{
			throw new IllegalArgumentException("drawPriorityFileName is null");
		}
		if (mapObjectsFileName == null)
		{
			throw new IllegalArgumentException("mapObjectsFileName is null");
		}

		drawPriorityContainer.readFromStream(new FileInputStream(drawPriorityFileName));
		readMapObjectsFromStream(new FileInputStream(mapObjectsFileName));
	}

	/**
	 * Map map objects (its draw settings and other descriprition) from stream
	 *
	 * @param input input stream to read map objects from. Must be not null
	 * @throws IllegalArgumentException input is null
	 * @throws IOException error while reading
	 */
	public void readMapObjectsFromStream(InputStream input) throws IllegalArgumentException, IOException
	{
		if (input == null)
		{
			throw new IllegalArgumentException("input is null");
		}

		try
		{
			tryParseMapObjectsFromStream(input);
		}
		catch (ParserConfigurationException | SAXException ex)
		{
			throw new IOException(ex);
		}
	}

	private void tryParseMapObjectsFromStream(InputStream input) throws IllegalArgumentException, ParserConfigurationException, SAXException, IOException
	{
		if (input == null)
		{
			throw new IllegalArgumentException("input is null");
		}

		rendererTypes.clear();

		DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
		Document mapObjectsXmlDocumnet = documentBuilder.parse(input);

		Element rootElement = mapObjectsXmlDocumnet.getDocumentElement();
		rootElement.normalize();
		NodeList mapObjectNodes = rootElement.getElementsByTagName("mapObject");
		for (int i = 0; i < mapObjectNodes.getLength(); i++)
		{
			Element mapObjectElement = (Element) mapObjectNodes.item(i);
			String drawingId = mapObjectElement.getAttribute("drawingId");
			String rendererType = mapObjectElement.getAttribute("rendererType");

			rendererTypes.put(drawingId, rendererType);
		}
	}
}
