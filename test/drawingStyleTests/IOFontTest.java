package drawingStyleTests;

import drawingStyle.IOFont;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Testing IOFont class
 *
 * @author Евгений
 */
public class IOFontTest
{
	/**
	 * Test auto initialize in contructor
	 */
	@Test
	public void autoInitializeTest()
	{
		IOFont testFont = new IOFont(null);
		assertNotNull(testFont.getFont());
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void fileTest()
	{
		IOFont writeFont = new IOFont(new Font("Arial", 0, 15));
		//запись
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			writeFont.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}


		//чтение
		IOFont readFont = new IOFont();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			readFont.readFromStream(input);
			input.close();
			assertEquals(readFont.getFont().getFamily(), writeFont.getFont().getFamily());
			assertEquals(readFont.getFont().getStyle(), writeFont.getFont().getStyle());
			assertEquals(readFont.getFont().getSize(), writeFont.getFont().getSize());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
