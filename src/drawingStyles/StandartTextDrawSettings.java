package drawingStyles;

import java.awt.Color;
import java.awt.Font;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import rendering.TextDrawSettings;

/**
 * How to draw text
 *
 * @author pgalex
 */
public class StandartTextDrawSettings implements TextDrawSettings
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
	public StandartTextDrawSettings()
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
	public StandartTextDrawSettings(Color textColor, Font textFont) throws IllegalArgumentException
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
	@Override
	public Color getColor()
	{
		return color.getStoringColor();
	}

	/**
	 * Set new text color
	 *
	 * @param textColorToSet new text color
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
	@Override
	public Font getFont()
	{
		return font.getStoringFont();
	}

	/**
	 * Set new text font
	 *
	 * @param textFontToSet new text font
	 * @throws IllegalArgumentException textFontToSet is null
	 */
	public void setFont(Font textFontToSet) throws IllegalArgumentException
	{
		if (textFontToSet == null)
		{
			throw new IllegalArgumentException();
		}

		font.setStoringFont(textFontToSet);
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
			font.readFromStream(input);
			color.readFromStream(input);
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
			font.writeToStream(output);
			color.writeToStream(output);
		}
		catch (Exception ex)
		{
			throw new IOException(ex);
		}
	}

	/**
	 * Create copy of this text draw settings with other color
	 *
	 * @param newTextColor new text color
	 * @return text draw settings by source draw settings, but with given color
	 * @throws IllegalArgumentException sourceTextDrawSettings or newTextColor is
	 * null
	 */
	@Override
	public TextDrawSettings createCopyWithColor(Color newTextColor) throws IllegalArgumentException
	{
		if (newTextColor == null)
		{
			throw new IllegalArgumentException();
		}

		StandartTextDrawSettings textDrawSettingsWithOtherColor = new StandartTextDrawSettings();
		textDrawSettingsWithOtherColor.setFont(getFont());
		textDrawSettingsWithOtherColor.setColor(newTextColor);

		return textDrawSettingsWithOtherColor;
	}
}
