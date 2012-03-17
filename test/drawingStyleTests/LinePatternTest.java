package drawingStyleTests;

import drawingStyle.LinePattern;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * LinePattern class tests
 *
 * @author pgalex
 */
public class LinePatternTest
{
	private final String TEST_FILE_NAME = "testFile.txt";

	public LinePatternTest()
	{
	}

	/**
	 * Default value in constructor
	 */
	@Test
	public void constructorTest()
	{
		LinePattern pattern = new LinePattern(null);
		assertNotNull(pattern.getPattern());
		assertTrue(pattern.getPattern().length > 0);
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void fileTest()
	{
		float[] pattern = new float[4];
		pattern[0] = 2;
		pattern[1] = 3;
		pattern[2] = 4;
		pattern[3] = 5;
		LinePattern writingPattern = new LinePattern(pattern);
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingPattern.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		//чтение
		LinePattern readingPattern = new LinePattern();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
			readingPattern.readFromStream(input);
			input.close();
			assertArrayEquals(writingPattern.getPattern(), readingPattern.getPattern(), 0.01f);
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
