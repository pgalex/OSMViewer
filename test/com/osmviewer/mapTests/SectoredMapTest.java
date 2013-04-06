package com.osmviewer.mapTests;

import com.osmviewer.map.SectoredMap;
import com.osmviewer.mapDefenitionUtilities.MapBounds;
import com.osmviewer.rendering.RenderableMapObjectsDrawPriorityComparator;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests of SectoredMap class
 *
 * @author pgalex
 */
public class SectoredMapTest
{
	/**
	 * Test rendering with null rendering visitor
	 */
	@Test
	public void renderingWithNullVisitorTests()
	{
		try
		{
			SectoredMap map = new SectoredMap();
			map.renderObjectsByDrawPriority(null, new MapBounds(10, 20, 10, 20), new RenderableMapObjectsDrawPriorityComparator());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test rendering with null rendering area
	 */
	@Test
	public void renderingWithNullAreaTests()
	{
		try
		{
			SectoredMap map = new SectoredMap();
			map.renderObjectsByDrawPriority(new TestRenderableMapObjectsVisitor(), null, new RenderableMapObjectsDrawPriorityComparator());
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test rendering with null comparator
	 */
	@Test
	public void renderingWithNullComparatorTests()
	{
		try
		{
			SectoredMap map = new SectoredMap();
			map.renderObjectsByDrawPriority(new TestRenderableMapObjectsVisitor(), new MapBounds(10, 20, 10, 20), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}
}