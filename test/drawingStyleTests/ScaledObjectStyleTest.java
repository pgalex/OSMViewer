package drawingStyleTests;

import drawingStyle.*;
import java.awt.Color;
import java.awt.Font;
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
public class ScaledObjectStyleTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public ScaledObjectStyleTest()
	{
	}

	/**
	 * тест создания с нулевыми аргументами
	 */
	@Test
	public void constructorTest()
	{
		ScaledObjectStyle testStyle = new ScaledObjectStyle(true, true, true, null, null, null, null, null);
		assertNotNull(testStyle.getPointStyle());
		assertNotNull(testStyle.getLineStyle());
		assertNotNull(testStyle.getPolygonStyle());
		assertNotNull(testStyle.getTextColor());
		assertNotNull(testStyle.getTextFont());
	}

	/**
	 * чтение запись
	 */
	@Test
	public void fileTest()
	{
		ScaledObjectStyle writingStyle = new ScaledObjectStyle(true, false, true, null, null,
						null, new IOColor(Color.RED), new IOFont(new Font("Arial", 1, 3)) );
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
		ScaledObjectStyle readingStyle = new ScaledObjectStyle();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(writingStyle.isDrawLine(), readingStyle.isDrawLine());
			assertEquals(writingStyle.isDrawPoint(), readingStyle.isDrawPoint());
			assertEquals(writingStyle.isDrawPolygon(), readingStyle.isDrawPolygon());
			assertEquals(writingStyle.getTextColor().getColor(), readingStyle.getTextColor().getColor());
			assertEquals(writingStyle.getTextFont().getFont(), readingStyle.getTextFont().getFont());
			assertEquals(writingStyle.getTextFont().getFont().getFamily(), readingStyle.getTextFont().getFont().getFamily());
			assertEquals(writingStyle.getTextFont().getFont().getStyle(), readingStyle.getTextFont().getFont().getStyle());
			assertEquals(writingStyle.getTextFont().getFont().getSize(), readingStyle.getTextFont().getFont().getSize());
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
