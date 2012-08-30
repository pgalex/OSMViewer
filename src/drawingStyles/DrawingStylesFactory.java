package drawingStyles;

/**
 * Using for creating StyleViewer and StyleEditor
 *
 * @author pgalex
 */
public class DrawingStylesFactory
{
	/**
	 * Create style viewer. Style viewer optimized for runtime work, but not allow
	 * editing
	 *
	 * @return style viewer
	 */
	static public StyleViewer createStyleViewer()
	{
		return new MapObjectDrawStylesViewer();
	}

	/**
	 * Create style editor. Slower than style viewer, but allow editing
	 *
	 * @return style editor
	 */
	static public StyleEditor createStyleEditor()
	{
		return new MapObjectDrawStylesEditor();
	}
}
