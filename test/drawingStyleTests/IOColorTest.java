package drawingStyleTests;

import drawingStyle.IOColor;
import java.awt.Color;
import java.io.*;
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
	public void readingWritingTest()
	{
		try
		{
			IOColor writedColor = new IOColor(Color.MAGENTA);
			writeColorToTestFile(writedColor);

			IOColor readedColor = readColorFromTestFile();

			assertEquals(writedColor.getColor(), readedColor.getColor());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	private void writeColorToTestFile(IOColor pWritingColor) throws IOException
	{
		DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
		pWritingColor.writeToStream(output);
		output.close();
	}

	private IOColor readColorFromTestFile() throws IOException
	{
		DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
		IOColor readedColor = new IOColor();
		readedColor.readFromStream(input);
		input.close();

		return readedColor;
	}
}
