
import drawingStyle.MapObjectStyle;
import drawingStyle.ScaledObjectStyle;
import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import map.MapTag;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
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
	 * Тест базовых функция кол-во уровней месштаба
	 */
	@Test
	public void ScaleLevelsCountTest()
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
	public void ScaleLevelsTest()
	{
		MapObjectStyle style = new MapObjectStyle(8);
		ScaledObjectStyle scaledStyle = new ScaledObjectStyle();
		scaledStyle.lineStyle.color = Color.YELLOW;
		scaledStyle.drawLine = true;

		// нормальный уровень
		style.setStyleOnScale(4, scaledStyle);
		assertEquals(scaledStyle.drawLine, style.getStyleOnScale(4).drawLine);
		assertEquals(scaledStyle.lineStyle.color, style.getStyleOnScale(4).lineStyle.color);

		// меньше ноля
		style.setStyleOnScale(-1, scaledStyle);
		assertEquals(scaledStyle.drawLine, style.getStyleOnScale(0).drawLine);
		assertEquals(scaledStyle.lineStyle.color, style.getStyleOnScale(0).lineStyle.color);

		// больше максимального
		style.setStyleOnScale(style.getCurrentScaleLevelsCount() + 2, scaledStyle);
		assertEquals(scaledStyle.drawLine, style.getStyleOnScale(style.getCurrentScaleLevelsCount() - 1).drawLine);
		assertEquals(scaledStyle.lineStyle.color, style.getStyleOnScale(style.getCurrentScaleLevelsCount() - 1).lineStyle.color);
	}

	/**
	 * Тест чтения/записи с меньшими кол-вом уровней масштаба
	 */
	@Test
	public void FileWithOtherScaleLevelsLessTest()
	{
		// меньше того что по умолчанию - последние копируются
		MapObjectStyle writingStyle = new MapObjectStyle(3);
		ScaledObjectStyle scaledStyle = new ScaledObjectStyle();
		scaledStyle.lineStyle.color = Color.RED;
		writingStyle.setStyleOnScale(2, scaledStyle);
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

		MapObjectStyle readingStyle = new MapObjectStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.ReadFromStream(input);
			input.close();
			assertEquals(true, readingStyle.isDefaultScaleLevelsCount());
			for (int i = writingStyle.getCurrentScaleLevelsCount() - 1; i < readingStyle.getCurrentScaleLevelsCount(); i++)
				assertEquals(readingStyle.getStyleOnScale(i).lineStyle.color, scaledStyle.lineStyle.color);
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
	public void FileWithOtherScaleLevelsMoreTest()
	{
		// больше того что по умолчанию - последние обрезаются
		MapObjectStyle writingStyle = new MapObjectStyle(30);
		MapObjectStyle readingStyle = new MapObjectStyle();
		ScaledObjectStyle scaledStyle1 = new ScaledObjectStyle();
		scaledStyle1.lineStyle.color = Color.RED;
		writingStyle.setStyleOnScale(writingStyle.getCurrentScaleLevelsCount() - 1, scaledStyle1);

		ScaledObjectStyle scaledStyle2 = new ScaledObjectStyle();
		scaledStyle2.lineStyle.color = Color.GREEN;
		writingStyle.setStyleOnScale(readingStyle.getCurrentScaleLevelsCount() - 1, scaledStyle2);
		writingStyle.setStyleOnScale(readingStyle.getCurrentScaleLevelsCount() - 2, scaledStyle1);
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


		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.ReadFromStream(input);
			input.close();
			assertEquals(true, readingStyle.isDefaultScaleLevelsCount());
			assertEquals(false, writingStyle.isDefaultScaleLevelsCount());
			assertEquals(readingStyle.getStyleOnScale(readingStyle.getCurrentScaleLevelsCount() - 1).lineStyle.color,
							scaledStyle2.lineStyle.color);
			assertEquals(readingStyle.getStyleOnScale(readingStyle.getCurrentScaleLevelsCount() - 2).lineStyle.color,
							scaledStyle1.lineStyle.color);
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
	public void FileTest()
	{
		MapObjectStyle writingStyle = new MapObjectStyle();
		writingStyle.canBeLine = true;
		writingStyle.canBePoint = false;
		writingStyle.canBePolygon = true;
		writingStyle.description = "object1";
		writingStyle.defenitionTags.add(new MapTag("k1", "v1"));
		writingStyle.defenitionTags.add(new MapTag("k2", "v2"));
		writingStyle.drawPriority = 10;
		ScaledObjectStyle scaledStyle0 = new ScaledObjectStyle();
		scaledStyle0.drawLine = true;
		scaledStyle0.drawPoint = false;
		scaledStyle0.drawPolygon = true;
		scaledStyle0.lineStyle.color = Color.CYAN;
		writingStyle.setStyleOnScale(0, scaledStyle0);
		ScaledObjectStyle scaledStyle5 = new ScaledObjectStyle();
		scaledStyle5.drawLine = false;
		scaledStyle5.drawPoint = true;
		scaledStyle5.drawPolygon = true;
		scaledStyle5.lineStyle.color = Color.CYAN;
		writingStyle.setStyleOnScale(5, scaledStyle5);
		writingStyle.textFont = new Font("Arial", Font.BOLD, 16);
		writingStyle.textColor = Color.MAGENTA;
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
			assertEquals(writingStyle.textFont, readingStyle.textFont);
			assertEquals(writingStyle.getStyleOnScale(0).drawLine, readingStyle.getStyleOnScale(0).drawLine);
			assertEquals(writingStyle.getStyleOnScale(0).drawPoint, readingStyle.getStyleOnScale(0).drawPoint);
			assertEquals(writingStyle.getStyleOnScale(0).drawPolygon, readingStyle.getStyleOnScale(0).drawPolygon);
			assertEquals(writingStyle.getStyleOnScale(0).lineStyle.color, readingStyle.getStyleOnScale(0).lineStyle.color);
			assertEquals(writingStyle.getStyleOnScale(5).drawLine, readingStyle.getStyleOnScale(5).drawLine);
			assertEquals(writingStyle.getStyleOnScale(5).drawPoint, readingStyle.getStyleOnScale(5).drawPoint);
			assertEquals(writingStyle.getStyleOnScale(5).drawPolygon, readingStyle.getStyleOnScale(5).drawPolygon);
			assertEquals(writingStyle.getStyleOnScale(5).lineStyle.color, readingStyle.getStyleOnScale(5).lineStyle.color);
			assertEquals(writingStyle.getCurrentScaleLevelsCount(), readingStyle.getCurrentScaleLevelsCount());
			assertEquals(writingStyle.textColor, readingStyle.textColor);
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
