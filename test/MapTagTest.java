/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import map.MapTag;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abc
 */
public class MapTagTest
{
	public MapTagTest()
	{
	}

	@Test
	public void compareToTest()
	{
		MapTag tag1 = new MapTag();
		MapTag tag2 = new MapTag();
		//пустые
		assertEquals(true, tag1.compareTo(tag2));

		//один пустой
		tag1.setKey("k1");
		tag1.setValue("v1");
		assertEquals(false, tag1.compareTo(tag2));

		//одинаковые
		tag2.setKey("k1");
		tag2.setValue("v1");
		assertEquals(true, tag1.compareTo(tag2));

		//разные
		tag2.setKey("k2");
		tag2.setValue("v2");
		assertEquals(false, tag1.compareTo(tag2));
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
