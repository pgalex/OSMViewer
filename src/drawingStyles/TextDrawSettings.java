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
	private IOFont font;
	/**
	 * Text color
	 */
	private IOColor color;

	/**
	 * Default constructor
	 */
	public TextDrawSettings()
	{
		font = new IOFont();
		color = new IOColor();
	}

	/**
	 * Constructor
	 *
	 * @param pColor text color
	 * @param pFont text font
	 */
	public TextDrawSettings(Color pColor, Font pFont)
	{
		color = new IOColor(pColor);
		font = new IOFont(pFont);
	}

	/**
	 * Get text color
	 *
	 * @return text color
	 */
	@Override
	public Color getColor()
	{
		return color.getColor();
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
	 * @param pInput input stream
	 * @throws IOException reading error
	 */
	@Override
	public void readFromStream(DataInputStream pInput) throws IOException
	{
		try
		{
			font.readFromStream(pInput);
			color.readFromStream(pInput);
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
			font.writeToStream(pOutput);
			color.writeToStream(pOutput);
		}
		catch (Exception ex)
		{
			throw new IOException();
		}
	}
}
