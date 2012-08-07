package drawingStyles.exceptions;

import drawingStyles.DrawSettingsOnScaleArray;

/**
 * drawing style on a scale level is null
 *
 * @author pgalex
 */
public class DrawSettingsOnScaleIsNullException extends DrawSettingsOnScaleArrayRuntimeException
{
	/**
	 * Constructor
	 *
	 * @param pArrayThrowedException scaled object style array throwed this
	 * exception
	 */
	public DrawSettingsOnScaleIsNullException(DrawSettingsOnScaleArray pArrayThrowedException)
	{
		super(pArrayThrowedException);
	}
}
