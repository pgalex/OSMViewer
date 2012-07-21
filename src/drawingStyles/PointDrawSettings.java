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
	 * Default constructor
	 */
	public PointDrawSettings()
	{
		icon = new IOIcon();
	}

	/**
	 * Contructor
	 *
	 * @param pIcon icon. Can be null
	 */
	public PointDrawSettings(BufferedImage pIcon)
	{
		icon = new IOIcon(pIcon);
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
			icon.readFromStream(pInput);
		}
		catch (Exception ex)
		{
			throw new IOException();
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
			icon.writeToStream(pOutput);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
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
	 * @param pIcon new icon
	 */
	public void setIcon(BufferedImage pIcon)
	{
		icon.setImage(pIcon);
	}
}
