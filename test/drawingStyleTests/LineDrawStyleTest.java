package drawingStyleTests;

import drawingStyle.IOColor;
import drawingStyle.LineDrawStyle;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author abc
 */
public class LineDrawStyleTest
{
	private final String TEST_FILE_NAME = "testFile.txt";
	
	public LineDrawStyleTest()
	{
	}

	/**
	 * Тест контсруктора с нулевыми параметрами
	 */
	@Test
	public void constructorTest()
	{
		// null шаблон
		LineDrawStyle testStyle = new LineDrawStyle(new IOColor(Color.BLACK), 2, null);
		assertArrayEquals(LineDrawStyle.SOLID_LINE_PATTERN, testStyle.getPattern(), 0.01f);

		// пустой шаблон
		testStyle = new LineDrawStyle(new IOColor(Color.BLACK), 2, new float[0]);
		assertArrayEquals(LineDrawStyle.SOLID_LINE_PATTERN, testStyle.getPattern(), 0.01f);

		// null цвет
		testStyle = new LineDrawStyle(null, 1, null);
		assertNotNull(testStyle.getColor());
	}
	
	@Test
	public void fileTest()
	{
		float[] pattern = new float[4];
		pattern[0] = 2;
		pattern[1] = 3;
		pattern[2] = 4;
		pattern[3] = 5;
		LineDrawStyle writingStyle = new LineDrawStyle(new IOColor(Color.CYAN), 11, pattern);

		//запись
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
		LineDrawStyle readingStyle = new LineDrawStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(writingStyle.getColor().getColor(), readingStyle.getColor().getColor());
			assertArrayEquals(writingStyle.getPattern(), readingStyle.getPattern(), 0.01f);
			assertEquals(writingStyle.getWidth(), readingStyle.getWidth());
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
