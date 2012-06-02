package drawingStyleTests;

import fileIO.ReadableMapData;
import fileIO.WritableMapData;
import java.io.*;

/**
 * Common parameters of all drawing style tests
 *
 * @author pgalex
 */
public class DrawingStyleIOTester
{
	/**
	 * Name of file used of reading/writing testing
	 */
	public static final String TEST_FILE_NAME = "testFile.txt";

	/**
	 * Write object to test file
	 *
	 * @param pWritingObject object to write
	 * @throws Exception error while writing
	 */
	public static void writeToTestFile(WritableMapData pWritingObject) throws Exception
	{
		if (pWritingObject == null)
			throw new NullPointerException();

		DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleIOTester.TEST_FILE_NAME));
		pWritingObject.writeToStream(output);
		output.close();
	}

	/**
	 * Read from test file to object
	 *
	 * @param pReadingObject object for reading
	 * @throws Exception error while reading
	 */
	public static void readFromTestFile(ReadableMapData pReadingObject) throws Exception
	{
		if (pReadingObject == null)
			throw new NullPointerException();

		DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleIOTester.TEST_FILE_NAME));
		pReadingObject.readFromStream(input);
		input.close();
	}
}
