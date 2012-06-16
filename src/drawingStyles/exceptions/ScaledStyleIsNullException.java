package drawingStyles.exceptions;

import drawingStyles.ScaledObjectStyleArray;

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
	public ScaledStyleIsNullException(ScaledObjectStyleArray pArrayThrowedException)
	{
		super(pArrayThrowedException);
	}
}
