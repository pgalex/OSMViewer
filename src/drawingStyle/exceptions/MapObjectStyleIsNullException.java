package drawingStyle.exceptions;

import drawingStyle.StyleEditor;

/**
 * Style of map object is null
 *
 * @author pgalex
 */
public class MapObjectStyleIsNullException extends RuntimeException
{
	/**
	 * Style editor that throwed exception
	 */
	private StyleEditor editorThrowedException;

	/**
	 * Constructor
	 *
	 * @param pEditorThrowedException Style editor that throwed exception
	 */
	public MapObjectStyleIsNullException(StyleEditor pEditorThrowedException)
	{
		editorThrowedException = pEditorThrowedException;
	}

	/**
	 * Get Style editor that throwed exception
	 *
	 * @return Style editor that throwed exception
	 */
	public StyleEditor getEditorThrowedException()
	{
		return editorThrowedException;
	}
}
