package drawingStyles;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

/**
 * Image with reading/writing
 *
 * @author abc
 */
public class ImageWithIO
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
	public ImageWithIO()
	{
		image = null;
	}

	/**
	 * Create with image
	 *
	 * @param imageToStore storing image
	 */
	public ImageWithIO(BufferedImage imageToStore)
	{
		image = imageToStore;
	}

	/**
	 * Create by reading image from file
	 *
	 * @param fileName name of image file
	 * @throws IOException reading error
	 */
	public ImageWithIO(String fileName) throws IOException
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
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			boolean isImageValid = input.readBoolean(); // for null image
			if (isImageValid)
			{
				int imageBytesLength = input.readInt();
				byte[] imageBytes = new byte[imageBytesLength];
				input.read(imageBytes, 0, imageBytesLength);

				InputStream imageInputStream = new ByteArrayInputStream(imageBytes);
				image = ImageIO.read(imageInputStream);
				imageInputStream.close();
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
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			boolean isImageValid = (image != null); // for null image
			output.writeBoolean(isImageValid);
			if (isImageValid)
			{
				// writing as bytes array, cuz file ImageIO.read, reads all file to end

				ByteArrayOutputStream bytesStream = new ByteArrayOutputStream();
				ImageIO.write(image, IMAGE_FORMAT, bytesStream);

				output.writeInt(bytesStream.toByteArray().length);
				output.write(bytesStream.toByteArray());

				bytesStream.close();
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
