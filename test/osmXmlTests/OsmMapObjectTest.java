package osmXmlTests;

import java.util.ArrayList;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.BeforeClass;
import org.junit.Test;
import osmXml.OsmMapObject;

/**
 *
 * @author pgalex
 */
public class OsmMapObjectTest
{
	public OsmMapObjectTest()
	{
	}

	/**
	 * Testing setTags with null argument
	 */
	@Test
	public void setNullTagsTest()
	{
		OsmMapObject testObject = new OsmMapObject();
		ArrayList<MapTag> someTags = new ArrayList<MapTag>();
		someTags.add(new MapTag());
		try
		{
			testObject.setTags(null);
			//ok
			assertNull(testObject.getTags());
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
