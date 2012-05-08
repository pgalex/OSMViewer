package osmXmlTests;

import java.util.ArrayList;
import map.MapPoint;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import osmXml.OSMFileNode;

/**
 *
 * @author pgalex
 */
public class OsmFileNodeTest
{
	public OsmFileNodeTest()
	{
	}

	/**
	 * Testing create map point by node with correct data
	 */
	@Test
	public void creatingPointNormalWorkTest()
	{
		OSMFileNode testNode = new OSMFileNode();
		testNode.setId(123456789);
		ArrayList<MapTag> pointTags = new ArrayList<MapTag>();
		pointTags.add(new MapTag("k1", "v1"));
		pointTags.add(new MapTag("k2", "v2"));
		testNode.setTags(pointTags);
		testNode.setLatitude(1.5);
		testNode.setLongitude(-15.5);
		
		MapPoint pointByNode = testNode.createMapPoint();
		assertNotNull(pointByNode);
		assertEquals(testNode.getId(), pointByNode.getId());
		assertEquals(testNode.getLatitude(), pointByNode.getPosition().getLatitude(), 0.0001);
		assertEquals(testNode.getLongitude(), pointByNode.getPosition().getLongitude(), 0.0001);
		assertNull(pointByNode.getStyleIndex());
		assertEquals(pointTags.size(), pointByNode.getDefenitionTags().size());
		for (int i = 0; i < pointByNode.getDefenitionTags().size(); i++)
			assertTrue(pointTags.get(i).compareTo(pointByNode.getDefenitionTags().get(i)));
	}
	
	/**
	 * Testing create map point by node with null tags
	 */
	@Test
	public void creatingPointNullTagsTest()
	{
		OSMFileNode testNode = new OSMFileNode();
		testNode.setTags(null);
		
		MapPoint pointByNode = testNode.createMapPoint();
		assertNotNull(pointByNode);
		assertNotNull(pointByNode.getDefenitionTags());
	}
	
	/**
	 * Testing create map point by node with empty tags
	 */
	@Test
	public void creatingPointEmptyTagsTest()
	{
		OSMFileNode testNode = new OSMFileNode();
		testNode.setTags(new ArrayList<MapTag>());
		
		MapPoint pointByNode = testNode.createMapPoint();
		assertNotNull(pointByNode);
		assertNotNull(pointByNode.getDefenitionTags());
	}

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}
}
