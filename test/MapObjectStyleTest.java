
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import drawingStyle.MapObjectStyle;
import drawingStyle.ScaledObjectStyle;
import java.awt.Color;
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
	private final String TEST_FILE_NAME = "testFile.txt";
	
	public MapObjectStyleTest()
	{
	}
	
	/**
	 * Тест чтения/записи в файл
	 */
	@Test
	public void FileTest()
	{
		MapObjectStyle writingStyle = new MapObjectStyle();
		writingStyle.canBeLine = true;
		writingStyle.canBePoint = false;
		writingStyle.canBePolygon = true;
		writingStyle.description = "object1";
		writingStyle.defenitionTags.add(new MapTag("k1","v1"));
		writingStyle.defenitionTags.add(new MapTag("k2","v2"));
		writingStyle.drawPriority = 10;
		writingStyle.scaledStyles[0] = new ScaledObjectStyle();
		writingStyle.scaledStyles[0].drawLine = true;
		writingStyle.scaledStyles[0].drawPoint = false;
		writingStyle.scaledStyles[0].drawPolygon = true;
		writingStyle.scaledStyles[0].lineStyle.color = Color.CYAN;
		writingStyle.scaledStyles[5] = new ScaledObjectStyle();
		writingStyle.scaledStyles[5].drawLine = false;
		writingStyle.scaledStyles[5].drawPoint = true;
		writingStyle.scaledStyles[5].drawPolygon = true;
		writingStyle.scaledStyles[5].lineStyle.color = Color.CYAN;
		writingStyle.textTagKey = "name";
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingStyle.WriteToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		//чтение
		MapObjectStyle readingStyle = new MapObjectStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.ReadFromStream(input);
			input.close();
			assertEquals(writingStyle.canBeLine, readingStyle.canBeLine);
			assertEquals(writingStyle.canBePoint, readingStyle.canBePoint);
			assertEquals(writingStyle.canBePolygon, readingStyle.canBePolygon);
			assertEquals(true, writingStyle.CompareDefenitionTags(readingStyle.defenitionTags));
			assertEquals(writingStyle.description, readingStyle.description);
			assertEquals(writingStyle.scaledStyles[0].drawLine, readingStyle.scaledStyles[0].drawLine);
			assertEquals(writingStyle.scaledStyles[0].drawPoint, readingStyle.scaledStyles[0].drawPoint);
			assertEquals(writingStyle.scaledStyles[0].drawPolygon, readingStyle.scaledStyles[0].drawPolygon);
			assertEquals(writingStyle.scaledStyles[0].lineStyle.color, readingStyle.scaledStyles[0].lineStyle.color);
			assertEquals(writingStyle.scaledStyles[5].drawLine, readingStyle.scaledStyles[5].drawLine);
			assertEquals(writingStyle.scaledStyles[5].drawPoint, readingStyle.scaledStyles[5].drawPoint);
			assertEquals(writingStyle.scaledStyles[5].drawPolygon, readingStyle.scaledStyles[5].drawPolygon);
			assertEquals(writingStyle.scaledStyles[5].lineStyle.color, readingStyle.scaledStyles[5].lineStyle.color);
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Тест сравнения по тегам
	 */
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
