package drawingStyles.exceptions;

import drawingStyles.DrawSettingsOnScaleArray;

/**
 * drawing style on a scale level is null
 *
 * @author pgalex
 */
public class ScaledStyleIsNullException extends ScaledObjectStyleArrayRuntimeException
{
	/**
	 * Constructor
	 *
	 * @param pArrayThrowedException scaled object style array throwed this
	 * exception
	 */
	public ScaledStyleIsNullException(DrawSettingsOnScaleArray pArrayThrowedException)
	{
		super(pArrayThrowedException);
	}
}
