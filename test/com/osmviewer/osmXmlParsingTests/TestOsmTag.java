package com.osmviewer.osmXmlParsingTests;

import com.osmviewer.osmXml.OsmTag;

/**
 * Test implementation of OsmTag
 *
 * @author pgalex
 */
public class TestOsmTag implements OsmTag
{
	@Override
	public String getKey()
	{
		return "key";
	}

	@Override
	public String getValue()
	{
		return "value";
	}
}
