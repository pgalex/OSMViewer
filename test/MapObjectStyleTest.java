
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
	 * Тест базовых функций кол-во уровней месштаба
	 */
	@Test
	public void scaleLevelsCountTest()
	{
		// кол-во по умолчанию
		MapObjectStyle style = new MapObjectStyle();
		assertEquals(true, style.isDefaultScaleLevelsCount());

		//кол-во не по умолчанию
		style = new MapObjectStyle(5);
		assertEquals(false, style.isDefaultScaleLevelsCount());
		assertEquals(5, style.getCurrentScaleLevelsCount());
	}

	/**
	 * Тест работы фунций установки, получения стиля на уровне масштаба. Они
	 * должны учитывать неправильные значения на входе
	 */
	@Test
	public void scaleLevelsTest()
	{
		MapObjectStyle style = new MapObjectStyle(8);
		ScaledObjectStyle scaledStyle = new ScaledObjectStyle(true, false, true, null, null, null, null, null);

		// нормальный уровень
		style.setStyleOnScale(4, scaledStyle);
		assertEquals(scaledStyle.isDrawLine(), style.getStyleOnScale(4).isDrawLine());
		assertEquals(scaledStyle.isDrawPoint(), style.getStyleOnScale(4).isDrawPoint());

		// меньше ноля
		style.setStyleOnScale(-1, scaledStyle);
		assertEquals(scaledStyle.isDrawLine(), style.getStyleOnScale(0).isDrawLine());
		assertEquals(scaledStyle.isDrawPoint(), style.getStyleOnScale(0).isDrawPoint());

		// больше максимального
		style.setStyleOnScale(style.getCurrentScaleLevelsCount() + 2, scaledStyle);
		assertEquals(scaledStyle.isDrawLine(), style.getStyleOnScale(style.getCurrentScaleLevelsCount() - 1).isDrawLine());
		assertEquals(scaledStyle.isDrawPoint(), style.getStyleOnScale(style.getCurrentScaleLevelsCount() - 1).isDrawPoint());
	}

	/**
	 * Тест чтения/записи с меньшими кол-вом уровней масштаба
	 */
	@Test
	public void fileWithOtherScaleLevelsLessTest()
	{
		// меньше того что по умолчанию - последние копируются
		MapObjectStyle writingStyle = new MapObjectStyle(3);
		ScaledObjectStyle scaledStyle = new ScaledObjectStyle(false, true, true, null, null, 
						null, null, null);
		writingStyle.setStyleOnScale(2, scaledStyle);
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

		MapObjectStyle readingStyle = new MapObjectStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(true, readingStyle.isDefaultScaleLevelsCount());
			for (int i = writingStyle.getCurrentScaleLevelsCount() - 1; i < readingStyle.getCurrentScaleLevelsCount(); i++)
				assertEquals(readingStyle.getStyleOnScale(i).isDrawPolygon(), scaledStyle.isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Тест чтения/записи с большим кол-вом уровней масштаба
	 */
	@Test
	public void fileWithOtherScaleLevelsMoreTest()
	{
		// больше того что по умолчанию - последние обрезаются
		MapObjectStyle writingStyle = new MapObjectStyle(30);
		MapObjectStyle readingStyle = new MapObjectStyle();
		ScaledObjectStyle scaledStyle1 = new ScaledObjectStyle(false, true, true, null, null, 
						null, null, null);
		writingStyle.setStyleOnScale(writingStyle.getCurrentScaleLevelsCount() - 1, scaledStyle1);

		ScaledObjectStyle scaledStyle2 = new ScaledObjectStyle(false, true, false, null, null, 
						null, null, null);
		writingStyle.setStyleOnScale(readingStyle.getCurrentScaleLevelsCount() - 1, scaledStyle2);
		writingStyle.setStyleOnScale(readingStyle.getCurrentScaleLevelsCount() - 2, scaledStyle1);
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


		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(true, readingStyle.isDefaultScaleLevelsCount());
			assertEquals(false, writingStyle.isDefaultScaleLevelsCount());
			assertEquals(readingStyle.getStyleOnScale(readingStyle.getCurrentScaleLevelsCount() - 1).isDrawPolygon(), scaledStyle2.isDrawPolygon());
			assertEquals(readingStyle.getStyleOnScale(readingStyle.getCurrentScaleLevelsCount() - 2).isDrawPolygon(), scaledStyle1.isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Тест чтения/записи в файл
	 */
	@Test
	public void fileTest()
	{
		MapObjectStyle writingStyle = new MapObjectStyle();
		writingStyle.canBeLine = true;
		writingStyle.canBePoint = false;
		writingStyle.canBePolygon = true;
		writingStyle.defenitionTags.add(new MapTag("k1", "v1"));
		writingStyle.defenitionTags.add(new MapTag("k2", "v2"));
		writingStyle.textTagKey = "name";
		writingStyle.drawPriority = 10;
		writingStyle.description = "object1";
		ScaledObjectStyle scaledStyle0 = new ScaledObjectStyle(true, false, true, null, null, 
						null, null, null);
		writingStyle.setStyleOnScale(0, scaledStyle0);
		ScaledObjectStyle scaledStyle5 = new ScaledObjectStyle(false, true, true, null, null, 
						null, null, null);
		writingStyle.setStyleOnScale(5, scaledStyle5);

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
			assertEquals(writingStyle.canBeLine, readingStyle.canBeLine);
			assertEquals(writingStyle.canBePoint, readingStyle.canBePoint);
			assertEquals(writingStyle.canBePolygon, readingStyle.canBePolygon);
			assertEquals(true, writingStyle.compareDefenitionTags(readingStyle.defenitionTags));
			assertEquals(writingStyle.description, readingStyle.description);
			assertEquals(writingStyle.textTagKey, readingStyle.textTagKey);
			assertEquals(writingStyle.drawPriority, readingStyle.drawPriority);
			assertEquals(writingStyle.description, readingStyle.description);
			assertEquals(writingStyle.getStyleOnScale(0).isDrawLine(), readingStyle.getStyleOnScale(0).isDrawLine());
			assertEquals(writingStyle.getStyleOnScale(0).isDrawPoint(), readingStyle.getStyleOnScale(0).isDrawPoint());
			assertEquals(writingStyle.getStyleOnScale(0).isDrawPolygon(), readingStyle.getStyleOnScale(0).isDrawPolygon());
			assertEquals(writingStyle.getStyleOnScale(5).isDrawLine(), readingStyle.getStyleOnScale(5).isDrawLine());
			assertEquals(writingStyle.getStyleOnScale(5).isDrawPoint(), readingStyle.getStyleOnScale(5).isDrawPoint());
			assertEquals(writingStyle.getStyleOnScale(5).isDrawPolygon(), readingStyle.getStyleOnScale(5).isDrawPolygon());
			assertEquals(writingStyle.getCurrentScaleLevelsCount(), readingStyle.getCurrentScaleLevelsCount());
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
