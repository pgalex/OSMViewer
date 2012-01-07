
import java.util.ArrayList;
import drawingStyle.MapObjectStyle;
import map.MapTag;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author abc
 */
public class MapObjectStyleTest
{
	public MapObjectStyleTest()
	{
	}

	@Test
	public void CompareDefenitionTagsTest()
	{
		MapObjectStyle style = new MapObjectStyle();
		ArrayList<MapTag> compareTags = new ArrayList<MapTag>();
		//пустые списки
		assertEquals(true, style.CompareDefenitionTags(compareTags));

		//один пустой
		style.defenitionTags.add(new MapTag("key1", "value1"));
		assertEquals(false, style.CompareDefenitionTags(compareTags));

		//друго пустой
		style.defenitionTags.clear();
		compareTags.add(new MapTag("key1", "value1"));
		assertEquals(false, style.CompareDefenitionTags(compareTags));

		//один тег
		compareTags.clear();
		style.defenitionTags.clear();
		compareTags.add(new MapTag("key1", "value1"));
		style.defenitionTags.add(new MapTag("key1", "value1"));
		assertEquals(true, style.CompareDefenitionTags(compareTags));

		compareTags.clear();
		style.defenitionTags.clear();
		compareTags.add(new MapTag("key1", "value1"));
		style.defenitionTags.add(new MapTag("key2", "value2"));
		assertEquals(false, style.CompareDefenitionTags(compareTags));

		//несколько тегов
		compareTags.clear();
		style.defenitionTags.clear();
		compareTags.add(new MapTag("key1", "value1"));
		compareTags.add(new MapTag("key5", "value1"));
		compareTags.add(new MapTag("key6", "value6"));
		style.defenitionTags.add(new MapTag("key2", "value2"));
		style.defenitionTags.add(new MapTag("key1", "value1"));
		style.defenitionTags.add(new MapTag("key3", "value3"));
		assertEquals(false, style.CompareDefenitionTags(compareTags));

		compareTags.clear();
		style.defenitionTags.clear();
		compareTags.add(new MapTag("key1", "value1"));
		compareTags.add(new MapTag("key3", "value3"));
		compareTags.add(new MapTag("key2", "value2"));
		style.defenitionTags.add(new MapTag("key2", "value2"));
		style.defenitionTags.add(new MapTag("key1", "value1"));
		style.defenitionTags.add(new MapTag("key3", "value3"));
		assertEquals(true, style.CompareDefenitionTags(compareTags));
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
