package com.osmviewer.rendering;

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
	 * Create copy of this text draw settings with other color
	 *
	 * @param newTextColor new text color
	 * @return text draw settings by source draw settings, but with given color
	 * @throws IllegalArgumentException sourceTextDrawSettings or newTextColor is
	 * null
	 */
	public TextDrawSettings createCopyWithColor(Color newTextColor) throws IllegalArgumentException;
}
