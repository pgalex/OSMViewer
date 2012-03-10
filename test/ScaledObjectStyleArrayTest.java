/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import drawingStyle.ScaledObjectStyle;
import drawingStyle.ScaledObjectStyleArray;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import org.junit.AfterClass;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class ScaledObjectStyleArrayTest
{
	private final String TEST_FILE_NAME = "testFile.txt";
	
	public ScaledObjectStyleArrayTest()
	{
	}
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
		style.setStyleOnScale(-1, scaledStyle);
		assertEquals(scaledStyle.isDrawLine(), style.getStyleOnScale(0).isDrawLine());
		assertEquals(scaledStyle.isDrawPoint(), style.getStyleOnScale(0).isDrawPoint());

		// больше максимального
		style.setStyleOnScale(style.count() + 2, scaledStyle);
		assertEquals(scaledStyle.isDrawLine(), style.getStyleOnScale(style.count() - 1).isDrawLine());
		assertEquals(scaledStyle.isDrawPoint(), style.getStyleOnScale(style.count() - 1).isDrawPoint());
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
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
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
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
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
			DataOutputStream output = new DataOutputStream(new FileOutputStream(TEST_FILE_NAME));
			writingStyle.writeToStream(output);
			output.close();
		}
		catch (Exception ex)
		{
			fail();
		}


		try
		{
			DataInputStream input = new DataInputStream(new FileInputStream(TEST_FILE_NAME));
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

	@BeforeClass
	public static void setUpClass() throws Exception
	{
	}

	@AfterClass
	public static void tearDownClass() throws Exception
	{
	}
}
