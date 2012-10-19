package drawingStyles;

import IO.ReadableMapData;
import IO.WritableMapData;
import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * How to draw text
 *
 * @author pgalex
 */
public class TextDrawSettings implements ReadableMapData, WritableMapData
{
	/**
	 * Text font
	 */
	private FontWithIO font;
	/**
	 * Text color
	 */
	private ColorWithIO color;

	/**
	 * Create with default values
	 */
	public TextDrawSettings()
	{
		font = new FontWithIO();
		color = new ColorWithIO();
	}

	/**
	 * Create with parameters
	 *
	 * @param textColor text color
	 * @param textFont text font
	 * @throws IllegalArgumentException textColor or textFont is null
	 */
	public TextDrawSettings(Color textColor, Font textFont) throws IllegalArgumentException
	{
		if (textColor == null)
		{
			throw new IllegalArgumentException();
		}
		if (textFont == null)
		{
			throw new IllegalArgumentException();
		}

		color = new ColorWithIO(textColor);
		font = new FontWithIO(textFont);
	}

	/**
	 * Get text color
	 *
	 * @return text color
	 */
	public Color getColor()
	{
		return color.getStoringColor();
	}

	/**
	 * Set new text color
	 *
	 * @param textColorToSet
	 * @throws IllegalArgumentException textColorToSet is null
	 */
	public void setColor(Color textColorToSet) throws IllegalArgumentException
	{
		if (textColorToSet == null)
		{
			throw new IllegalArgumentException();
		}

		color.setStoringColor(textColorToSet);
	}

	/**
	 * Get text font
	 *
	 * @return text font
	 */
	public Font getFont()
	{
		return font.getStoringFont();
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
			font.readFromStream(input);
			color.readFromStream(input);
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
			font.writeToStream(output);
			color.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}
