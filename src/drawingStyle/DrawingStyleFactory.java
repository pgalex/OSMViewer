package drawingStyle;

/**
 * Using for creating StyleViewer and StyleEditor
 *
 * @author pgalex
 */
public class DrawingStyleFactory
{
	/**
	 * Create style viewer
	 *
	 * @return style viewer
	 */
	static public StyleViewer createStyleViewer()
	{
		return new MapObjectStyleViewer();
	}

	/**
	 * Create style editor
	 *
	 * @return style editor
	 */
	static public StyleEditor createStyleEditor()
	{
		return new MapObjectStyleEditor();
	}
}
