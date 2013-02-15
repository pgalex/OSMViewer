package drawingStylesTests;

import IOTesting.IOTester;
import drawingStyles.ColorPolygonFiller;
import drawingStyles.PolygonFiller;
import drawingStyles.PolygonFillersFactory;
import drawingStyles.TexturePolygonFiller;
import java.awt.Color;
import java.awt.image.BufferedImage;
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
public class PolygonFillersFactoryTest
{
	/**
	 * Creating color filler normal work test
	 */
	@Test
	public void createColorFillerTest()
	{
		PolygonFiller createdFiller = PolygonFillersFactory.createColorFiller(Color.WHITE);

		assertTrue(createdFiller instanceof ColorPolygonFiller);
	}

	/**
	 * Creating color filler with null color test
	 */
	@Test
	public void createColorFillerWithNullColorTest()
	{
		try
		{
			PolygonFiller createdFiller = PolygonFillersFactory.createColorFiller(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Creating texure filler normal work test
	 */
	@Test
	public void createTextureFillerTest()
	{
		PolygonFiller createdFiller = PolygonFillersFactory.createTextureFiller(new BufferedImage(1, 1, BufferedImage.TYPE_3BYTE_BGR));

		assertTrue(createdFiller instanceof TexturePolygonFiller);
	}

	/**
	 * Creating texure filler with null texture test
	 */
	@Test
	public void createTextureFillerWithNullTextureTest()
	{
		try
		{
			PolygonFiller createdFiller = PolygonFillersFactory.createTextureFiller(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test reading/writing of color filler
	 */
	@Test
	public void colorFillerReadingWritingTest()
	{
		try
		{
			PolygonFiller writingFiller = PolygonFillersFactory.createColorFiller(Color.GREEN);
			
			DataOutputStream output = new DataOutputStream(new FileOutputStream(IOTester.TEST_FILE_NAME));
			PolygonFillersFactory.writeFillerToSream(writingFiller, output);
			output.close();
			
			DataInputStream input = new DataInputStream(new FileInputStream(IOTester.TEST_FILE_NAME));
			PolygonFiller readingFiller = PolygonFillersFactory.readFillerFromStream(input);
			input.close();
			
			assertEquals(writingFiller.getType(), readingFiller.getType());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
	
	/**
	 * Test reading/writing of color filler
	 */
	@Test
	public void textureFillerReadingWritingTest()
	{
		try
		{
			PolygonFiller writingFiller = PolygonFillersFactory.createTextureFiller(new BufferedImage(1, 1, BufferedImage.TYPE_4BYTE_ABGR));
			
			DataOutputStream output = new DataOutputStream(new FileOutputStream(IOTester.TEST_FILE_NAME));
			PolygonFillersFactory.writeFillerToSream(writingFiller, output);
			output.close();
			
			DataInputStream input = new DataInputStream(new FileInputStream(IOTester.TEST_FILE_NAME));
			PolygonFiller readingFiller = PolygonFillersFactory.readFillerFromStream(input);
			input.close();
			
			assertEquals(writingFiller.getType(), readingFiller.getType());
		}
		catch (Exception ex)
		{
			fail();
		}
	}
}
