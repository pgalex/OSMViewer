package drawingStyle;

/**
 * Using for create StyleViewer and StyleEditor for manipulating drawing styles
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
