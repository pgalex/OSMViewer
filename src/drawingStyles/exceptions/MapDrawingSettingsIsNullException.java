package drawingStyles.exceptions;

import drawingStyles.StyleEditor;

/**
 * Map drawing settings gives to editor is null
 *
 * @author pgalex
 */
public class MapDrawingSettingsIsNullException extends StyleEditorRuntimeException
{
	/**
	 * Constructor
	 *
	 * @param pEditorThrowedException Style editor that throwed exception
	 */
	public MapDrawingSettingsIsNullException(StyleEditor pEditorThrowedException)
	{
		super(pEditorThrowedException);
	}
}
