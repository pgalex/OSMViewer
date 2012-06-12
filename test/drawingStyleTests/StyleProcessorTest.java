package drawingStyleTests;

import org.junit.Test;

/**
 * Tests of StyleProcessor class
 *
 * @author pgalex
 */
public class StyleProcessorTest
{
	/**
	 * Testing protected methods of StyleProcessor
	 */
	@Test
	public void protectedMethodsTest()
	{
		TestStyleProcessor testStyleProcessor = new TestStyleProcessor();
		testStyleProcessor.runProtectedMethodsTests();
	}
}