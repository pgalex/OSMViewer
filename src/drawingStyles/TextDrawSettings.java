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
public class TextDrawSettings implements TextDrawStyle, ReadableMapData, WritableMapData
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
	 * @throws IllegalArgumentException text color is null 
	 */
	public TextDrawSettings(Color textColor, Font textFont) throws IllegalArgumentException
	{
		color = new ColorWithIO(textColor);
		font = new FontWithIO(textFont);
	}

	/**
	 * Get text color
	 *
	 * @return text color
	 */
	@Override
	public Color getColor()
	{
		return color.getStoringColor();
	}

	/**
	 * Get text font
	 *
	 * @return text font
	 */
	@Override
	public Font getFont()
	{
		return font.getFont();
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
