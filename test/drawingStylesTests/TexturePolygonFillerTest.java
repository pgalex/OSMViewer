package drawingStylesTests;

import drawingStyles.PolygonFillerType;
import drawingStyles.TexturePolygonFiller;
import java.awt.Paint;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author pgalex
 */
public class TexturePolygonFillerTest
{
	/**
	 * Test creating with null texture
	 */
	@Test
	public void creatingWithNullTextureTest()
	{
		try
		{
			TexturePolygonFiller filler = new TexturePolygonFiller(null);
			fail();
		}
		catch (IllegalArgumentException ex)
		{
			// ok
		}
	}

	/**
	 * Test getType of filler
	 */
	@Test
	public void typeTest()
	{
		TexturePolygonFiller filler = new TexturePolygonFiller(new BufferedImage(10, 20, BufferedImage.TYPE_BYTE_GRAY));
		assertEquals(PolygonFillerType.BY_TEXTURE, filler.getType());
	}
	
	/**
	 * Test creating paing
	 */
	@Test
	public void getPaintTest()
	{
		TexturePolygonFiller filler = new TexturePolygonFiller(new BufferedImage(10, 20, BufferedImage.TYPE_BYTE_GRAY));
		Paint paint = filler.getPaint();
		
		assertTrue(paint instanceof TexturePaint);
	}
}
