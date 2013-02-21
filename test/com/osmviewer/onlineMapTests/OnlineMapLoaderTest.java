package com.osmviewer.onlineMapTests;

import com.osmviewer.drawingStyles.DrawingStylesFactory;
import com.osmviewer.map.onlineMap.OnlineMap;
import com.osmviewer.map.onlineMap.OnlineMapLoader;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
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
			testLoader.loadToMap(null, DrawingStylesFactory.createStandartDrawSettingsViewer(), new OnlineMap());
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
			testLoader.loadToMap(new MapBounds(-1, 1, -1, 1), DrawingStylesFactory.createStandartDrawSettingsViewer(), null);
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