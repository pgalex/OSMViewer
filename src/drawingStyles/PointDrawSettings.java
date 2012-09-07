package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to object if its one node
 *
 * @author abc
 */
public class PointDrawSettings implements PointDrawStyle, ReadableMapData, WritableMapData
{
	/**
	 * Icon
	 */
	private IOIcon icon;

	/**
	 * Create with default values
	 */
	public PointDrawSettings()
	{
		icon = new IOIcon();
	}

	/**
	 * Create with parameters
	 *
	 * @param pointIcon icon. Can be null
	 */
	public PointDrawSettings(BufferedImage pointIcon)
	{
		icon = new IOIcon(pointIcon);
	}

	/**
	 * Get icon
	 *
	 * @return icon
	 */
	@Override
	public BufferedImage getIcon()
	{
		return icon.getImage();
	}

	/**
	 * Set new icon
	 *
	 * @param iconToSet new icon
	 */
	public void setIcon(BufferedImage iconToSet)
	{
		icon.setImage(iconToSet);
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
			icon.readFromStream(input);
		}
		catch (Exception ex)
		{
			throw new IOException();
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
			icon.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}
