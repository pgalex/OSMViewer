package onlineMapTests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import onlineMap.OnlineOSMParser;
import static org.junit.Assert.*;
import org.junit.*;
import osmXml.OsmBounds;
import osmXml.OsmNode;
import osmXml.OsmRelation;
import osmXml.OsmWay;

/**
 *
 * @author preobrazhentsev
 */
public class OnlineOSMParserTest
{
	private static final String TEST_MAP_FILE_NAME = "testmap.osm";

	public OnlineOSMParserTest()
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
	public void parsingTest() throws FileNotFoundException
	{

		try
		{
			OnlineOSMParser converter = new OnlineOSMParser();
			converter.convert(new FileInputStream(new File(TEST_MAP_FILE_NAME)));

			//границы
			OsmBounds bounds = converter.getParserBounds();
			assertEquals(10.1, bounds.getLatitudeMinimum(), 0.001);
			assertEquals(20.1, bounds.getLongitudeMinimum(), 0.001);
			assertEquals(10.3, bounds.getLatitudeMaximum(), 0.001);
			assertEquals(20.3, bounds.getLongitudeMaximum(), 0.001);

			//точка
			ArrayList<OsmNode> nodes = converter.getParserNodes();
			assertEquals(123456789, nodes.get(0).getId());
			assertEquals(55.55, nodes.get(0).getLatitude(), 0.001);
			assertEquals(38.38, nodes.get(0).getLongitude(), 0.001);
			assertEquals("name", nodes.get(0).getTags().get(0).getKey());
			assertEquals("Осёнка", nodes.get(0).getTags().get(0).getValue());
			assertEquals("railway", nodes.get(0).getTags().get(1).getKey());
			assertEquals("halt", nodes.get(0).getTags().get(1).getValue());

			//ways
			ArrayList<OsmWay> ways = converter.getParserWays();
			OsmWay tempWay = ways.get(0);
			assertEquals(107289909, tempWay.getId());
			assertEquals(1233435465, (long) tempWay.getNodesIds().get(0));
			assertEquals(1233435417, (long) tempWay.getNodesIds().get(1));
			assertEquals(1233435413, (long) tempWay.getNodesIds().get(2));
			assertEquals("highway", tempWay.getTags().get(0).getKey());
			assertEquals("residential", tempWay.getTags().get(0).getValue());
			assertEquals("name", tempWay.getTags().get(1).getKey());
			assertEquals("Луговая улица", tempWay.getTags().get(1).getValue());

			//relation
			ArrayList<OsmRelation> relations = converter.getParserRelations();
			OsmRelation tempRelation = relations.get(0);
			assertEquals((long) 1693664, tempRelation.id);
			assertEquals("type", tempRelation.tags.get(0).getKey());
			assertEquals("boundary", tempRelation.tags.get(0).getValue());
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
