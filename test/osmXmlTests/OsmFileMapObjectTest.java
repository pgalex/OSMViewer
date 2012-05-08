package osmXmlTests;

import java.util.ArrayList;
import map.MapTag;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import osmXml.OsmFileMapObject;

/**
 *
 * @author pgalex
 */
public class OsmFileMapObjectTest
{
	public OsmFileMapObjectTest()
	{
	}

	/**
	 * Testing setTags with null argument
	 */
	@Test
	public void setNullTagsTest()
	{
		OsmFileMapObject testObject = new OsmFileMapObject();
		ArrayList<MapTag> someTags = new ArrayList<MapTag>();
		someTags.add(new MapTag());
		try
		{
			testObject.setTags(null);
			//ok
			assertNotNull(testObject.getTags());
			assertTrue(testObject.getTags().isEmpty());
		}
		catch (Exception ex)
		{
			fail();
		}
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
