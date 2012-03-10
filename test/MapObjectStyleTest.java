
import drawingStyle.MapObjectStyle;
import drawingStyle.ScaledObjectStyle;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

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
	public void fileTest()
	{
		MapObjectStyle writingStyle = new MapObjectStyle(true, false,
						true, "name", 10, "object1");
		writingStyle.defenitionTags.add(new MapTag("k1", "v1"));
		writingStyle.defenitionTags.add(new MapTag("k2", "v2"));

		ScaledObjectStyle scaledStyle0 = new ScaledObjectStyle(true, false, true, null, null,
						null, null, null);
		writingStyle.scaledStyles.setStyleOnScale(0, scaledStyle0);
		ScaledObjectStyle scaledStyle5 = new ScaledObjectStyle(false, true, true, null, null,
						null, null, null);
		writingStyle.scaledStyles.setStyleOnScale(5, scaledStyle5);

		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingStyle.writeToStream(output);
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
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(writingStyle.isCanBeLine(), readingStyle.isCanBeLine());
			assertEquals(writingStyle.isCanBePoint(), readingStyle.isCanBePoint());
			assertEquals(writingStyle.isCanBePolygon(), readingStyle.isCanBePolygon());
			assertEquals(true, writingStyle.compareDefenitionTags(readingStyle.defenitionTags));
			assertEquals(writingStyle.getDescription(), readingStyle.getDescription());
			assertEquals(writingStyle.getTextTagKey(), readingStyle.getTextTagKey());
			assertEquals(writingStyle.getDrawPriority(), readingStyle.getDrawPriority());
			assertEquals(writingStyle.getDescription(), readingStyle.getDescription());
			assertEquals(writingStyle.scaledStyles.getStyleOnScale(0).isDrawLine(), readingStyle.scaledStyles.getStyleOnScale(0).isDrawLine());
			assertEquals(writingStyle.scaledStyles.getStyleOnScale(0).isDrawPoint(), readingStyle.scaledStyles.getStyleOnScale(0).isDrawPoint());
			assertEquals(writingStyle.scaledStyles.getStyleOnScale(0).isDrawPolygon(), readingStyle.scaledStyles.getStyleOnScale(0).isDrawPolygon());
			assertEquals(writingStyle.scaledStyles.getStyleOnScale(5).isDrawLine(), readingStyle.scaledStyles.getStyleOnScale(5).isDrawLine());
			assertEquals(writingStyle.scaledStyles.getStyleOnScale(5).isDrawPoint(), readingStyle.scaledStyles.getStyleOnScale(5).isDrawPoint());
			assertEquals(writingStyle.scaledStyles.getStyleOnScale(5).isDrawPolygon(), readingStyle.scaledStyles.getStyleOnScale(5).isDrawPolygon());
			assertEquals(writingStyle.scaledStyles.count(), readingStyle.scaledStyles.count());
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
	public void compareDefenitionTagsTest()
	{
		MapObjectStyle objectStyle = new MapObjectStyle();
		ArrayList<MapTag> compareTags = new ArrayList<MapTag>();

		//пустые списки
		objectStyle.defenitionTags.clear();
		compareTags.clear();
		assertEquals(true, objectStyle.compareDefenitionTags(compareTags));

		// в стиле пустой
		objectStyle.defenitionTags.clear();
		compareTags.clear();
		compareTags.add(new MapTag("k1", "v1"));
		assertEquals(false, objectStyle.compareDefenitionTags(compareTags));

		// в тегах пустой
		objectStyle.defenitionTags.clear();
		compareTags.clear();
		objectStyle.defenitionTags.add(new MapTag("k1", "v1"));
		assertEquals(false, objectStyle.compareDefenitionTags(compareTags));

		// разный порядок
		objectStyle.defenitionTags.clear();
		compareTags.clear();
		objectStyle.defenitionTags.add(new MapTag("k2", "v2"));
		objectStyle.defenitionTags.add(new MapTag("k1", "v1"));
		objectStyle.defenitionTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k2", "v2"));
		assertEquals(true, objectStyle.compareDefenitionTags(compareTags));

		// несовпадение
		objectStyle.defenitionTags.add(new MapTag("k4", "v4"));
		objectStyle.defenitionTags.add(new MapTag("k1", "v1"));
		objectStyle.defenitionTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k2", "v2"));
		assertEquals(false, objectStyle.compareDefenitionTags(compareTags));

		// неравное кол-во, несовпадают
		objectStyle.defenitionTags.add(new MapTag("k4", "v4"));
		objectStyle.defenitionTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k2", "v2"));
		assertEquals(false, objectStyle.compareDefenitionTags(compareTags));

		// неравное кол-во, совпадают
		objectStyle.defenitionTags.add(new MapTag("k3", "v3"));
		objectStyle.defenitionTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k1", "v1"));
		compareTags.add(new MapTag("k3", "v3"));
		compareTags.add(new MapTag("k2", "v2"));
		assertEquals(false, objectStyle.compareDefenitionTags(compareTags));
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
