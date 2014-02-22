package com.osmviewer.xmlDrawSettingsTests;

import com.osmviewer.xmlDrawSettings.XmlDrawSettingsContainer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of XmlDrawSettingsContainer
 *
 * @author preobrazhentsev
 */
public class XmlDrawSettingsContainerTest
{
	private void readTestXmlDataToContainer(XmlDrawSettingsContainer container) throws UnsupportedEncodingException, IOException
	{
		final String testXmlMapObjectsDocument = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<mapObjects>"
						+ "<mapObject drawingId=\"natural-wood-closed-way\" descriptionId=\"wood\" rendererType=\"polygon\">\n"
						+ "</mapObject>\n"
						+ "<mapObject drawingId=\"natural-wood-node\" descriptionId=\"wood\" rendererType=\"icon\">\n"
						+ "</mapObject>"
						+ "</mapObjects>";
		try (ByteArrayInputStream testDocumentInputStream = new ByteArrayInputStream(testXmlMapObjectsDocument.getBytes("UTF-8")))
		{
			container.readMapObjectsFromStream(testDocumentInputStream);
		}
	}

	// reading draw priorty normal work
	//...
	@Test
	public void readingMapObjectDataNormalWork() throws IOException
	{
		XmlDrawSettingsContainer drawSettingsContainer = new XmlDrawSettingsContainer();
		readTestXmlDataToContainer(drawSettingsContainer);
		assertEquals("polygon", drawSettingsContainer.findRendererType("natural-wood-closed-way"));
		assertEquals("icon", drawSettingsContainer.findRendererType("natural-wood-node"));
	}

	@Test
	public void readingMapObjectsFromNullInputStream() throws IOException
	{
		XmlDrawSettingsContainer drawSettingsContainer = new XmlDrawSettingsContainer();
		try
		{
			drawSettingsContainer.readMapObjectsFromStream(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void readingWithNullDrawPriorityFileName() throws IOException
	{
		XmlDrawSettingsContainer drawSettingsContainer = new XmlDrawSettingsContainer();
		try
		{
			drawSettingsContainer.readFromFiles(null, "123");
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void readingWithNullMapObjectsFileName() throws IOException
	{
		XmlDrawSettingsContainer drawSettingsContainer = new XmlDrawSettingsContainer();
		try
		{
			drawSettingsContainer.readFromFiles("123", null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void findRendererWithNullDrawingId()
	{
		XmlDrawSettingsContainer drawSettingsContainer = new XmlDrawSettingsContainer();
		try
		{
			drawSettingsContainer.findRendererType(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void findDrawPriorityWithNullDrawingId()
	{
		XmlDrawSettingsContainer drawSettingsContainer = new XmlDrawSettingsContainer();
		try
		{
			drawSettingsContainer.findDrawPriority(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void findPolygonDrawSettingsWithNullDrawingId()
	{
		XmlDrawSettingsContainer drawSettingsContainer = new XmlDrawSettingsContainer();
		try
		{
			drawSettingsContainer.findPolygonDrawSettings(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
