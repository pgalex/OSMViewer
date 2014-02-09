package com.osmviewer.IOTesting;

import java.io.*;

/**
 * Using for read/write testing
 *
 * @author pgalex
 */
public class IOTester
{
	public static final String TEST_FILE_NAME = "test/supportFiles/testFile.txt";

	public static DataOutputStream createTestOutputStream() throws FileNotFoundException
	{
		return new DataOutputStream(new FileOutputStream(IOTester.TEST_FILE_NAME));
	}

	public static DataInputStream createTestInputStream() throws FileNotFoundException
	{
		return new DataInputStream(new FileInputStream(IOTester.TEST_FILE_NAME));
	}
	
	public static void deleteTestFile()
	{
		File testFile = new File(IOTester.TEST_FILE_NAME);
		if (testFile.exists())
		{
			testFile.delete();
		}
	}
}
