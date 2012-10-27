package drawingStyles;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Factory for polygon fillers. Also provides read/write methods
 *
 * @author pgalex
 */
public class PolygonFillersFactory
{
	/**
	 * Create color filler with color
	 *
	 * @param fillColor color, using to fill polygon
	 * @return created filler
	 * @throws IllegalArgumentException fillColor is null
	 */
	public static PolygonFiller createColorFiller(Color fillColor) throws IllegalArgumentException
	{
		if (fillColor == null)
		{
			throw new IllegalArgumentException();
		}

		return new ColorPolygonFiller(fillColor);
	}

	/**
	 * Create texure filler with texure
	 *
	 * @param fillTexture texture, using to fill polygon
	 * @return created filler
	 * @throws IllegalArgumentException fillTexture is null
	 */
	public static PolygonFiller createTextureFiller(BufferedImage fillTexture) throws IllegalArgumentException
	{
		if (fillTexture == null)
		{
			throw new IllegalArgumentException();
		}

		return new TexturePolygonFiller(fillTexture);
	}

	/**
	 * Read polygon filler from stream. Using filler type while reading
	 *
	 * @param input input stream
	 * @return read filler
	 * @throws IOException reading error
	 */
	public static PolygonFiller readFillerFromStream(DataInputStream input) throws IOException
	{
		try
		{
			String readingFillerTypeName = input.readUTF();
			PolygonFiller readingFiller = createFillerByTypeForReading(PolygonFillerType.valueOf(readingFillerTypeName));

			readingFiller.readFromStream(input);

			return readingFiller;
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}

	/**
	 * Create filler equal filler type with default values
	 *
	 * @param fillerType type of creating polygon filler
	 * @return created filler
	 * @throws IllegalArgumentException given fillerType not supported
	 */
	private static PolygonFiller createFillerByTypeForReading(PolygonFillerType fillerType) throws IllegalArgumentException
	{
		PolygonFiller fillerByType = null;
		switch (fillerType)
		{
			case BY_COLOR:
				fillerByType = new ColorPolygonFiller(Color.BLACK); // some color
				break;

			case BY_TEXTURE:
				fillerByType = new TexturePolygonFiller(new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB)); // some image
				break;

			default:
				throw new IllegalArgumentException();
		}

		return fillerByType;
	}

	/**
	 * Write filler to stream
	 *
	 * @param fillerToWrite filler to write
	 * @param output output stream
	 * @throws IOException writing error
	 */
	public static void writeFillerToSream(PolygonFiller fillerToWrite, DataOutputStream output) throws IOException
	{
		try
		{
			output.writeUTF(fillerToWrite.getType().name());
			fillerToWrite.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}
