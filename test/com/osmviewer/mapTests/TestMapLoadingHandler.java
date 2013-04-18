package com.osmviewer.mapTests;

import com.osmviewer.map.MapLoadingHandler;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Test implementation of MapLoadingHandler
 *
 * @author pgalex
 */
public class TestMapLoadingHandler implements MapLoadingHandler
{
	public volatile AtomicInteger mapLoadedCallsCount;

	public TestMapLoadingHandler()
	{
		mapLoadedCallsCount = new AtomicInteger(0);
	}

	@Override
	public void mapLoaded()
	{
		mapLoadedCallsCount.incrementAndGet();
	}
}
