package drawingStyles.exceptions;

import drawingStyles.StyleEditor;

/**
 * Style of map object is null
 *
 * @author pgalex
 */
public class MapObjectStyleIsNullException extends StyleEditorRuntimeException
{
	/**
	 * Constructor
	 *
	 * @param pEditorThrowedException Style editor that throwed exception
	 */
	public MapObjectStyleIsNullException(StyleEditor pEditorThrowedException)
	{
		super(pEditorThrowedException);
	}
}
