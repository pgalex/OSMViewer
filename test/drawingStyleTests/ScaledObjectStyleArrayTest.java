package drawingStyleTests;

import drawingStyle.ScaledObjectStyle;
import drawingStyle.ScaledObjectStyleArray;
import drawingStyle.exceptions.ScaleLevelOutOfBoundsException;
import drawingStyle.exceptions.ScaledStyleIsNullException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import static org.junit.Assert.*;
import org.junit.Test;

/**
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
	public void fileWithOtherScaleLevelsLessTest()
	{
		// меньше того что по умолчанию - последние копируются
		ScaledObjectStyleArray writingStyle = new ScaledObjectStyleArray(3);
		ScaledObjectStyle scaledStyle = new ScaledObjectStyle(false, true, true, null, null,
						null, null, null);
		writingStyle.setStyleOnScale(2, scaledStyle);
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			writingStyle.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}

		ScaledObjectStyleArray readingStyle = new ScaledObjectStyleArray();
		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(true, readingStyle.isDefaultLevelsCount());
			for (int i = writingStyle.count() - 1; i < readingStyle.count(); i++)
				assertEquals(readingStyle.getStyleOnScale(i).isDrawPolygon(), scaledStyle.isDrawPolygon());
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
	public void fileWithOtherScaleLevelsMoreTest()
	{
		// больше того что по умолчанию - последние обрезаются
		ScaledObjectStyleArray writingStyle = new ScaledObjectStyleArray(30);
		ScaledObjectStyleArray readingStyle = new ScaledObjectStyleArray();
		ScaledObjectStyle scaledStyle1 = new ScaledObjectStyle(false, true, true, null, null,
						null, null, null);
		writingStyle.setStyleOnScale(writingStyle.count() - 1, scaledStyle1);

		ScaledObjectStyle scaledStyle2 = new ScaledObjectStyle(false, true, false, null, null,
						null, null, null);
		writingStyle.setStyleOnScale(readingStyle.count() - 1, scaledStyle2);
		writingStyle.setStyleOnScale(readingStyle.count() - 2, scaledStyle1);
		try
		{
			DataOutputStream output = new DataOutputStream(new FileOutputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			writingStyle.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}


		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(DrawingStyleTestsParameters.TEST_FILE_NAME));
			readingStyle.readFromStream(input);
			input.close();
			assertEquals(true, readingStyle.isDefaultLevelsCount());
			assertEquals(false, writingStyle.isDefaultLevelsCount());
			assertEquals(readingStyle.getStyleOnScale(readingStyle.count() - 1).isDrawPolygon(), scaledStyle2.isDrawPolygon());
			assertEquals(readingStyle.getStyleOnScale(readingStyle.count() - 2).isDrawPolygon(), scaledStyle1.isDrawPolygon());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
