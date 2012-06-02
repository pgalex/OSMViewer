package drawingStyleTests;

import drawingStyle.ScaledObjectStyle;
import drawingStyle.ScaledObjectStyleArray;
import drawingStyle.exceptions.ScaleLevelOutOfBoundsException;
import drawingStyle.exceptions.ScaledStyleIsNullException;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * ScaledObjectStyleArray tests
 *
 * @author pgalex
 */
public class ScaledObjectStyleArrayTest
{
	/**
	 * Тест isDefaultLevelsCount кол-во по умолчанию
	 */
	@Test
	public void defaultScaleLevelsCountTest()
	{
		ScaledObjectStyleArray style = new ScaledObjectStyleArray();
		assertEquals(true, style.isDefaultLevelsCount());
	}

	/**
	 * Тест кол-во не по умолчанию
	 */
	@Test
	public void notDefaultScaleLevelsCountTest()
	{
		ScaledObjectStyleArray style = new ScaledObjectStyleArray(5);
		assertEquals(false, style.isDefaultLevelsCount());
		assertEquals(5, style.count());
	}

	/**
	 * Тест работы фунций установки, получения стиля на уровне масштаба. Они
	 * должны учитывать неправильные значения на входе
	 */
	@Test
	public void scaleLevelsTest()
	{
		ScaledObjectStyleArray style = new ScaledObjectStyleArray(8);
		ScaledObjectStyle scaledStyle = new ScaledObjectStyle(true, false, true, null, null, null, null, null);

		// нормальный уровень
		style.setStyleOnScale(4, scaledStyle);
		assertEquals(scaledStyle.isDrawLine(), style.getStyleOnScale(4).isDrawLine());
		assertEquals(scaledStyle.isDrawPoint(), style.getStyleOnScale(4).isDrawPoint());

		// меньше ноля
		try
		{
			style.setStyleOnScale(-1, scaledStyle); // there should be out of range exception
			fail();
		}
		catch (ScaleLevelOutOfBoundsException ex)
		{
			assertEquals(style, ex.getArrayThrowedException());
			assertEquals(-1, ex.getIncorrectScaleLevel());
			assertEquals(0, ex.getBoundsMinimum());
			assertEquals(style.count(), ex.getBoundsMaximum());
		}

		// больше максимального
		try
		{
			style.setStyleOnScale(style.count() + 1, scaledStyle); // there should be out of range exception
			fail();
		}
		catch (ScaleLevelOutOfBoundsException ex)
		{
			assertEquals(style, ex.getArrayThrowedException());
			assertEquals(style.count() + 1, ex.getIncorrectScaleLevel());
			assertEquals(0, ex.getBoundsMinimum());
			assertEquals(style.count(), ex.getBoundsMaximum());
		}
	}

	/**
	 * Test setting null style
	 */
	@Test
	public void setNullStyleTest()
	{
		ScaledObjectStyleArray style = new ScaledObjectStyleArray(1);

		try
		{
			style.setStyleOnScale(0, null);
			fail();
		}
		catch (ScaledStyleIsNullException ex)
		{
			assertEquals(style, ex.getArrayThrowedException());
		}
	}

	/**
	 * Тест чтения/записи с меньшими кол-вом уровней масштаба
	 */
	@Test
	public void writingWithLessScaleAndReadingLevelsTest()
	{
		try
		{
			// меньше того что по умолчанию - последние копируются
			ScaledObjectStyle scaledStyle = new ScaledObjectStyle(false, true, true, null, null,
							null, null, null);

			ScaledObjectStyleArray writedStyle = new ScaledObjectStyleArray(3);
			writedStyle.setStyleOnScale(2, scaledStyle);

			DrawingStyleIOTester.writeToTestFile(writedStyle);

			ScaledObjectStyleArray readStyle = new ScaledObjectStyleArray();
			DrawingStyleIOTester.readFromTestFile(readStyle);

			assertEquals(true, readStyle.isDefaultLevelsCount());
			for (int i = writedStyle.count() - 1; i < readStyle.count(); i++)
				assertEquals(readStyle.getStyleOnScale(i).isDrawPolygon(), scaledStyle.isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}

	/**
	 * Тест чтения/записи с большим кол-вом уровней масштаба
	 */
	@Test
	public void writingWithMoreScaleLevelsAndReadingTest()
	{
		try
		{
			final int WRITING_ARRAY_SIZE = 30;
			final int READING_ARRAY_SIZE = 12;
			// больше того что по умолчанию - последние обрезаются
			ScaledObjectStyle scaledStyle1 = new ScaledObjectStyle(false, true, true, null, null,
							null, null, null);
			ScaledObjectStyle scaledStyle2 = new ScaledObjectStyle(false, true, false, null, null,
							null, null, null);

			ScaledObjectStyleArray writedStyleArray = new ScaledObjectStyleArray(WRITING_ARRAY_SIZE);
			writedStyleArray.setStyleOnScale(WRITING_ARRAY_SIZE - 1, scaledStyle1);
			writedStyleArray.setStyleOnScale(READING_ARRAY_SIZE - 1, scaledStyle2);
			writedStyleArray.setStyleOnScale(READING_ARRAY_SIZE - 2, scaledStyle1);
			
			DrawingStyleIOTester.writeToTestFile(writedStyleArray);
			
			ScaledObjectStyleArray readStyleArray = new ScaledObjectStyleArray(READING_ARRAY_SIZE);
			DrawingStyleIOTester.readFromTestFile(readStyleArray);

			assertEquals(true, readStyleArray.isDefaultLevelsCount());
			assertEquals(false, writedStyleArray.isDefaultLevelsCount());
			assertEquals(readStyleArray.getStyleOnScale(readStyleArray.count() - 1).isDrawPolygon(), scaledStyle2.isDrawPolygon());
			assertEquals(readStyleArray.getStyleOnScale(readStyleArray.count() - 2).isDrawPolygon(), scaledStyle1.isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
