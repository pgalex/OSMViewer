/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.ArrayList;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.InputSource;
import osmXml.OSMFileBounds;
import osmXml.OSMFileNode;
import osmXml.OSMFileRelation;
import osmXml.OSMFileWay;
import osmXml.OSMFlyConverter;
import static org.junit.Assert.*;

/**
 *
 * @author preobrazhentsev
 */
public class OSMFlyConverterTest
{
	private static final String TEST_MAP_FILE_NAME = "testmap.osm";

	public OSMFlyConverterTest()
	{
	}

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}

	@Before
	public void setUp()
	{
	}

	@After
	public void tearDown()
	{
	}

	@Test
	public void testParsing() throws FileNotFoundException
	{

		try
		{
			OSMFlyConverter converter = new OSMFlyConverter();
			InputSource source = new InputSource(new FileInputStream(new File(TEST_MAP_FILE_NAME)));
			converter.Convert(source);

			//границы
			OSMFileBounds bounds = converter.getParserBounds();
			assertEquals(10.1, bounds.minLatitude, 0.001);
			assertEquals(20.1, bounds.minLongitude, 0.001);
			assertEquals(10.3, bounds.maxLatitude, 0.001);
			assertEquals(20.3, bounds.maxLongitude, 0.001);

			//точка
			ArrayList<OSMFileNode> nodes = converter.getParserNodes();
			assertEquals(123456789, nodes.get(0).id);
			assertEquals(55.55, nodes.get(0).latitude, 0.001);
			assertEquals(38.38, nodes.get(0).longitude, 0.001);
			assertEquals("name", nodes.get(0).tags.get(0).key);
			assertEquals("Осёнка", nodes.get(0).tags.get(0).value);
			assertEquals("railway", nodes.get(0).tags.get(1).key);
			assertEquals("halt", nodes.get(0).tags.get(1).value);

			//ways
			ArrayList<OSMFileWay> ways = converter.getParserWays();
			OSMFileWay tempWay = ways.get(0);
			assertEquals((long) 107289909, tempWay.id);
			assertEquals((long) 1233435465, tempWay.nodesIds.get(0));
			assertEquals((long) 1233435417, tempWay.nodesIds.get(1));
			assertEquals((long) 1233435413, tempWay.nodesIds.get(2));
			assertEquals("highway", tempWay.tags.get(0).key);
			assertEquals("residential", tempWay.tags.get(0).value);
			assertEquals("name", tempWay.tags.get(1).key);
			assertEquals("Луговая улица", tempWay.tags.get(1).value);

			//relation
			ArrayList<OSMFileRelation> relations = converter.getParserRelations();
			OSMFileRelation tempRelation = relations.get(0);
			assertEquals((long) 1693664, tempRelation.id);
			assertEquals("type", tempRelation.tags.get(0).key);
			assertEquals("boundary", tempRelation.tags.get(0).value);
			assertEquals("node", tempRelation.members.get(0).type);
			assertEquals((long) 123456789, tempRelation.members.get(0).ref);
			assertEquals("admin_centre", tempRelation.members.get(0).role);
		}
		catch (Exception e)
		{
			fail();
		}
	}
}
