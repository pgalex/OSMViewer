package com.osmviewer.xmlDrawSettingsTests;

import com.osmviewer.xmlDrawSettings.XmlDrawPriorityContainer;
import com.osmviewer.xmlDrawSettings.exceptions.ParsingXmlErrorException;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author preobrazhentsev
 */
public class XmlMapObjectsIdFinderTest
{
	@Test
	public void correctParsingXmlDrawPriority() throws ParsingXmlErrorException
	{
		final String xmlDrawPriority = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
						+ "<drawPriority>\n"
						+ "	<mapObject id=\"firstMapObject\"/>\n"
						+ "	<mapObject id=\"secondMapObject\"/>\n"
						+ "</drawPriority>";

		XmlDrawPriorityContainer xmlDrawPriorityContainer = new XmlDrawPriorityContainer();
		xmlDrawPriorityContainer.readFromStream(new ByteArrayInputStream(xmlDrawPriority.getBytes(StandardCharsets.UTF_8)));
		
		assertEquals(new Integer(0), xmlDrawPriorityContainer.getDrawPriorityOf("firstMapObject"));
		assertEquals(new Integer(1), xmlDrawPriorityContainer.getDrawPriorityOf("secondMapObject"));
	}
	
	// finding draw priority of unexists object
}
