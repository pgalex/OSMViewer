package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Image with reading/writing
 *
 * @author abc
 */
public class IOIcon implements ReadableMapData, WritableMapData
{
	/**
	 * Format of image for Writing/reading
	 */
	private static final String IMAGE_FORMAT = "png";
	/**
	 * Image. Can be null
	 */
	private BufferedImage image;

	/**
	 * Create with default values
	 */
	public IOIcon()
	{
		image = null;
	}

	/**
	 * Create with image
	 *
	 * @param imageToStore storing image
	 */
	public IOIcon(BufferedImage imageToStore)
	{
		image = imageToStore;
	}

	/**
	 * Create by reading image from file
	 *
	 * @param fileName name of image file
	 * @throws IOException reading error
	 */
	public IOIcon(String fileName) throws IOException
	{
		try
		{
			image = ImageIO.read(new File(fileName));
		}
		catch (Exception e)
		{
			image = null;
			throw new IOException(e);
		}
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
			boolean isValidImage = input.readBoolean(); // for null image
			if (isValidImage)
			{
				image = ImageIO.read(input);
			}
			else
			{
				image = null;
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
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
			boolean isValidImage = (image != null); // for null image
			output.writeBoolean(isValidImage);
			if (isValidImage)
			{
				ImageIO.write(image, IMAGE_FORMAT, output);
			}
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Get storing image
	 *
	 * @return storing image
	 */
	public BufferedImage getImage()
	{
		return image;
	}

	/**
	 * Set storing image
	 *
	 * @param imageToSet new storing image
	 */
	public void setImage(BufferedImage imageToSet)
	{
		image = imageToSet;
	}
}
