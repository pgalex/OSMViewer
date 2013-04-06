package com.osmviewer.mapTests;

import com.osmviewer.map.RenderableMapObjectsDrawSettingsFinder;
import com.osmviewer.mapDefenitionUtilities.DefenitionTags;
import com.osmviewer.rendering.RenderableMapObjectDrawSettings;

/**
 * Test implementation of RenderableMapObjectsDrawSettingsFinder class
 *
 * @author pgalex
 */
public class TestRenderableMapObjectsDrawSettingsFinder implements RenderableMapObjectsDrawSettingsFinder
{
	@Override
	public RenderableMapObjectDrawSettings findMapObjectDrawSettings(DefenitionTags mapObjectDefenitionTags)
	{
		return new TestRenderableMapObjectDrawSettings();
	}
}
