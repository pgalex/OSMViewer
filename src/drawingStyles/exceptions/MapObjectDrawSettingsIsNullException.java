package drawingStyles.exceptions;

import drawingStyles.StyleEditor;

/**
 * Style of map object is null
 *
 * @author pgalex
 */
public class MapObjectDrawSettingsIsNullException extends StyleEditorRuntimeException
{
	/**
	 * Constructor
	 *
	 * @param pEditorThrowedException Style editor that throwed exception
	 */
	public MapObjectDrawSettingsIsNullException(StyleEditor pEditorThrowedException)
	{
		super(pEditorThrowedException);
	}
}
