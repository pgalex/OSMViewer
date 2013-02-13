package rendering;

import java.awt.Color;
import java.awt.Font;

/**
 * Draw settings of text
 *
 * @author pgalex
 */
public interface TextDrawSettings
{
	/**
	 * Get text color
	 *
	 * @return text color
	 */
	public Color getColor();

	/**
	 * Get text font
	 *
	 * @return text font
	 */
	public Font getFont();

	/**
	 * Set new text color
	 *
	 * @param textColorToSet new text color
	 * @throws IllegalArgumentException textColorToSet is null
	 */
	public void setColor(Color textColorToSet) throws IllegalArgumentException;

	/**
	 * Set new text font
	 *
	 * @param textFontToSet new text font
	 * @throws IllegalArgumentException textFontToSet is null
	 */
	public void setFont(Font textFontToSet) throws IllegalArgumentException;
}
