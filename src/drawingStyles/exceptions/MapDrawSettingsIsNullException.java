package drawingStyles.exceptions;

import drawingStyles.StyleEditor;

/**
 * Map drawing settings gives to editor is null
 *
 * @author pgalex
 */
public class MapDrawSettingsIsNullException extends StyleEditorRuntimeException
{
	/**
	 * Constructor
	 *
	 * @param pEditorThrowedException Style editor that throwed exception
	 */
	public MapDrawSettingsIsNullException(StyleEditor pEditorThrowedException)
	{
		super(pEditorThrowedException);
	}
}
