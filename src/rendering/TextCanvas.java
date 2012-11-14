package rendering;

import drawingStyles.TextDrawSettings;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

/**
 * Canvas using for text drawing
 *
 * @author pgalex
 */
public class TextCanvas
{
	/**
	 * Graphics where draw text
	 */
	private Graphics2D canvas;

	/**
	 * Create text canvas by graphics
	 *
	 * @param canvasForText graphics using to draw text
	 * @throws IllegalArgumentException canvasForText is null
	 */
	public TextCanvas(Graphics2D canvasForText) throws IllegalArgumentException
	{
		if (canvasForText == null)
		{
			throw new IllegalArgumentException();
		}

		canvas = canvasForText;
	}

	/**
	 * Draw text on canvas by text center x and top bound of text
	 *
	 * @param textToDraw text to draw on canvas. Will not be draw if empty
	 * @param drawSettings draw setting of text
	 * @param textCenterX text center x on canvas
	 * @param textTop text top bound on canvas
	 * @throws IllegalArgumentException textToDraw or drawSettings is null
	 */
	public void drawTextUnderPoint(String textToDraw, TextDrawSettings drawSettings,
					double textCenterX, double textTop) throws IllegalArgumentException
	{
		if (textToDraw == null)
		{
			throw new IllegalArgumentException();
		}
		if (drawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		if (textToDraw.isEmpty())
		{
			return;
		}

		canvas.setColor(drawSettings.getColor());
		canvas.setFont(drawSettings.getFont());

		FontMetrics textFontMetrics = canvas.getFontMetrics(drawSettings.getFont());
		int textWidth = textFontMetrics.stringWidth(textToDraw);
		int textHeight = textFontMetrics.getHeight();

		canvas.drawString(textToDraw, (int) textCenterX - textWidth / 2, (int) textTop + textHeight / 2);
	}

	/**
	 * Draw text on canvas by center point of text (text center will be in given
	 * point)
	 *
	 * @param textToDraw text to draw on canvas. Will not be draw if empty
	 * @param drawSettings draw setting of text
	 * @param textCenterX text center x on canvas
	 * @param textCenterY text center y on canvas
	 * @throws IllegalArgumentException textToDraw or drawSettings is null
	 */
	public void drawTextAtPoint(String textToDraw, TextDrawSettings drawSettings,
					double textCenterX, double textCenterY) throws IllegalArgumentException
	{
		if (textToDraw == null)
		{
			throw new IllegalArgumentException();
		}
		if (drawSettings == null)
		{
			throw new IllegalArgumentException();
		}

		if (textToDraw.isEmpty())
		{
			return;
		}

		canvas.setColor(drawSettings.getColor());
		canvas.setFont(drawSettings.getFont());

		FontMetrics textFontMetrics = canvas.getFontMetrics(drawSettings.getFont());
		int textWidth = textFontMetrics.stringWidth(textToDraw);
		int textAscent = textFontMetrics.getAscent();

		canvas.drawString(textToDraw, 
						(int) textCenterX - textWidth / 2,
						(int) textCenterY + textAscent / 2);
	}
}
