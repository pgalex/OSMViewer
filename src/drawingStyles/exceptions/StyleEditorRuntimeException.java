package drawingStyles.exceptions;

import drawingStyles.StyleEditor;

/**
 * Runtime exception in style editor
 * @author pgalex
 */
public class StyleEditorRuntimeException extends RuntimeException
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
	public StyleEditorRuntimeException(StyleEditor pEditorThrowedException)
	{
		editorThrowedException = pEditorThrowedException;
	}

	/**
	 * Get style editor that throwed exception
	 *
	 * @return Style editor that throwed exception
	 */
	public StyleEditor getEditorThrowedException()
	{
		return editorThrowedException;
	}
}
