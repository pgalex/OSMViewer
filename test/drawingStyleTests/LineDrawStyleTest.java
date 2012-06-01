package drawingStyleTests;

import drawingStyle.IOColor;
import drawingStyle.LineDrawStyle;
import drawingStyle.LinePattern;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author abc
 */
public class LineDrawStyleTest
{
	/**
	 * Тест контсруктора с нулевыми параметрами
	 */
	@Test
	public void constructorTest()
	{
		// null шаблон
		LineDrawStyle testStyle = new LineDrawStyle(null, 1, null);
		assertNotNull(testStyle.getColor());
		assertNotNull(testStyle.getLinePattern());
	}

	@Test
	public void fileTest()
	{
		float[] pattern = new float[4];
		pattern[0] = 2;
		pattern[1] = 3;
		pattern[2] = 4;
		pattern[3] = 5;
		LineDrawStyle writingStyle = new LineDrawStyle(new IOColor(Color.CYAN), 11, new LinePattern(pattern));

		//запись
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
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
			DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(writingStyle.getColor().getColor(), readingStyle.getColor().getColor());
			assertArrayEquals(writingStyle.getLinePattern().getPattern(), readingStyle.getLinePattern().getPattern(), 0.01f);
			assertEquals(writingStyle.getWidth(), readingStyle.getWidth());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
