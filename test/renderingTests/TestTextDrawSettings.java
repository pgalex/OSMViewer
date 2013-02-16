package renderingTests;

import java.awt.Color;
import java.awt.Font;
import rendering.TextDrawSettings;

/**
 * Test implementation of TextDrawSettings
 *
 * @author pgalex
 */
public class TestTextDrawSettings implements TextDrawSettings
{
	@Override
	public Color getColor()
	{
		return Color.BLACK;
	}

	@Override
	public Font getFont()
	{
		return new Font("Arial", 0, 14);
	}

	@Override
	public TextDrawSettings createCopyWithColor(Color newTextColor) throws IllegalArgumentException
	{
		return new TestTextDrawSettings();
	}
}
