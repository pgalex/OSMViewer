/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package onlineMapTests;

import drawingStyle.DrawingStyleFactory;
import drawingStyle.MapObjectStyle;
import drawingStyle.StyleEditor;
import java.util.ArrayList;
import java.util.Collections;
import map.EditableDefenitionTags;
import map.MapObject;
import map.MapObjectDrawPriorityComparator;
import map.MapTag;
import map.exceptions.MapObjectIsNullException;
import onlineMap.OnlineMap;
import org.junit.AfterClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author pgalex
 */
public class OnlineMapTest
{
	public OnlineMapTest()
	{
	}

	/**
	 * Test adding null object
	 */
	@Test
	public void addingIncorrectParameterTest()
	{
		OnlineMap testMap = new OnlineMap();
		try
		{
			testMap.addObject(null);
			fail();
		}
		catch (MapObjectIsNullException ex)
		{
			//ok
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
