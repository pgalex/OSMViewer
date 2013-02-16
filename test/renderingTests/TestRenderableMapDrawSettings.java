package renderingTests;

import java.awt.Color;
import rendering.RenderableMapDrawSettings;

/**
 * Test implementation of MapDrawSettings
 *
 * @author pgalex
 */
public class TestRenderableMapDrawSettings implements RenderableMapDrawSettings
{
	@Override
	public Color getMapBackgroundColor()
	{
		return Color.WHITE;
	}
}
