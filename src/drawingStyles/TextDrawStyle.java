package drawingStyles;

import java.awt.Color;
import java.awt.Font;

/**
 * Drawing style of text(that can be displaying under object)
 *
 * @author pgalex
 */
public interface TextDrawStyle
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
}
