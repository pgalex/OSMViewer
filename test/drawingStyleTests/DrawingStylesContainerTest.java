package drawingStyleTests;

import org.junit.Test;

/**
 * Tests of DrawingStylesContainer class
 *
 * @author pgalex
 */
public class DrawingStylesContainerTest
{
	/**
	 * Testing protected methods
	 */
	@Test
	public void protectedMethodsTest()
	{
		TestDrawingStylesContainer testStylesContainer = new TestDrawingStylesContainer();
		testStylesContainer.runProtectedMethodsTests();
	}
}