package drawingStylesTests;

import org.junit.Test;

/**
 * Tests of DrawingStylesContainer class
 *
 * @author pgalex
 */
public class MapObjectDrawStylesContainerTest
{
	/**
	 * Testing protected methods
	 */
	@Test
	public void protectedMethodsTest()
	{
		TestMapObjectDrawStylesContainer testStylesContainer = new TestMapObjectDrawStylesContainer();
		testStylesContainer.runProtectedMethodsTests();
	}
}