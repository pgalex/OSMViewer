/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mapTests;

import map.EditableDefenitionTags;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class EditableDefenitionTagsTest
{
	public EditableDefenitionTagsTest()
	{
	}

	/**
	 * Testing add method
	 */
	@Test
	public void addingTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		tags.add(new MapTag("k2", "v2"));
		
		assertFalse(tags.isEmpty());
		assertEquals(2, tags.size());
		assertTrue(tags.get(0).compareTo(new MapTag("k1", "v1")));
		assertTrue(tags.get(1).compareTo(new MapTag("k2", "v2")));
	}
	
	/**
	 * Testing clear method
	 */
	@Test
	public void clearTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		tags.add(new MapTag("k2", "v2"));
		
		assertFalse(tags.isEmpty());
		assertEquals(2, tags.size());
		tags.clear();
		assertTrue(tags.isEmpty());
		assertEquals(0, tags.size());
	}
	
	/**
	 * Testing remove method
	 */
	@Test
	public void removeTest()
	{
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		tags.add(new MapTag("k2", "v2"));
		
		assertFalse(tags.isEmpty());
		assertEquals(2, tags.size());
		
		tags.remove(1);
		assertEquals(1, tags.size());
		assertTrue(tags.get(0).compareTo(new MapTag("k1", "v1")));
		
		tags.remove(0);
		assertTrue(tags.isEmpty());
		assertEquals(0, tags.size());
		
		try
		{
			tags.remove(-1);
			fail();
		}
		catch(Exception ex)
		{
			//ok
		}
		try
		{
			tags.remove(1);
			fail();
		}
		catch(Exception ex)
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