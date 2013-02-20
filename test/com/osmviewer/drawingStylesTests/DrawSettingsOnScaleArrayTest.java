package com.osmviewer.drawingStylesTests;

import com.osmviewer.IOTesting.IOTester;
import com.osmviewer.drawingStyles.DrawSettingsOnScale;
import com.osmviewer.drawingStyles.DrawSettingsOnScaleArray;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * DrawSettingsOnScaleArray tests
 *
 * @author pgalex
 */
public class DrawSettingsOnScaleArrayTest
{
	/**
	 * Test finding text draw style if draw text is true
	 */
	@Test
	public void findingTextDrawStyleCanDrawText()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setDrawText();

		stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel(), styleOnScale);

		assertNotNull(stylesArray.findTextDrawSettings(stylesArray.getMinimumScaleLevel()));
	}
	
	/**
	 * Test finding text draw style if draw text is false
	 */
	@Test
	public void findingTextDrawStyleCanNotDrawText()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setNotDrawText();

		stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel(), styleOnScale);

		assertNull(stylesArray.findTextDrawSettings(stylesArray.getMinimumScaleLevel()));
	}

	/**
	 * Test finding point draw style if draw point is true
	 */
	@Test
	public void findingPointDrawStyleCanDrawPoint()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setDrawPoint();
		styleOnScale.setNotDrawLine();
		styleOnScale.setNotDrawPolygon();

		stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel(), styleOnScale);

		assertNotNull(stylesArray.findPointDrawSettings(stylesArray.getMinimumScaleLevel()));
	}

	/**
	 * Test finding point draw style if draw point is false
	 */
	@Test
	public void findingPointDrawStyleCannotDrawPoint()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setNotDrawPoint();
		styleOnScale.setDrawLine();
		styleOnScale.setDrawPolygon();

		stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel(), styleOnScale);

		assertNull(stylesArray.findPointDrawSettings(stylesArray.getMinimumScaleLevel()));
	}

	/**
	 * Test finding line draw style if draw line is true
	 */
	@Test
	public void findingLineDrawStyleCanDrawLine()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setNotDrawPoint();
		styleOnScale.setDrawLine();
		styleOnScale.setNotDrawPolygon();

		stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel(), styleOnScale);

		assertNotNull(stylesArray.findLineDrawSettings(stylesArray.getMinimumScaleLevel()));
	}

	/**
	 * Test finding line draw style if draw line is false
	 */
	@Test
	public void findingLineDrawStyleCannotDrawLine()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setDrawPoint();
		styleOnScale.setNotDrawLine();
		styleOnScale.setDrawPolygon();

		stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel(), styleOnScale);

		assertNull(stylesArray.findLineDrawSettings(stylesArray.getMinimumScaleLevel()));
	}

	/**
	 * Test finding polygon draw style if draw polygon is true
	 */
	@Test
	public void findingPolygonDrawStyleCanDrawPolygon()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setNotDrawPoint();
		styleOnScale.setNotDrawLine();
		styleOnScale.setDrawPolygon();

		stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel(), styleOnScale);

		assertNotNull(stylesArray.findPolygonDrawSettings(stylesArray.getMinimumScaleLevel()));
	}

	/**
	 * Test finding polygon draw style if draw polygon is false
	 */
	@Test
	public void findingPolygonDrawStyleCannotDrawPolygon()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale styleOnScale = new DrawSettingsOnScale();
		styleOnScale.setDrawPoint();
		styleOnScale.setDrawLine();
		styleOnScale.setNotDrawPolygon();

		stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel(), styleOnScale);

		assertNull(stylesArray.findPolygonDrawSettings(stylesArray.getMinimumScaleLevel()));
	}

	/**
	 * Testing correction of default maximum/minimum scale level values
	 */
	@Test
	public void creatingWithCorrectScaleLevelsCountTest()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		assertTrue(stylesArray.getMaximumScaleLevel() > stylesArray.getMinimumScaleLevel());
	}

	/**
	 * Testing setDrawSettingsOnScale normal work
	 */
	@Test
	public void setStyleOnScaleWithCorrectParametersTest()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale scaledStyle = new DrawSettingsOnScale();
		scaledStyle.setDrawPoint();
		scaledStyle.setNotDrawLine();
		scaledStyle.setDrawPolygon();

		final int correctScaleLevelAtBegin = stylesArray.getMinimumScaleLevel();
		stylesArray.setDrawSettingsOnScale(correctScaleLevelAtBegin, scaledStyle);

		final int correctScaleLevelAtMiddle = stylesArray.getMinimumScaleLevel() + 5;
		stylesArray.setDrawSettingsOnScale(correctScaleLevelAtMiddle, scaledStyle);

		final int correctScaleLevelAtEnd = stylesArray.getMaximumScaleLevel();
		stylesArray.setDrawSettingsOnScale(correctScaleLevelAtEnd, scaledStyle);

		assertEquals(scaledStyle.isDrawPoint(), stylesArray.getDrawSettingsOnScale(correctScaleLevelAtBegin).isDrawPoint());
		assertEquals(scaledStyle.isDrawLine(), stylesArray.getDrawSettingsOnScale(correctScaleLevelAtBegin).isDrawLine());
		assertEquals(scaledStyle.isDrawPolygon(), stylesArray.getDrawSettingsOnScale(correctScaleLevelAtBegin).isDrawPolygon());

		assertEquals(scaledStyle.isDrawPoint(), stylesArray.getDrawSettingsOnScale(correctScaleLevelAtMiddle).isDrawPoint());
		assertEquals(scaledStyle.isDrawLine(), stylesArray.getDrawSettingsOnScale(correctScaleLevelAtMiddle).isDrawLine());
		assertEquals(scaledStyle.isDrawPolygon(), stylesArray.getDrawSettingsOnScale(correctScaleLevelAtMiddle).isDrawPolygon());

		assertEquals(scaledStyle.isDrawPoint(), stylesArray.getDrawSettingsOnScale(correctScaleLevelAtEnd).isDrawPoint());
		assertEquals(scaledStyle.isDrawLine(), stylesArray.getDrawSettingsOnScale(correctScaleLevelAtEnd).isDrawLine());
		assertEquals(scaledStyle.isDrawPolygon(), stylesArray.getDrawSettingsOnScale(correctScaleLevelAtEnd).isDrawPolygon());
	}

	/**
	 * Testing setDrawSettingsOnScale on scale less than 0
	 */
	@Test
	public void setStyleOnScaleLessThanBounds()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale scaledStyle = new DrawSettingsOnScale();
		scaledStyle.setDrawPoint();
		scaledStyle.setNotDrawLine();
		scaledStyle.setDrawPolygon();
		try
		{
			stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel() - 1, scaledStyle); // there should be out of range exception
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Testing setDrawSettingsOnScale on scale more than maximum
	 */
	@Test
	public void setStyleOnScaleMoreThanBounds()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();
		DrawSettingsOnScale scaledStyle = new DrawSettingsOnScale();
		scaledStyle.setDrawPoint();
		scaledStyle.setNotDrawLine();
		scaledStyle.setDrawPolygon();

		try
		{
			stylesArray.setDrawSettingsOnScale(stylesArray.getMaximumScaleLevel() + 1, scaledStyle); // there should be out of range exception
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test setting null stylesArray
	 */
	@Test
	public void setNullStyleTest()
	{
		DrawSettingsOnScaleArray stylesArray = new DrawSettingsOnScaleArray();

		try
		{
			stylesArray.setDrawSettingsOnScale(stylesArray.getMinimumScaleLevel(), null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
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
			DrawSettingsOnScale styleAtBegin = new DrawSettingsOnScale();
			styleAtBegin.setDrawPoint();
			styleAtBegin.setNotDrawLine();
			styleAtBegin.setNotDrawPolygon();

			DrawSettingsOnScale styleAtMiddle = new DrawSettingsOnScale();
			styleAtMiddle.setNotDrawPoint();
			styleAtMiddle.setDrawLine();
			styleAtMiddle.setNotDrawPolygon();

			DrawSettingsOnScale styleAtEnd = new DrawSettingsOnScale();
			styleAtEnd.setNotDrawPoint();
			styleAtEnd.setNotDrawLine();
			styleAtEnd.setDrawPolygon();

			DrawSettingsOnScaleArray writingStyles = new DrawSettingsOnScaleArray();

			final int scaleLevelAtBegin = writingStyles.getMinimumScaleLevel();
			writingStyles.setDrawSettingsOnScale(scaleLevelAtBegin, styleAtBegin);

			final int scaleLevelAtMiddle = writingStyles.getMinimumScaleLevel() + 5;
			writingStyles.setDrawSettingsOnScale(scaleLevelAtMiddle, styleAtMiddle);

			final int scaleLevelAtEnd = writingStyles.getMaximumScaleLevel();
			writingStyles.setDrawSettingsOnScale(scaleLevelAtEnd, styleAtEnd);

			writingStyles.writeToStream(IOTester.createTestOutputStream());

			DrawSettingsOnScaleArray readingStyle = new DrawSettingsOnScaleArray();
			readingStyle.readFromStream(IOTester.createTestInputStream());

			assertEquals(readingStyle.getDrawSettingsOnScale(scaleLevelAtBegin).isDrawPoint(), styleAtBegin.isDrawPoint());
			assertEquals(readingStyle.getDrawSettingsOnScale(scaleLevelAtBegin).isDrawLine(), styleAtBegin.isDrawLine());
			assertEquals(readingStyle.getDrawSettingsOnScale(scaleLevelAtBegin).isDrawPolygon(), styleAtBegin.isDrawPolygon());

			assertEquals(readingStyle.getDrawSettingsOnScale(scaleLevelAtMiddle).isDrawPoint(), styleAtMiddle.isDrawPoint());
			assertEquals(readingStyle.getDrawSettingsOnScale(scaleLevelAtMiddle).isDrawLine(), styleAtMiddle.isDrawLine());
			assertEquals(readingStyle.getDrawSettingsOnScale(scaleLevelAtMiddle).isDrawPolygon(), styleAtMiddle.isDrawPolygon());

			assertEquals(readingStyle.getDrawSettingsOnScale(scaleLevelAtEnd).isDrawPoint(), styleAtEnd.isDrawPoint());
			assertEquals(readingStyle.getDrawSettingsOnScale(scaleLevelAtEnd).isDrawLine(), styleAtEnd.isDrawLine());
			assertEquals(readingStyle.getDrawSettingsOnScale(scaleLevelAtEnd).isDrawPolygon(), styleAtEnd.isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
