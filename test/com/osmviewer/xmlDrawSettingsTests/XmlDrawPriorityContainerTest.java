package com.osmviewer.xmlDrawSettingsTests;

import com.osmviewer.xmlDrawSettings.XmlDrawPriorityContainer;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of XmlDrawPriorityContainer class
 *
 * @author preobrazhentsev
 */
public class XmlDrawPriorityContainerTest
{
	private void fillContainerWithTestData(XmlDrawPriorityContainer drawPriorityContainer) throws UnsupportedEncodingException, IOException
	{
		final String testXmlDrawPriorityDocumnet = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<drawPriority>\n"
						+ "	<mapObject drawingId=\"first\"/>\n"
						+ "	<mapObject drawingId=\"second\"/>\n"
						+ "	<mapObject drawingId=\"third\"/>\n"
						+ "</drawPriority>";
		try (ByteArrayInputStream testDocumentInputStream = new ByteArrayInputStream(testXmlDrawPriorityDocumnet.getBytes("UTF-8")))
		{
			drawPriorityContainer.readFromStream(testDocumentInputStream);
		}
	}

	@Test
	public void parsingTestDocument() throws UnsupportedEncodingException, IOException
	{
		XmlDrawPriorityContainer drawPriorityContainer = new XmlDrawPriorityContainer();
		fillContainerWithTestData(drawPriorityContainer);
		assertEquals(0, (int) drawPriorityContainer.getDrawPriorityOf("first"));
		assertEquals(1, (int) drawPriorityContainer.getDrawPriorityOf("second"));
		assertEquals(2, (int) drawPriorityContainer.getDrawPriorityOf("third"));
	}

	@Test
	public void gettingPriorityByNullDrawingId()
	{
		XmlDrawPriorityContainer drawPriorityContainer = new XmlDrawPriorityContainer();
		try
		{
			drawPriorityContainer.getDrawPriorityOf(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	@Test
	public void gettingPriorityByUnexistsDrawingId() throws IOException
	{
		XmlDrawPriorityContainer drawPriorityContainer = new XmlDrawPriorityContainer();
		fillContainerWithTestData(drawPriorityContainer);
		assertNull(drawPriorityContainer.getDrawPriorityOf("abc"));
	}

	@Test
	public void readingFromNullInput() throws IOException
	{
		XmlDrawPriorityContainer drawPriorityContainer = new XmlDrawPriorityContainer();
		try
		{
			drawPriorityContainer.readFromStream(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}
