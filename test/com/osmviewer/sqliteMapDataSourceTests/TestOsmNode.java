package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.osmXml.OsmNode;
import com.osmviewer.osmXml.OsmTag;

/**
 * Implementation of osmNode using for temporary nodes database test
 *
 * @author pgalex
 */
public class TestOsmNode implements OsmNode
{
	public long id;
	public double latitude;
	public double longitude;

	@Override
	public long getId()
	{
		return id;
	}

	@Override
	public double getLatitude()
	{
		return latitude;
	}

	@Override
	public double getLongitude()
	{
		return longitude;
	}

	@Override
	public int getTagsCount()
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

	@Override
	public OsmTag getTag(int index) throws IllegalArgumentException
	{
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}
}
