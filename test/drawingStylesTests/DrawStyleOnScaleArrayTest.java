package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.DrawStyleOnScale;
import drawingStyles.DrawStyleOnScaleArray;
import drawingStyles.exceptions.ScaleLevelOutOfBoundsException;
import drawingStyles.exceptions.ScaledStyleIsNullException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * DrawStyleOnScaleArray tests
 *
 * @author pgalex
 */
public class DrawStyleOnScaleArrayTest
{
	/**
	 * Testing correction of default maximum/minimum scale level values
	 */
	@Test
	public void creatingWithCorrectScaleLevelsCountTest()
	{
		DrawStyleOnScaleArray stylesArray = new DrawStyleOnScaleArray();
		assertTrue(stylesArray.getMaximumScaleLevel() > stylesArray.getMinimumScaleLevel());
	}

	/**
	 * Testing setStyleOnScale normal work
	 */
	@Test
	public void setStyleOnScaleWithCorrectParametersTest()
	{
		DrawStyleOnScaleArray stylesArray = new DrawStyleOnScaleArray();
		DrawStyleOnScale scaledStyle = new DrawStyleOnScale(true, false, true, null, null, null, null, null);

		final int correctScaleLevelAtBegin = stylesArray.getMinimumScaleLevel();
		stylesArray.setStyleOnScale(correctScaleLevelAtBegin, scaledStyle);
		
		final int correctScaleLevelAtMiddle = stylesArray.getMinimumScaleLevel() + 5;
		stylesArray.setStyleOnScale(correctScaleLevelAtMiddle, scaledStyle);
		
		final int correctScaleLevelAtEnd = stylesArray.getMaximumScaleLevel();
		stylesArray.setStyleOnScale(correctScaleLevelAtEnd, scaledStyle);

		assertEquals(scaledStyle.isDrawPoint(), stylesArray.getStyleOnScale(correctScaleLevelAtBegin).isDrawPoint());
		assertEquals(scaledStyle.isDrawLine(), stylesArray.getStyleOnScale(correctScaleLevelAtBegin).isDrawLine());
		assertEquals(scaledStyle.isDrawPolygon(), stylesArray.getStyleOnScale(correctScaleLevelAtBegin).isDrawPolygon());
		
		assertEquals(scaledStyle.isDrawPoint(), stylesArray.getStyleOnScale(correctScaleLevelAtMiddle).isDrawPoint());
		assertEquals(scaledStyle.isDrawLine(), stylesArray.getStyleOnScale(correctScaleLevelAtMiddle).isDrawLine());
		assertEquals(scaledStyle.isDrawPolygon(), stylesArray.getStyleOnScale(correctScaleLevelAtMiddle).isDrawPolygon());
		
		assertEquals(scaledStyle.isDrawPoint(), stylesArray.getStyleOnScale(correctScaleLevelAtEnd).isDrawPoint());
		assertEquals(scaledStyle.isDrawLine(), stylesArray.getStyleOnScale(correctScaleLevelAtEnd).isDrawLine());
		assertEquals(scaledStyle.isDrawPolygon(), stylesArray.getStyleOnScale(correctScaleLevelAtEnd).isDrawPolygon());
	}

	/**
	 * Testing setStyleOnScale on scale less than 0
	 */
	@Test
	public void setStyleOnScaleLessThanBounds()
	{
		DrawStyleOnScaleArray stylesArray = new DrawStyleOnScaleArray();
		DrawStyleOnScale scaledStyle = new DrawStyleOnScale(true, false, true, null, null, null, null, null);
		try
		{
			stylesArray.setStyleOnScale(stylesArray.getMinimumScaleLevel() - 1, scaledStyle); // there should be out of range exception
			fail();
		}
		catch (ScaleLevelOutOfBoundsException ex)
		{
			assertEquals(stylesArray, ex.getArrayThrowedException());
			assertEquals(stylesArray.getMinimumScaleLevel() - 1, ex.getIncorrectScaleLevel());
			assertEquals(stylesArray.getMinimumScaleLevel(), ex.getBoundsMinimum());
			assertEquals(stylesArray.getMaximumScaleLevel(), ex.getBoundsMaximum());
		}
	}

	/**
	 * Testing setStyleOnScale on scale more than maximum
	 */
	@Test
	public void setStyleOnScaleMoreThanBounds()
	{
		DrawStyleOnScaleArray stylesArray = new DrawStyleOnScaleArray();
		DrawStyleOnScale scaledStyle = new DrawStyleOnScale(true, false, true, null, null, null, null, null);
		try
		{
			stylesArray.setStyleOnScale(stylesArray.getMaximumScaleLevel() + 1, scaledStyle); // there should be out of range exception
			fail();
		}
		catch (ScaleLevelOutOfBoundsException ex)
		{
			assertEquals(stylesArray, ex.getArrayThrowedException());
			assertEquals(stylesArray.getMaximumScaleLevel() + 1, ex.getIncorrectScaleLevel());
			assertEquals(stylesArray.getMinimumScaleLevel(), ex.getBoundsMinimum());
			assertEquals(stylesArray.getMaximumScaleLevel(), ex.getBoundsMaximum());
		}
	}

	/**
	 * Test setting null stylesArray
	 */
	@Test
	public void setNullStyleTest()
	{
		DrawStyleOnScaleArray stylesArray = new DrawStyleOnScaleArray();

		try
		{
			stylesArray.setStyleOnScale(stylesArray.getMinimumScaleLevel(), null);
			fail();
		}
		catch (ScaledStyleIsNullException ex)
		{
			assertEquals(stylesArray, ex.getArrayThrowedException());
		}
	}

	/**
	 * Reading/writing test
	 */
	@Test
	public void readingWritingTest()
	{
		try
		{
			DrawStyleOnScale styleAtBegin = new DrawStyleOnScale(true, false, false, null, null,
							null, null, null);
			DrawStyleOnScale styleAtMiddle = new DrawStyleOnScale(false, true, false, null, null,
							null, null, null);
			DrawStyleOnScale styleAtEnd = new DrawStyleOnScale(false, false, true, null, null,
							null, null, null);

			DrawStyleOnScaleArray writingStyles = new DrawStyleOnScaleArray();
			
			final int scaleLevelAtBegin = writingStyles.getMinimumScaleLevel();
			writingStyles.setStyleOnScale(scaleLevelAtBegin, styleAtBegin);
			
			final int scaleLevelAtMiddle = writingStyles.getMinimumScaleLevel() + 5;
			writingStyles.setStyleOnScale(scaleLevelAtMiddle, styleAtMiddle);
			
			final int scaleLevelAtEnd = writingStyles.getMaximumScaleLevel();
			writingStyles.setStyleOnScale(scaleLevelAtEnd, styleAtEnd);
			
			IOTester.writeToTestFile(writingStyles);

			DrawStyleOnScaleArray readingStyle = new DrawStyleOnScaleArray();
			IOTester.readFromTestFile(readingStyle);
			
			assertEquals(readingStyle.getStyleOnScale(scaleLevelAtBegin).isDrawPoint(), styleAtBegin.isDrawPoint());
			assertEquals(readingStyle.getStyleOnScale(scaleLevelAtBegin).isDrawLine(), styleAtBegin.isDrawLine());
			assertEquals(readingStyle.getStyleOnScale(scaleLevelAtBegin).isDrawPolygon(), styleAtBegin.isDrawPolygon());
			
			assertEquals(readingStyle.getStyleOnScale(scaleLevelAtMiddle).isDrawPoint(), styleAtMiddle.isDrawPoint());
			assertEquals(readingStyle.getStyleOnScale(scaleLevelAtMiddle).isDrawLine(), styleAtMiddle.isDrawLine());
			assertEquals(readingStyle.getStyleOnScale(scaleLevelAtMiddle).isDrawPolygon(), styleAtMiddle.isDrawPolygon());
			
			assertEquals(readingStyle.getStyleOnScale(scaleLevelAtEnd).isDrawPoint(), styleAtEnd.isDrawPoint());
			assertEquals(readingStyle.getStyleOnScale(scaleLevelAtEnd).isDrawLine(), styleAtEnd.isDrawLine());
			assertEquals(readingStyle.getStyleOnScale(scaleLevelAtEnd).isDrawPolygon(), styleAtEnd.isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}