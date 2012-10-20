package IOTesting;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.io.*;

/**
 * Common parameters of all drawing style tests
 *
 * @author pgalex
 */
public class IOTester
{
	/**
	 * Name of file used of reading/writing testing
	 */
	public static final String TEST_FILE_NAME = "test/supportFiles/testFile.txt";

	/**
	 * Write object to test file
	 *
	 * @param writingObject object to write
	 * @throws Exception error while writing
	 */
	public static void writeToTestFile(WritableMapData writingObject) throws Exception
	{
		if (writingObject == null)
		{
			throw new NullPointerException();
		}

		DataOutputStream output = new DataOutputStream(new FileOutputStream(IOTester.TEST_FILE_NAME));
		writingObject.writeToStream(output);
		output.close();
	}

	/**
	 * Read from test file to object
	 *
	 * @param readingObject object for reading
	 * @throws Exception error while reading
	 */
	public static void readFromTestFile(ReadableMapData readingObject) throws Exception
	{
		if (readingObject == null)
		{
			throw new NullPointerException();
		}

		DataInputStream input = new DataInputStream(new FileInputStream(IOTester.TEST_FILE_NAME));
		readingObject.readFromStream(input);
		input.close();
	}
}
