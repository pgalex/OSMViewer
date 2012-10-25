package mapTests;

import rendering.ScalesArray;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * ScalesArray class test
 *
 * @author pgalex
 */
public class ScalesArrayTest
{
	/**
	 * Testing that less minimum than maximum
	 */
	@Test
	public void minimumLessThanMaximumTest()
	{
		assertTrue(ScalesArray.MINIMUM_SCALE_LEVEL < ScalesArray.MAXIMUM_SCALE_LEVEL);
	}

	/**
	 * Testing that scale increasing with scale level
	 */
	@Test
	public void scaleIncreasingWithScaleLevelTest()
	{
		for (int i = ScalesArray.MINIMUM_SCALE_LEVEL; i <= ScalesArray.MAXIMUM_SCALE_LEVEL - 1; i++)
		{
			double currentScale = ScalesArray.getScaleByScaleLevel(i);
			double nextScale = ScalesArray.getScaleByScaleLevel(i + 1);
			assertTrue(currentScale < nextScale);
		}
	}

	/**
	 * Testing getting scale when scale level less than minimum
	 */
	@Test
	public void scaleLevelLessThanMinimumTest()
	{
		double scaleOnMinimum = ScalesArray.getScaleByScaleLevel(ScalesArray.MINIMUM_SCALE_LEVEL);
		double scaleOnLessThanMinimum = ScalesArray.getScaleByScaleLevel(ScalesArray.MINIMUM_SCALE_LEVEL - 1);
		assertEquals(scaleOnMinimum, scaleOnLessThanMinimum, 0.00001);
	}

	/**
	 * Testing getting scale when scale level more than minimum
	 */
	@Test
	public void scaleLevelMoreThanMaximumTest()
	{
		double scaleOnMaximum = ScalesArray.getScaleByScaleLevel(ScalesArray.MAXIMUM_SCALE_LEVEL);
		double scaleOnMoreThanMaximum = ScalesArray.getScaleByScaleLevel(ScalesArray.MAXIMUM_SCALE_LEVEL + 1);
		assertEquals(scaleOnMaximum, scaleOnMoreThanMaximum, 0.00001);
	}
}
