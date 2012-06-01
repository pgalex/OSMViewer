package drawingStyleTests;

import drawingStyle.IOColor;
import java.awt.Color;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * IOColor class tests
 *
 * @author abc
 */
public class IOColorTest
{
	/**
	 * Reading/writing test
	 */
	@Test
	public void fileTest()
	{
		IOColor writingColor = new IOColor(Color.MAGENTA);
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			writingColor.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			IOColor readingColor = new IOColor();
			readingColor.readFromStream(input);
			input.close();
			assertEquals(writingColor.getColor(), readingColor.getColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
