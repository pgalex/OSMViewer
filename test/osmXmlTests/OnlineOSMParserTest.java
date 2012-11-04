package osmXmlTests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import osmXml.OnlineOsmParser;
import org.junit.*;
import osmXml.OnlineOsmParser;
import static org.junit.Assert.*;
import osmXml.OsmBounds;
import osmXml.OsmBounds;
import osmXml.OsmNode;
import osmXml.OsmNode;
import osmXml.OsmWay;
import osmXml.OsmWay;

/**
 *
 * @author preobrazhentsev
 */
public class OnlineOSMParserTest
{
	private static final String TEST_MAP_FILE_NAME = "test/supportFiles/testmap.osm";

	@Test
	public void parsingTest() throws FileNotFoundException
	{

		try
		{
			OnlineOsmParser converter = new OnlineOsmParser();
			converter.convert(new FileInputStream(new File(TEST_MAP_FILE_NAME)));

			//границы
			OsmBounds bounds = converter.getBounds();
			assertEquals(10.1, bounds.getLatitudeMinimum(), 0.001);
			assertEquals(20.1, bounds.getLongitudeMinimum(), 0.001);
			assertEquals(10.3, bounds.getLatitudeMaximum(), 0.001);
			assertEquals(20.3, bounds.getLongitudeMaximum(), 0.001);

			//точка
			ArrayList<OsmNode> nodes = converter.getNodes();
			assertEquals(123456789, nodes.get(0).getId());
			assertEquals(55.55, nodes.get(0).getLatitude(), 0.001);
			assertEquals(38.38, nodes.get(0).getLongitude(), 0.001);
			assertEquals("name", nodes.get(0).getTags().get(0).getKey());
			assertEquals("Осёнка", nodes.get(0).getTags().get(0).getValue());
			assertEquals("railway", nodes.get(0).getTags().get(1).getKey());
			assertEquals("halt", nodes.get(0).getTags().get(1).getValue());

			//ways
			ArrayList<OsmWay> ways = converter.getWays();
			OsmWay tempWay = ways.get(0);
			assertEquals(107289909, tempWay.getId());
			assertEquals(1233435465, (long) tempWay.getNodesIds().get(0));
			assertEquals(1233435417, (long) tempWay.getNodesIds().get(1));
			assertEquals(1233435413, (long) tempWay.getNodesIds().get(2));
			assertEquals("highway", tempWay.getTags().get(0).getKey());
			assertEquals("residential", tempWay.getTags().get(0).getValue());
			assertEquals("name", tempWay.getTags().get(1).getKey());
			assertEquals("Луговая улица", tempWay.getTags().get(1).getValue());
		}
		catch (Exception e)
		{
			fail();
		}
	}
}
