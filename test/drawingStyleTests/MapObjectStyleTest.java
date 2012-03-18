package drawingStyleTests;

import drawingStyle.MapObjectStyle;
import drawingStyle.ScaledObjectStyle;
import drawingStyle.ScaledObjectStyleArray;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import map.EditableDefenitionTags;
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
	 * Test sorting, cuz its comparable. Sorting by tags count. More tags - less
	 * index
	 */
	@Test
	public void sortingTest()
	{
		EditableDefenitionTags tags1 = new EditableDefenitionTags();
		tags1.add(new MapTag("k1", "v1"));
		tags1.add(new MapTag("k2", "v2"));
		tags1.add(new MapTag("k3", "v3"));

		EditableDefenitionTags tags2 = new EditableDefenitionTags();
		tags2.add(new MapTag("k4", "v4"));
		tags2.add(new MapTag("k5", "v5"));
		tags2.add(new MapTag("k6", "v6"));
		tags2.add(new MapTag("k7", "v7"));
		
		EditableDefenitionTags tags3 = new EditableDefenitionTags();
		tags3.add(new MapTag("k8", "v8"));

		ArrayList<MapObjectStyle> styles = new ArrayList<MapObjectStyle>();
		styles.add(new MapObjectStyle(true, true, true, "", 0, "", null, tags1));
		styles.add(new MapObjectStyle(true, true, true, "", 0, "", null, tags2));
		styles.add(new MapObjectStyle(true, true, true, "", 0, "", null, tags3));
		Collections.sort(styles);

		assertTrue(styles.get(0).getDefenitionTags().compareTo(tags2));
		assertTrue(styles.get(1).getDefenitionTags().compareTo(tags1));
		assertTrue(styles.get(2).getDefenitionTags().compareTo(tags3));
	}

	/**
	 * Constructor should auto-initialize null values
	 */
	@Test
	public void constructorTest()
	{
		MapObjectStyle style = new MapObjectStyle(true, true, true, "", 0, "", null, null);
		assertNotNull(style.getScaledStyles());
		assertNotNull(style.getDefenitionTags());
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
		EditableDefenitionTags tags = new EditableDefenitionTags();
		tags.add(new MapTag("k1", "v1"));
		tags.add(new MapTag("k2", "v2"));

		MapObjectStyle writingStyle = new MapObjectStyle(true, false,
						true, "name", 10, "object1", scaledStyles, tags);

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
			assertEquals(true, writingStyle.getDefenitionTags().compareTo(readingStyle.getDefenitionTags()));
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

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}
}
