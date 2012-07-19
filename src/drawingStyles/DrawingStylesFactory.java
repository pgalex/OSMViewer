package drawingStyles;

/**
 * Using for creating StyleViewer and StyleEditor
 *
 * @author pgalex
 */
public class DrawingStylesFactory
{
	/**
	 * Create style viewer
	 *
	 * @return style viewer
	 */
	static public StyleViewer createStyleViewer()
	{
		return new MapObjectDrawStylesViewer();
	}

	/**
	 * Create style editor
	 *
	 * @return style editor
	 */
	static public StyleEditor createStyleEditor()
	{
		return new MapObjectDrawStylesEditor();
	}
}
