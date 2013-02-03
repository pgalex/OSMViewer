package onlineMapTests;

import drawingStyles.DrawingStylesFactory;
import MapDefenitionUtilities.MapBounds;
import map.onlineMap.OnlineMap;
import map.onlineMap.OnlineMapLoader;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * OnlineMapLoader tests
 *
 * @author pgalex
 */
public class OnlineMapLoaderTest
{
	/**
	 * Test map loading with incorrect sector bounds
	 */
	@Test
	public void loadToMapIncorrectBoundsTest()
	{
		OnlineMapLoader testLoader = new OnlineMapLoader();
		try
		{
			testLoader.loadToMap(null, DrawingStylesFactory.createStyleViewer(), new OnlineMap());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Test map loading with incorrect style viewer
	 */
	@Test
	public void loadToMapIncorrectViewerTest()
	{
		OnlineMapLoader testLoader = new OnlineMapLoader();
		try
		{
			testLoader.loadToMap(new MapBounds(-1, 1, -1, 1), null, new OnlineMap());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Test map loading with incorrect map
	 */
	@Test
	public void loadToMapIncorrectMapTest()
	{
		OnlineMapLoader testLoader = new OnlineMapLoader();
		try
		{
			testLoader.loadToMap(new MapBounds(-1, 1, -1, 1), DrawingStylesFactory.createStyleViewer(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Test map loading with incorrect map
	 */
	@Test
	public void protectedMethodsTest()
	{
		TestOnlineMapLoader testLoader = new TestOnlineMapLoader();
		testLoader.testProtectedMethod();
	}
}
