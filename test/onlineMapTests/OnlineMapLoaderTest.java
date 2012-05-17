package onlineMapTests;

import drawingStyle.DrawingStyleFactory;
import drawingStyle.StyleViewer;
import map.MapBounds;
import map.exceptions.MapBoundsIsNullRuntimeException;
import map.exceptions.MapIsNullRutimeException;
import map.exceptions.StyleViewerIsNullException;
import onlineMap.OnlineMap;
import onlineMap.OnlineMapLoader;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author pgalex
 */
public class OnlineMapLoaderTest
{
	public OnlineMapLoaderTest()
	{
	}

	/**
	 * Test map loading with incorrect sector bounds
	 */
	@Test
	public void loadToMapIncorrectBoundsTest()
	{
		OnlineMapLoader testLoader = new OnlineMapLoader();
		try
		{
			testLoader.loadToMap(null, DrawingStyleFactory.createStyleViewer(), new OnlineMap());
			fail();
		}
		catch (MapBoundsIsNullRuntimeException ex)
		{
			// ok
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
		catch (StyleViewerIsNullException ex)
		{
			// ok
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
			testLoader.loadToMap(new MapBounds(-1, 1, -1, 1), DrawingStyleFactory.createStyleViewer(), null);
			fail();
		}
		catch (MapIsNullRutimeException ex)
		{
			// ok
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
