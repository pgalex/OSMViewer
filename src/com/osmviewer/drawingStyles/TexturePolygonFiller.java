package com.osmviewer.drawingStyles;

import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Polygon filler - by texture
 *
 * @author pgalex
 */
public class TexturePolygonFiller implements PolygonFiller
{
	/**
	 * Texture for filling
	 */
	private ImageWithIO fillTexture;

	/**
	 * Create with texture
	 *
	 * @param textureForFilling texture for filling
	 * @throws IllegalArgumentException textureForFilling is null
	 */
	public TexturePolygonFiller(BufferedImage textureForFilling) throws IllegalArgumentException
	{
		if (textureForFilling == null)
		{
			throw new IllegalArgumentException();
		}

		fillTexture = new ImageWithIO(textureForFilling);
	}

	/**
	 * Get filler type
	 *
	 * @return filler type
	 */
	@Override
	public PolygonFillerType getType()
	{
		return PolygonFillerType.BY_TEXTURE;
	}

	/**
	 * Get paint for drawing using filler
	 *
	 * @return paint for drawing by filler
	 */
	@Override
	public Paint getPaint()
	{
		return new TexturePaint(fillTexture.getImage(),
						new Rectangle(0, 0, fillTexture.getImage().getWidth(), fillTexture.getImage().getHeight()));
	}

	/**
	 * Read from stream
	 *
	 * @param input input stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			fillTexture.readFromStream(input);
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}

	/**
	 * Write into stream
	 *
	 * @param output output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			fillTexture.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}
}
