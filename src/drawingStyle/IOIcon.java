package drawingStyle;

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
	 * Writing format
	 */
	private static final String IMAGE_FORMAT = "png";
	/**
	 * Image. Can be null. Must be "settable" only from constructor
	 */
	private BufferedImage image;

	/**
	 * Default constructor
	 */
	public IOIcon()
	{
		image = null;
	}

	/**
	 * Conctructor by exists image
	 *
	 * @param pImage Image
	 */
	public IOIcon(BufferedImage pImage)
	{
		image = pImage;
	}

	/**
	 * Contructor with reading image from file
	 *
	 * @param pFileName file name (.png, .jpg .. etc )
	 * @throws IOException reading error
	 */
	public IOIcon(String pFileName) throws IOException
	{
		try
		{
			image = ImageIO.read(new File(pFileName));
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
	 * @param pInput reading stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			boolean isValidImage = pInput.readBoolean(); // for null image
			if (isValidImage)
				image = ImageIO.read(pInput);
			else
				image = null;
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Write into stream
	 *
	 * @param pOutput output stream
	 * @throws IOException writing error
	 */
	@Override
	public void writeToStream(DataOutputStream pOutput) throws IOException
	{
		try
		{
			boolean isValidImage = (image != null); // for null image
			pOutput.writeBoolean(isValidImage);
			if (isValidImage)
				ImageIO.write(image, IMAGE_FORMAT, pOutput);
		}
		catch (Exception e)
		{
			throw new IOException(e);
		}
	}

	/**
	 * Get image
	 *
	 * @return image
	 */
	public BufferedImage getImage()
	{
		return image;
	}
}
