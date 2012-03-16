package drawingStyleTests;


import drawingStyle.MapObjectStyle;
import drawingStyle.ScaledObjectStyle;
import drawingStyle.ScaledObjectStyleArray;
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
	 * Constructor should auto-initialize null values
	 */
	@Test
	public void constructorTest()
	{
		MapObjectStyle style = new MapObjectStyle(true, true, true, "", 0, "", null);
		assertNotNull(style.getScaledStyles());
	}

	/**
	 * Writing/reading test
	 */
	@Test
	public void fileTest()
	{
		ScaledObjectStyleArray scaledStyles = new ScaledObjectStyleArray();
		scaledStyles.setStyleOnScale(0, new ScaledObjectStyle(true, false, true, null, null,
						null, null, null));
		scaledStyles.setStyleOnScale(5, new ScaledObjectStyle(false, true, true, null, null,
						null, null, null));

		MapObjectStyle writingStyle = new MapObjectStyle(true, false,
						true, "name", 10, "object1", scaledStyles);
		
		writingStyle.defenitionTags.add(new MapTag("k1", "v1"));
		writingStyle.defenitionTags.add(new MapTag("k2", "v2"));

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
			assertEquals(writingStyle.getScaledStyles().getStyleOnScale(0).isDrawLine(), readingStyle.getScaledStyles().getStyleOnScale(0).isDrawLine());
			assertEquals(writingStyle.getScaledStyles().getStyleOnScale(0).isDrawPoint(), readingStyle.getScaledStyles().getStyleOnScale(0).isDrawPoint());
			assertEquals(writingStyle.getScaledStyles().getStyleOnScale(0).isDrawPolygon(), readingStyle.getScaledStyles().getStyleOnScale(0).isDrawPolygon());
			assertEquals(writingStyle.getScaledStyles().getStyleOnScale(5).isDrawLine(), readingStyle.getScaledStyles().getStyleOnScale(5).isDrawLine());
			assertEquals(writingStyle.getScaledStyles().getStyleOnScale(5).isDrawPoint(), readingStyle.getScaledStyles().getStyleOnScale(5).isDrawPoint());
			assertEquals(writingStyle.getScaledStyles().getStyleOnScale(5).isDrawPolygon(), readingStyle.getScaledStyles().getStyleOnScale(5).isDrawPolygon());
			assertEquals(writingStyle.getScaledStyles().count(), readingStyle.getScaledStyles().count());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Comparing defenition tags test
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
