package com.osmviewer.sqliteMapDataSourceTests;

import com.osmviewer.sqliteMapDataSource.TemporaryDatabaseOsmNode;
import com.osmviewer.sqliteMapDataSource.exceptions.DatabaseErrorExcetion;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * TemporaryDatabaseOsmNode class tests
 *
 * @author pgalex
 */
public class TemporaryDatabaseOsmNodeTest
{
	/**
	 * Test creating with null results set
	 */
	@Test
	public void creatingByNullResultsSet()
	{
		try
		{
			TemporaryDatabaseOsmNode node = new TemporaryDatabaseOsmNode(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
		catch (DatabaseErrorExcetion ex)
		{
			fail();
		}
	}
}