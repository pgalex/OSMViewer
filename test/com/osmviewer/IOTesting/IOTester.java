package com.osmviewer.IOTesting;

import java.io.*;

/**
 * Common parameters of all drawing style tests
 *
 * @author pgalex
 */
public class IOTester
{
	public static final String TEST_FILE_NAME = "test/temporaryFiles/testFile.txt";

	public static DataOutputStream createTestOutputStream() throws FileNotFoundException
	{
		return new DataOutputStream(new FileOutputStream(IOTester.TEST_FILE_NAME));
	}

	public static DataInputStream createTestInputStream() throws FileNotFoundException
	{
		return new DataInputStream(new FileInputStream(IOTester.TEST_FILE_NAME));
	}
}
