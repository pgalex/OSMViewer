package com.osmviewer.drawingStyles;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to object if its one node
 *
 * @author abc
 */
public class StandartPointDrawSettings
{
	/**
	 * Icon
	 */
	private ImageWithIO icon;

	/**
	 * Create with default values
	 */
	public StandartPointDrawSettings()
	{
		icon = new ImageWithIO();
	}

	/**
	 * Get icon
	 *
	 * @return icon
	 */
	public BufferedImage getIcon()
	{
		return icon.getImage();
	}

	/**
	 * Set new icon
	 *
	 * @param iconToSet new icon. If null - no icon
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
	public void readFromStream(DataInputStream input) throws IOException
	{
		try
		{
			icon.readFromStream(input);
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
	public void writeToStream(DataOutputStream output) throws IOException
	{
		try
		{
			icon.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}
}
