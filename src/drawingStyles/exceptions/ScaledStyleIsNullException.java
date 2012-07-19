package drawingStyles.exceptions;

import drawingStyles.DrawStyleOnScaleArray;

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
	public ScaledStyleIsNullException(DrawStyleOnScaleArray pArrayThrowedException)
	{
		super(pArrayThrowedException);
	}
}
